package pgu.client.service;

import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicProfile;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pub")
public interface PublicProfileService extends RemoteService {

    PublicProfile fetchPublicProfileByUrl(String publicUrl);

    PublicContacts fetchPublicContacts(String userId);


}
