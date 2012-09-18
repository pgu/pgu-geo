package pgu.client.service;

import pgu.shared.dto.PublicPreferences;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pub")
public interface PublicProfileService extends RemoteService {

    PublicPreferences fetchPublicPreferences(String userId);

    void saveProfile(String json);

}
