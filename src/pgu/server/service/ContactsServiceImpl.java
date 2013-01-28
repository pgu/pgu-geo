package pgu.server.service;

import pgu.client.service.ContactsService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.model.BaseContacts;
import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.ContactsNumberByCountry;
import pgu.shared.model.FusionUrls;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ContactsServiceImpl extends RemoteServiceServlet implements ContactsService {

    private final DAO           dao             = new DAO();
    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();

    @Override
    public void saveChartsPreferences(final String profileId, final String jsonChartTypes) {
        final ChartsPreferences chartsPreferences = new ChartsPreferences();
        chartsPreferences.setProfileId(profileId);
        chartsPreferences.setValues(jsonChartTypes);
        dao.ofy().async().put(chartsPreferences);
    }

    @Override
    public void saveFusionUrls(final String profileId, final String jsonFusionUrls) {

        if (u.isVoid(jsonFusionUrls) //
                || "[]".equals(jsonFusionUrls)) {

            dao.ofy().async().delete(FusionUrls.class, profileId);
            return;
        }

        final FusionUrls fusionUrls = new FusionUrls();
        fusionUrls.setProfileId(profileId);
        fusionUrls.setValues(jsonFusionUrls);
        dao.ofy().async().put(fusionUrls);
    }

    @Override
    public void saveContactsNumberByCountry(final String profileId, final String jsonContactsNumberByCountry) {

        if (u.isVoid(jsonContactsNumberByCountry) //
                || "[]".equals(jsonContactsNumberByCountry)) {

            dao.ofy().async().delete(ContactsNumberByCountry.class, profileId);
            return;
        }

        final ContactsNumberByCountry contactsNumberByCountry = new ContactsNumberByCountry();
        contactsNumberByCountry.setProfileId(profileId);
        contactsNumberByCountry.setValues(jsonContactsNumberByCountry);
        dao.ofy().async().put(contactsNumberByCountry);
    }

    @Override
    public void saveContacts(final String profileId, final String jsonContacts) {

        final BaseContacts baseContacts = new BaseContacts();
        baseContacts.setProfileId(profileId);
        baseContacts.setValue(jsonContacts);

        dao.ofy().async().put(baseContacts);

        // TODO PGU Jan 28, 2013 jobs to update the contacts
    }

}
