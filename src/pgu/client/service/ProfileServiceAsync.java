package pgu.client.service;

import pgu.shared.model.ProfileLocations;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfileServiceAsync {

    void fetchCustomLocations(String profileId, AsyncCallback<ProfileLocations> asyncCallbackApp);

}
