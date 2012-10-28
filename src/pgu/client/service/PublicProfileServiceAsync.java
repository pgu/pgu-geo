package pgu.client.service;

import pgu.shared.dto.PublicProfile;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PublicProfileServiceAsync {

    void fetchPreferencesOnly(String userId, AsyncCallback<PublicProfile> asyncCallbackApp);

    void fetchPublicProfileByUrl(String publicUrl, AsyncCallback<PublicProfile> asyncCallbackApp);

    void saveProfile(PublicProfile publicProfile,  AsyncCallback<Void> callbackApp);

    void saveMapPreferences(String userId, String mapPreferences, AsyncCallback<Void> asyncCallbackApp);


}
