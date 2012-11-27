package pgu.client.service;

import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfileServiceAsync {

    void fetchCustomLocations(String profileId, AsyncCallback<ProfileLocations> asyncCallbackApp);

    void fetchPublicPreferences(String profileId, AsyncCallback<PublicPreferences> asyncCallbackApp);

    void saveLocations(String profileId, String json_copyCacheItems, String json_copyCacheReferential,
            AsyncCallback<Void> asyncCallbackApp);

    void savePublicProfile(String profileId, String jsonPublicProfile, AsyncCallback<Void> asyncCallbackApp);

}
