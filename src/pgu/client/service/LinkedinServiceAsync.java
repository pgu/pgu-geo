package pgu.client.service;

import pgu.shared.dto.AccessToken;
import pgu.shared.dto.Connections;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.Profile;
import pgu.shared.dto.PublicProfile;
import pgu.shared.dto.RequestToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LinkedinServiceAsync {

    void logInLinkedin(AsyncCallback<Void> asyncCallback);

    void getLinkedinUrlAuthorization(AsyncCallback<OauthAuthorizationStart> asyncCallback);

    void fetchConnections(AccessToken accessToken, AsyncCallback<Connections> asyncCallback);

    void fetchProfile(AccessToken accessToken, AsyncCallback<Profile> asyncCallbackApp);

    void getAccessToken(String oauthCode, RequestToken requestToken, AsyncCallback<AccessToken> callbackApp);

    void saveLocations(String userId, final String items2locations, final String referentialLocations,
            AsyncCallback<Void> callbackApp);

    void fetchPublicProfile(String publicUrl, AsyncCallback<PublicProfile> asyncCallbackApp);

}
