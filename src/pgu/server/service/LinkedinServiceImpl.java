package pgu.server.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import pgu.client.service.LinkedinService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.dto.AccessToken;
import pgu.shared.dto.Connections;
import pgu.shared.dto.Country;
import pgu.shared.dto.LinkedinProfile;
import pgu.shared.dto.Location;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.Person;
import pgu.shared.dto.Profile;
import pgu.shared.dto.RequestToken;
import pgu.shared.model.UserAndLocations;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LinkedinServiceImpl extends RemoteServiceServlet implements LinkedinService {

    private static final String PROFILE_URL     = "http://api.linkedin.com/v1/people/~";
    private static final String CONNECTIONS_URL = PROFILE_URL + "/connections";

    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();
    private final DAO           dao             = new DAO();

    private OAuthService        oauthService    = null;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        final String apiFileName = config.getServletContext().getInitParameter("apiFile");
        final Properties props = loadProperties(apiFileName);

        final String apiKey = props.getProperty("api.key");
        final String apiSecret = props.getProperty("api.secret");

        // System.out.println("k: " + apiKey);
        // System.out.println("s: " + apiSecret);

        oauthService = getOauthService(apiKey, apiSecret);

        super.init(config);
    }

    @Override
    public void logInLinkedin() {

        // https://developer.linkedin.com/documents/common-issues-oauth-authentication
        // https://developer.linkedin.com/documents/debugging-api-calls
        // https://developer.linkedin.com/oauth-test-console

        // set the scanner to catch input from the user
        final Scanner in = new Scanner(System.in);

        // get our request token
        final Token requestToken = oauthService.getRequestToken();

        // now use the request token to get the verifier
        System.out.println(oauthService.getAuthorizationUrl(requestToken));
        System.out.println("Visit the above URL in your browser and then paste the numerical verifier code here");
        System.out.print(">>");

        // build the verifer with the pin the user enters
        final Verifier verifier = new Verifier(in.nextLine());

        // use the request token and our verifier to get an access token and we are all set
        final Token accessToken = oauthService.getAccessToken(requestToken, verifier);

        // now make a simple query to make sure our token works
        // we fetch our own profile on linkedin. This query will be explained more on later pages
        final String myProfileUrl = "http://api.linkedin.com/v1/people/~";
        OAuthRequest request = newRequest(myProfileUrl, accessToken);
        Response response = request.send();
        // System.out.println(response.getBody());

        final String connectionsUrl = "http://api.linkedin.com/v1/people/~/connections";
        request = newRequest(connectionsUrl, accessToken);
        response = request.send();
        logResponseCode(response);

        final Gson gson = new Gson();
        final Connections connections = gson.fromJson(response.getBody(), Connections.class);
        System.out.println(connections.get_total());

        final ArrayList<Person> persons = connections.getValues();
        if (persons == null) {
            return;
        }

        final HashMap<String, Integer> code2weight = new HashMap<String, Integer>();
        for (final Person p : persons) {
            final Location location = p.getLocation();
            if (location == null) {
                continue;
            }
            final Country country = location.getCountry();
            if (country == null) {
                continue;
            }
            final String code = country.getCode();
            if (code == null) {
                continue;
            }

            if (code2weight.containsKey(code)) {
                final Integer count = code2weight.get(code) + 1;
                code2weight.put(code, count);
            } else {
                code2weight.put(code, 1);
            }
        }

        for (final Entry<String, Integer> e : code2weight.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        // https://developers.google.com/maps/documentation/javascript/tutorial

        // https://developer.linkedin.com/documents/profile-fields
        // first-name
        // last-name
        // location:(name)
        // location:(country:(code))
        // distance
        // connections
        // num-connections
        // num-connections-capped
        // languages

        // https://developer.linkedin.com/documents/field-selectors
        // http://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry)
        // http://api.linkedin.com/v1/people/~/connections:(id,first-name,last-name,industry)

        // https://developer.linkedin.com/documents/connections-api
        // http://api.linkedin.com/v1/people/~/connections
        // http://api.linkedin.com/v1/people/id=12345/connections
        // http://api.linkedin.com/v1/people/~/connections:(headline,first-name,last-name)

        // token invalidation
        // https://api.linkedin.com/uas/oauth/invalidateToken
    }

    private OAuthService getOauthService(final String apiKey, final String apiSecret) {
        return new ServiceBuilder() //
        .provider(LinkedInApi.class) //
        .apiKey(apiKey) //
        .apiSecret(apiSecret) //
        // .callback(getCallbackUrl()) // // TODO PGU Aug 1, 2012 how to use callback url
        .build();
    }

    private String getCallbackUrl() {
        return u.isEnvProd() ? "http://pgu-contacts.appspot.com"
                : "http://127.0.0.1:8888/Pgu_contacts.html?gwt.codesvr=127.0.0.1:9997";
    }

    private void logResponseCode(final Response response) {
        final int responseNumber = response.getCode();

        if (responseNumber >= 199 && responseNumber < 300) {
            System.out.println("HOORAY IT WORKED!!");
            // System.out.println(response.getBody());
        } else if (responseNumber >= 500 && responseNumber < 600) {
            // you could actually raise an exception here in your own code
            System.out.println("Ruh Roh application error of type 500: " + responseNumber);
            // System.out.println(response.getBody());
        } else if (responseNumber == 403) {
            System.out.println("A 403 was returned which usually means you have reached a throttle limit");
        } else if (responseNumber == 401) {
            System.out.println("A 401 was returned which is a Oauth signature error");
            // System.out.println(response.getBody());
        } else {
            System.out.println("We got a different response that we should add to the list: " + responseNumber);
            // System.out.println(response.getBody());
        }
    }

    private OAuthRequest newRequest( //
            final String url //
            , final AccessToken accessToken) {

        final Token token = new Token( //
                accessToken.getToken() //
                , accessToken.getSecret() //
                , accessToken.getRawResponse() //
                );

        return newRequest(url, token);
    }

    private OAuthRequest newRequest( //
            final String url //
            , final Token accessToken) {

        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
        request.addHeader("x-li-format", "json");
        oauthService.signRequest(accessToken, request);

        return request;
    }

    @Override
    public OauthAuthorizationStart getLinkedinUrlAuthorization() {

        final Token token = oauthService.getRequestToken();
        final String url = oauthService.getAuthorizationUrl(token);

        final RequestToken rqToken = new RequestToken();
        rqToken.setRawResponse(token.getRawResponse());
        rqToken.setSecret(token.getSecret());
        rqToken.setToken(token.getToken());

        final OauthAuthorizationStart oas = new OauthAuthorizationStart();
        oas.setRequestToken(rqToken);
        oas.setAuthorizationUrl(url);
        return oas;
    }

    private Properties loadProperties(final String apiFileName) {
        try {

            final Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(apiFileName));
            return props;

        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    // from connections, we got the names (useful for the user)
    // they are distributed by countries
    // for this user
    // what do we stock?
    //
    // the issue is: the user can have a lot of contacts..
    // first, how many contacts?
    // if more than 500, take the first 500.. (see connections_url + from 0 to 500)
    // else take them all
    // (batch de 100?)
    // --> answer: by default: 500 connections, from 0 to 500. cf https://developer.linkedin.com/documents/connections-api
    // in the UI, add a panel 'fetching connections and distributing them'
    //
    // fetch connections from linkedin only if the user used refresh btn or has no information stocked for connections
    // else get the distribution with the names from DB (stock user connections distribution and names)
    //
    // [[DB]] userId, [[country_code, country_name(how to get it?), num of connections]]
    // ex: [['fr','France',38],['es','Spain',9],...]
    //
    // [[DB]] userId, [[country_code, [connection names]]] (load them after the geocharts)
    // ex: [['fr',['Alice Aceli','Bruno Bourn']],['es',['Alicia Acelia','Bruno Bourno']],...]
    // save this information once the distribution is done
    //
    // this distribution allows to build the geocharts
    // build all geocharts but show only the ones the user has selected (stock user preferences of geocharts)
    // [[DB]] userId, {'world':true,'americas':false,...}
    // by default, only the world is shown
    //
    // for a click on an geo area, we have to add an UI area where to list all the connections names
    //
    // also, show all the urls of fusion tables (stock urls of fusion tables)
    // [[DB]] userId, [url1,url2,...]
    //
    // DB resume
    //
    // [[DB]] userId, distrib_num, charts_prefs, fusion_urls    -- notes: charts_prefs: array of only the codes of the visible charts
    // [[DB]] userId, distrib_name
    //
    // distrib_num: [['fr','France',38],['es','Spain',9],...]
    //distrib_name: [['fr',['Alice Aceli','Bruno Bourn']],['es',['Alicia Acelia','Bruno Bourno']],...]
    //charts_prefs: {'world':true,'americas':false,...}
    // fusion_urls: [url1,url2,...]
    //

    @Override
    public Connections fetchConnections(final AccessToken accessToken) {

        String jsonConnections = "";

        if (isTest) {
            jsonConnections = connectionsTest();

        } else {
            jsonConnections = fetchResponseBody(accessToken, CONNECTIONS_URL + ":(first-name,last-name,location)");

        }

        return new Gson().fromJson(jsonConnections, Connections.class);
    }

    private final boolean                        isTest               = true;

    /**
     * https://developer.linkedin.com/documents/profile-api
     */
    @Override
    public Profile fetchProfile(final AccessToken accessToken) {

        String jsonProfile = "";

        if (isTest) {
            jsonProfile = profileTest();

        } else {

            final String detailedProfiled = PROFILE_URL + //
                    ":(" + //
                    "id" + //
                    ",first-name" + //
                    ",last-name" + //
                    ",headline" + //
                    ",location" + //
                    ",num-connections" + //
                    ",num-connections-capped" + //
                    ",summary" + //
                    ",specialties" + //
                    ",picture-url" + //
                    ",public-profile-url" + //
                    ",positions:(id,company,endDate,isCurrent,startDate,summary,title,location)" + //
                    ",languages:(language,proficiency)" + //
                    ",educations" + //
                    ")";
            jsonProfile = fetchResponseBody(accessToken, detailedProfiled);
        }

        final LinkedinProfile javaProfile = new Gson().fromJson(jsonProfile, LinkedinProfile.class);
        final UserAndLocations userAndLocations = dao.ofy().find(UserAndLocations.class, javaProfile.getId());

        final Profile profile = new Profile();
        profile.setJson(jsonProfile);
        profile.setUserAndLocations(userAndLocations);
        return profile;
    }

    private String fetchResponseBody(final AccessToken accessToken, final String profileUrl) {

        final OAuthRequest request = newRequest(profileUrl, accessToken);
        final Response response = request.send();
        logResponseCode(response);

        final String body = response.getBody();
        log.info(this, "%s", body);
        return body;
    }

    @Override
    public AccessToken getAccessToken(final String oauthCode, final RequestToken requestToken) {
        final Token token = new Token( //
                requestToken.getToken() //
                , requestToken.getSecret() //
                , requestToken.getRawResponse() //
                );

        final Verifier verifier = new Verifier(oauthCode);
        final Token linkedinAccessToken = oauthService.getAccessToken(token, verifier);

        final AccessToken accessToken = new AccessToken();
        accessToken.setRawResponse(linkedinAccessToken.getRawResponse());
        accessToken.setSecret(linkedinAccessToken.getSecret());
        accessToken.setToken(linkedinAccessToken.getToken());

        return accessToken;
    }

    private String connectionsTest() {
        return readJsonFromFS("contacts.json");
    }

    private String profileTest() {
        return readJsonFromFS("profile.json");
    }

    private String readJsonFromFS(final String fileName) {

        final InputStream is = getServletContext().getResourceAsStream("/WEB-INF/pgu/" + fileName);
        final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        final StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (final IOException e) {
            log.error(this, e);
        }
        return sb.toString();
    }

    @Override
    public void saveLocations(final String userId, final String items2locations, final String referentialLocations) {
        log.info(this, "(\nuser[%s]\n%s\n\n%s\n)", userId, items2locations, referentialLocations);

        final UserAndLocations userAndLocations = getCurrentUserAndLocations(userId);

        userAndLocations.setItems2locations(items2locations);
        userAndLocations.setReferentialLocations(referentialLocations);
        dao.ofy().async().put(userAndLocations);

        // TODO PGU Sep 12, 2012 async: complete document profile with locations
    }

    private UserAndLocations getCurrentUserAndLocations(final String userId) {

        final UserAndLocations existingUserAndLocations = dao.ofy().find(UserAndLocations.class, userId);
        if (existingUserAndLocations != null) {
            return existingUserAndLocations;
        }

        final UserAndLocations userAndLocations = new UserAndLocations();
        userAndLocations.setUserId(userId);
        return userAndLocations;
    }

    private Index locationsIdx;

    private Index getLocationsIdx() {
        if (locationsIdx == null) {
            locationsIdx = SearchServiceFactory.getSearchService().getIndex(
                    IndexSpec.newBuilder().setName("locations_index"));
        }
        return locationsIdx;
    }

    private void backup(final String userId, final String locations) {
        final QueryOptions mainQueryOptions = QueryOptions.newBuilder() //
                .setReturningIdsOnly(true) //
                .build();

        final com.google.appengine.api.search.Query userLocations = com.google.appengine.api.search.Query.newBuilder() //
                .setOptions(mainQueryOptions) //
                .build("user_id:" + userId);

        final Results<ScoredDocument> docs = getLocationsIdx().search(userLocations);

        final int nbResults = docs.getResults().size();
        if (nbResults > 1) {
            final UnsupportedOperationException e = new UnsupportedOperationException( //
                    String.format( //
                            "Only one document should have been found for the user [%s] and we've found [%s]" //
                            , userId //
                            , nbResults //
                            ));
            log.error(this, e);
            throw e;

        }

        if (nbResults == 1) {
            final ScoredDocument doc = docs.getResults().iterator().next();
            getLocationsIdx().removeAsync(doc.getId());
        }

        final Document.Builder docBuilder = Document.newBuilder();
        docBuilder.addField(Field.newBuilder().setName("user_id").setText(userId));
        docBuilder.addField(Field.newBuilder().setName("locations").setText(locations));
        getLocationsIdx().add(docBuilder.build());

        //
        //
        //
        //

        // TODO PGU geopoints https://developers.google.com/appengine/docs/java/search/overview

    }

}
