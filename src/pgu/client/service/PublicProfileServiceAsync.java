package pgu.client.service;

import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PublicProfileServiceAsync {

    void fetchPublicProfileByUrl(String publicUrl, AsyncCallback<FullPublicProfile> asyncCallbackApp);

    void fetchPublicContacts(String userId, AsyncCallback<PublicContacts> asyncCallbackApp);

}
