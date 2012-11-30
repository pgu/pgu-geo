package pgu.server.service;

import pgu.client.service.ProfileService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;
import pgu.shared.model.UserAndLocations;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();
    private final DAO           dao             = new DAO();

    //    private static final Index PROFILE_IDX = SearchServiceFactory.getSearchService()
    //            .getIndex(IndexSpec.newBuilder().setName("profile_index"));

    @Override
    public ProfileLocations fetchProfileLocations(final String profileId) {

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
            final PublicPreferences initialPublicPreferences = new PublicPreferences();

            initialPublicPreferences.setProfileId(profileId);
            initialPublicPreferences.setValues("{\"positions\":false,\"educations\":false}");

            dao.ofy().async().put(initialPublicPreferences);

            return initialPublicPreferences;
        }

        return publicPreferences;
    }

    @Override
    public void saveLocations(final String profileId, final String items2locations, final String referentialLocations) {
        log.info(this, "(\nuser[%s]\n%s\n\n%s\n)", profileId, items2locations, referentialLocations);

        final UserAndLocations userAndLocations = new UserAndLocations();
        userAndLocations.setProfileId(profileId);

        userAndLocations.setItems2locations(items2locations);
        userAndLocations.setReferentialLocations(referentialLocations);
        dao.ofy().async().put(userAndLocations);

        // TODO PGU Sep 12, 2012 async: update each location with profile
    }

    @Override
    public void savePublicProfile(final String profileId, final String jsonPublicProfile) {

        final BasePublicProfile publicP = new BasePublicProfile();
        publicP.setProfileId(profileId);

        dao.ofy().async().put(publicP);
    }

    @Override
    public void saveProfile(final String profileId, final String jsonProfile) {

        // TODO PGU save profile with ofy
        // TODO PGU location_idx where each location <-> profile

        //        final Document.Builder docBuilder = Document.newBuilder() //
        //                .addField(Field.newBuilder().setName("profile_id").setText(profileId)) //
        //                .addField(Field.newBuilder().setName("json").setText(jsonProfile)) //
        //                ;
        //
        //        final Document doc = docBuilder.build();
        //        PROFILE_IDX.putAsync(doc);
    }

}
