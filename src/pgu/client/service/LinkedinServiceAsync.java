package pgu.client.service;

import pgu.shared.dto.AccessToken;
import pgu.shared.dto.Connections;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.RequestToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LinkedinServiceAsync {

    void logInLinkedin(AsyncCallback<Void> asyncCallback);

    void getLinkedinUrlAuthorization(AsyncCallback<OauthAuthorizationStart> asyncCallback);

    void fetchConnections(AccessToken accessToken, AsyncCallback<Connections> asyncCallback);

    void fetchProfile(AccessToken accessToken, AsyncCallback<String> asyncCallbackApp);

    void getAccessToken(String oauthCode, RequestToken requestToken, AsyncCallback<AccessToken> callbackApp);
}
