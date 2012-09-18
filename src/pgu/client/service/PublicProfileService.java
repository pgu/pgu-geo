package pgu.client.service;

import pgu.shared.dto.PublicProfile;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pub")
public interface PublicProfileService extends RemoteService {

    PublicProfile fetchPreferencesOnly(String userId);

    PublicProfile fetchPublicProfileByUrl(String publicUrl);

    void saveProfile(PublicProfile publicProfile);


}
