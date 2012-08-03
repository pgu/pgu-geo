package pgu.client;

import pgu.shared.Connections;
import pgu.shared.OauthAuthorizationStart;
import pgu.shared.RequestToken;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

    void logInLinkedin();

    OauthAuthorizationStart getLinkedinUrlAuthorization();

    Connections fetchConnections(String oauthCode, RequestToken requestToken);
}
