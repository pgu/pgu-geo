package pgu.client.service;

import pgu.shared.dto.PublicPreferences;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PublicProfileServiceAsync {

    void fetchPublicPreferences(String userId, AsyncCallback<PublicPreferences> asyncCallbackApp);

    void saveProfile(String json, AsyncCallback<Void> callbackApp);

}
