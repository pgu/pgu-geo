package pgu.server.service;

import pgu.client.service.PublicProfileService;
import pgu.server.access.DAO;
import pgu.server.utils.AppUtils;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.ContactsNumberByCountry;
import pgu.shared.model.FusionUrls;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Result;

@SuppressWarnings("serial")
public class PublicProfileServiceImpl extends RemoteServiceServlet implements PublicProfileService {

    private final DAO           dao             = new DAO();
    private final AppUtils      u               = new AppUtils();

    @Override
    public BasePublicProfile fetchPublicProfileByUrl(final String publicUrl) {

        //        final PublicProfile publicProfile = dao.ofy().query(PublicProfile.class).filter("url", publicUrl).get();
        //
        //        final UserAndLocations userAndLocations = dao.ofy().get(UserAndLocations.class, publicProfile.getProfileId());
        //        publicProfile.setUserAndLocations(userAndLocations);

        final BasePublicProfile basePublicProfile = dao.ofy().find(BasePublicProfile.class, publicUrl);

        return basePublicProfile;
    }

    @Override
    public PublicContacts fetchPublicContacts(final String userId) {

        final Result<ChartsPreferences> rChartsPreferences = dao.ofy().async().find(ChartsPreferences.class, userId);
        final Result<FusionUrls> rFusionUrls = dao.ofy().async().find(FusionUrls.class, userId);

        final ChartsPreferences chartsPreferences = rChartsPreferences.get();
        final String chartsPreferenceValues = chartsPreferences == null ? null : chartsPreferences.getValues();

        String contactsNumberByCountryValues;

        if (u.isVoid(chartsPreferenceValues) //
                || "[]".equals(chartsPreferenceValues)) {

            contactsNumberByCountryValues = null;

        } else {

            final Result<ContactsNumberByCountry> rContactsNumberByCountry = dao.ofy().async().find(ContactsNumberByCountry.class, userId);
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
