package pgu.server.service;

import pgu.client.service.ProfileService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();
    private final DAO           dao             = new DAO();

    @Override
    public ProfileLocations fetchCustomLocations(final String profileId) {

        final ProfileLocations profileLocations = dao.ofy().find(ProfileLocations.class, profileId);
        if (profileLocations == null) {
            return new ProfileLocations();
        }

        return profileLocations;
    }

    @Override
    public PublicPreferences fetchPublicPreferences(final String profileId) {

        final PublicPreferences publicPreferences = dao.ofy().find(PublicPreferences.class, profileId);
        if (publicPreferences == null) {
            return new PublicPreferences();
        }

        return publicPreferences;
    }

}
