package pgu.server.service;

import pgu.client.service.PublicProfileService;
import pgu.server.access.DAO;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.ContactsNumberByCountry;
import pgu.shared.model.FusionUrls;
import pgu.shared.model.PublicProfile;
import pgu.shared.model.UserAndLocations;

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
        final PublicProfile publicProfile = dao.ofy().query(PublicProfile.class).filter("url", publicUrl).get();

        final UserAndLocations userAndLocations = dao.ofy().get(UserAndLocations.class, publicProfile.getUserId());
        publicProfile.setUserAndLocations(userAndLocations);

        return publicProfile;
    }

    @Override
    public void saveMapPreferences(final String userId, final String mapPreferences) {

        final PublicProfile publicProfile = dao.ofy().find(PublicProfile.class, userId);
        if(publicProfile == null) {
            return ;
        }

        publicProfile.setMapPreferences(mapPreferences);
        dao.ofy().async().put(publicProfile);
    }

    @Override
    public PublicContacts fetchPublicContacts(final String userId) {

        final ContactsNumberByCountry contactsNumberByCountry = dao.ofy().find(ContactsNumberByCountry.class, userId);
        final String contactsNumberByCountryValues = contactsNumberByCountry == null ? null : contactsNumberByCountry.getValues();

        final FusionUrls fusionUrls = dao.ofy().find(FusionUrls.class, userId);
        final String fusionUrlValues = fusionUrls == null ? null : fusionUrls.getValues();

        final PublicContacts publicContacts = new PublicContacts();
        publicContacts.setFusionUrls(fusionUrlValues);
        publicContacts.setContactsNumberByCountry(contactsNumberByCountryValues);
        return publicContacts;
    }

}
