package pgu.client.service;

import pgu.shared.Connections;
import pgu.shared.OauthAuthorizationStart;
import pgu.shared.RequestToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GreetingServiceAsync {

    void logInLinkedin(AsyncCallback<Void> asyncCallback);

    void getLinkedinUrlAuthorization(AsyncCallback<OauthAuthorizationStart> asyncCallback);

    void fetchConnections(String oauthCode, RequestToken requestToken, AsyncCallback<Connections> asyncCallback);
}
