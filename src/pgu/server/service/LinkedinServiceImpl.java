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
import pgu.shared.dto.ItemLocation;
import pgu.shared.dto.ItemWithLocation;
import pgu.shared.dto.ItemsWithLocation;
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

    @Override
    public Connections fetchConnections(final AccessToken accessToken) {

        final String body = fetchResponseBody(accessToken, CONNECTIONS_URL);
        return new Gson().fromJson(body, Connections.class);
    }

    private final boolean                        isTest               = true;

    private static HashMap<String, ItemLocation> locationReferentiel  = new HashMap<String, ItemLocation>();
    private static final HashMap<Long, String>   educationId2location = new HashMap<Long, String>();
    static {

        final ItemLocation nantesLocation = new ItemLocation();
        nantesLocation.setName("Nantes, France");
        nantesLocation.setLat("47.218371");
        nantesLocation.setLng("-1.553621");

        locationReferentiel.put(nantesLocation.getName(), nantesLocation);

        educationId2location.put(23039762L, "Rostock, Germany");
        educationId2location.put(23039761L, "Nantes, France");
        educationId2location.put(3392191L, "Nantes, France");
        educationId2location.put(23039657L, "Nantes, France; Madrid, Spain");
    }

    /**
     * https://developer.linkedin.com/documents/profile-api
     */
    @Override
    public Profile fetchProfile(final AccessToken accessToken) {

        // TODO PGU Sep 5, 2012 for an education or a position, we decorate them with a list of locations names

        if (isTest) {

            // "item" can be either an education or a position
            // final HashMap<String, ArrayList<ItemLocation>> itemId2locations = new HashMap<String,
            // ArrayList<ItemLocation>>();

            // final Educations educations = javaProfile.getEducations();
            // final EducationsWithLocation edsLoc = new EducationsWithLocation();
            //
            // for (final Education ed : educations.getValues()) {
            //
            // final EducationWithLocation edLoc = new EducationWithLocation(ed);
            //
            // final String locationName = educationId2location.get(ed.getId());
            // if (!u.isVoid(locationName)) {
            // edLoc.getLocation().setName(locationName);
            // }
            //
            // edsLoc.getValues().add(edLoc);
            // }
            //
            // fillItemId2locations(javaProfile.getPositions(), itemId2locations);
            // fillItemId2locations(edsLoc, itemId2locations);
            //
            // profile.setItemId2locations(new Gson().toJson(itemId2locations));

            final boolean profileIsComingFromLinkedin = true;

            String jsonProfile ;
            if (profileIsComingFromLinkedin) {
                jsonProfile = profileTest();
            } else {
                jsonProfile = profileTest();
            }

            final LinkedinProfile javaProfile = new Gson().fromJson(jsonProfile, LinkedinProfile.class);
            final UserAndLocations userAndLocations = dao.ofy().find(UserAndLocations.class, javaProfile.getId());

            final Profile profile = new Profile();
            profile.setJson(jsonProfile);
            profile.setUserAndLocations(userAndLocations);
            return profile;

        }

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

        final Profile profile = new Profile();
        profile.setJson(fetchResponseBody(accessToken, detailedProfiled));
        return profile;
    }

    private void fillItemId2locations(final ItemsWithLocation items,
            final HashMap<String, ArrayList<ItemLocation>> itemId2locations) {

        if (items != null) {
            for (final ItemWithLocation item : items.getValues()) {

                final ArrayList<ItemLocation> itemLocations = new ArrayList<ItemLocation>();
                itemId2locations.put(String.valueOf(item.getId()), itemLocations);

                final Location location = item.getLocation();
                if (location != null) {
                    final String locName = location.getName();
                    if (!u.isVoid(locName)) {
                        final String[] locationNames = locName.split(";");

                        for (final String _locationName : locationNames) {
                            final String locationName = _locationName.trim();

                            if (locationReferentiel.containsKey(locationName)) {
                                final ItemLocation itemLoc = locationReferentiel.get(locationName);

                                final ItemLocation copy = new ItemLocation();
                                copy.setLat(itemLoc.getLat());
                                copy.setLng(itemLoc.getLng());
                                copy.setName(itemLoc.getName());

                                itemLocations.add(copy);

                            } else {
                                final ItemLocation itLoc = new ItemLocation();
                                itLoc.setName(locationName);
                                itemLocations.add(itLoc);

                            }
                        }
                    }
                }
            }
        }
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

    private String profileTest() {

        final InputStream is = getServletContext().getResourceAsStream("/WEB-INF/pgu/profile.json");
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
        dao.ofy().put(userAndLocations);

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
