package pgu.client.service;

import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pub")
public interface PublicProfileService extends RemoteService {

    FullPublicProfile fetchPublicProfileByUrl(String publicUrl);

    PublicContacts fetchPublicContacts(String userId);

}
