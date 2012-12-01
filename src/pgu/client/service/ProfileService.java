package pgu.client.service;

import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("profile")
public interface ProfileService extends RemoteService {

    ProfileLocations fetchProfileLocations(String profileId);

    PublicPreferences fetchPublicPreferences(String profileId);

    void saveLocations(String profileId, String json_copyCacheItems, String json_copyCacheReferential);

    void savePublicProfile(String profileId, String jsonPublicProfile);

    void saveProfile(String profileId, String jsonProfile);

    void savePublicPreferences(String profileId, String jsonPublicPreferences);

    void saveMapPreferences(String profileId, String mapPreferences);

    MapPreferences fetchMapPreferences(String profileId);

}
