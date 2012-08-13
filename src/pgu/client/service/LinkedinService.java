package pgu.client.service;

import pgu.shared.Connections;
import pgu.shared.OauthAuthorizationStart;
import pgu.shared.RequestToken;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("linkedin")
public interface LinkedinService extends RemoteService {

    void logInLinkedin();

    OauthAuthorizationStart getLinkedinUrlAuthorization();

    Connections fetchConnections(String oauthCode, RequestToken requestToken);
}
