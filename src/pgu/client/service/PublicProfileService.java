package pgu.client.service;

import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.PublicMapPreferences;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pub")
public interface PublicProfileService extends RemoteService {

    BasePublicProfile fetchPublicProfileByUrl(String publicUrl);

    PublicContacts fetchPublicContacts(String userId);

    PublicMapPreferences fetchMapPreferences(String profileUrl);


}
