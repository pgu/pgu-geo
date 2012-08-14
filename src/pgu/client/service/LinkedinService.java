package pgu.client.service;

import pgu.shared.dto.Connections;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.RequestToken;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("linkedin")
public interface LinkedinService extends RemoteService {

    void logInLinkedin();

    OauthAuthorizationStart getLinkedinUrlAuthorization();

    Connections fetchConnections(String oauthCode, RequestToken requestToken);

    String fetchProfile(String oAuthCode, RequestToken requestToken);
}
