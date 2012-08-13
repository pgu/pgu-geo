package pgu.client.service;

import pgu.shared.dto.Connections;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.RequestToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LinkedinServiceAsync {

    void logInLinkedin(AsyncCallback<Void> asyncCallback);

    void getLinkedinUrlAuthorization(AsyncCallback<OauthAuthorizationStart> asyncCallback);

    void fetchConnections(String oauthCode, RequestToken requestToken, AsyncCallback<Connections> asyncCallback);
}
