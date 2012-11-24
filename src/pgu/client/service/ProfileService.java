package pgu.client.service;

import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("profile")
public interface ProfileService extends RemoteService {

    ProfileLocations fetchCustomLocations(String profileId);

    PublicPreferences fetchPublicPreferences(String profileId);

}
