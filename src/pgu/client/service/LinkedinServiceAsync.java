package pgu.client.service;

import pgu.shared.dto.AccessToken;
import pgu.shared.dto.ContactsForCharts;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.Profile;
import pgu.shared.dto.RequestToken;
import pgu.shared.model.Country2ContactNames;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LinkedinServiceAsync {

    void logInLinkedin(AsyncCallback<Void> asyncCallback);

    void getLinkedinUrlAuthorization(AsyncCallback<OauthAuthorizationStart> asyncCallback);

    void fetchConnections(AccessToken accessToken, String userId, AsyncCallback<ContactsForCharts> asyncCallback);

    void fetchProfile(AccessToken accessToken, AsyncCallback<Profile> asyncCallbackApp);

    void getAccessToken(String oauthCode, RequestToken requestToken, AsyncCallback<AccessToken> callbackApp);

    void saveLocations(String userId, final String items2locations, final String referentialLocations,
            AsyncCallback<Void> callbackApp);

    void fetchContactsNames(String userId, AsyncCallback<Country2ContactNames> asyncCallbackApp);

    void saveChartsPreferences(String userId, String jsonChartTypes, AsyncCallback<Void> asyncCallbackApp);

}
