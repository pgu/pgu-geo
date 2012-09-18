package pgu.server.service;

import pgu.client.service.PublicProfileService;
import pgu.server.access.DAO;
import pgu.shared.dto.PublicProfile;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PublicProfileServiceImpl extends RemoteServiceServlet implements PublicProfileService {

    private final DAO           dao             = new DAO();

    @Override
    public PublicProfile fetchPreferencesOnly(final String userId) {

        final PublicProfile publicProfile = dao.ofy().find(PublicProfile.class, userId);
        if(publicProfile == null) {
            return null;
        }

        publicProfile.setProfile("");
        publicProfile.setUrl("");

        return publicProfile;
    }

    @Override
    public void saveProfile(final PublicProfile publicProfile) {
        dao.ofy().async().put(publicProfile);
    }

    @Override
    public PublicProfile fetchPublicProfileByUrl(final String publicUrl) {
        return dao.ofy().query(PublicProfile.class).filter("url", publicUrl).get();
    }


}
