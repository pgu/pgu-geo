package pgu.client.service;

import pgu.shared.dto.AccessToken;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.Profile;
import pgu.shared.dto.RequestToken;
import pgu.shared.model.Country2ContactNames;
import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("linkedin")
public interface LinkedinService extends RemoteService {

    void logInLinkedin();

    OauthAuthorizationStart getLinkedinUrlAuthorization();

    Country2ContactNumber fetchConnections(AccessToken accessToken, String userId);

    Profile fetchProfile(AccessToken accessToken);

    AccessToken getAccessToken(String oauthCode, RequestToken requestToken);

    void saveLocations(String userId, final String items2locations, final String referentialLocations);

    Country2ContactNames fetchContactsNames(String userId);

}
