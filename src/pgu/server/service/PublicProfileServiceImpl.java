package pgu.server.service;

import pgu.client.service.PublicProfileService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.ContactsNumberByCountry;
import pgu.shared.model.FusionUrls;
import pgu.shared.model.ProfileUrl;
import pgu.shared.model.PublicLocations;
import pgu.shared.model.PublicMapPreferences;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Result;

@SuppressWarnings("serial")
public class PublicProfileServiceImpl extends RemoteServiceServlet implements PublicProfileService {

    private final DAO           dao             = new DAO();
    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();

    @Override
    public FullPublicProfile fetchPublicProfileByUrl(final String profileUrl) {

        //        final PublicProfile publicProfile = dao.ofy().query(PublicProfile.class).filter("url", publicUrl).get();
        //
        //        final UserAndLocations userAndLocations = dao.ofy().get(UserAndLocations.class, publicProfile.getProfileId());
        //        publicProfile.setUserAndLocations(userAndLocations);

        // TODO PGU Feb 27, 2013
        // TODO PGU Feb 27, 2013  move the dto to a domain object, and save it when editing the profile
        // TODO PGU Feb 27, 2013

        final BasePublicProfile basePublicProfile = dao.ofy().find(BasePublicProfile.class, profileUrl);
        final PublicLocations publicLocations = dao.ofy().find(PublicLocations.class, profileUrl);
        final PublicMapPreferences publicMapPreferences = dao.ofy().find(PublicMapPreferences.class, profileUrl);

        final FullPublicProfile fullPublic = new FullPublicProfile();
        fullPublic.setBasePublicProfile(basePublicProfile);
        fullPublic.setPublicLocations(publicLocations);
        fullPublic.setPublicMapPreferences(publicMapPreferences);

        return fullPublic;
    }

    @Override
    public PublicContacts fetchPublicContacts(final String publicUrl) {

        // TODO PGU Feb 27, 2013
        // TODO PGU Feb 27, 2013  move this stuff in edition of contacts
        // TODO PGU Feb 27, 2013  move the dto to a domain object, and save it when editing the profile
        // TODO PGU Feb 27, 2013

        final ProfileUrl profileUrl = dao.ofy().query(ProfileUrl.class).filter("value", publicUrl).get();
        if (profileUrl == null) {
            return null;
        }

        final String profileId = profileUrl.getProfileId();

        final Result<ChartsPreferences> rChartsPreferences = dao.ofy().async().find(ChartsPreferences.class, profileId);
        final Result<FusionUrls> rFusionUrls = dao.ofy().async().find(FusionUrls.class, profileId);

        final ChartsPreferences chartsPreferences = rChartsPreferences.get();
        final String chartsPreferenceValues = chartsPreferences == null ? null : chartsPreferences.getValues();

        String contactsNumberByCountryValues;

        if (u.isVoid(chartsPreferenceValues) //
                || "[]".equals(chartsPreferenceValues)) {

            contactsNumberByCountryValues = null;

        } else {

            final Result<ContactsNumberByCountry> rContactsNumberByCountry = dao.ofy().async().find(ContactsNumberByCountry.class, profileId);
            final ContactsNumberByCountry contactsNumberByCountry = rContactsNumberByCountry.get();
            contactsNumberByCountryValues = contactsNumberByCountry == null ? null : contactsNumberByCountry.getValues();
        }

        final FusionUrls fusionUrls = rFusionUrls.get();
        final String fusionUrlValues = fusionUrls == null ? null : fusionUrls.getValues();

        final PublicContacts publicContacts = new PublicContacts();
        publicContacts.setFusionUrls(fusionUrlValues);
        publicContacts.setContactsNumberByCountry(contactsNumberByCountryValues);
        publicContacts.setChartsPreferences(chartsPreferenceValues);
        return publicContacts;
    }

}
