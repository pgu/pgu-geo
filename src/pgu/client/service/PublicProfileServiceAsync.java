package pgu.client.service;

import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PublicProfileServiceAsync {

    void fetchPublicProfileByUrl(String publicUrl, AsyncCallback<BasePublicProfile> asyncCallbackApp);

    void fetchPublicContacts(String userId, AsyncCallback<PublicContacts> asyncCallbackApp);

}
