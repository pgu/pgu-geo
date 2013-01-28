package pgu.client.service;

import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.FusionUrls;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("contacts")
public interface ContactsService extends RemoteService {

    void saveChartsPreferences(String profileId, String jsonChartTypes);

    void saveFusionUrls(String profileId, String jsonFusionUrls);

    void saveContactsNumberByCountry(String profileId, String jsonContactsNumberByCountry);

    void saveContacts(String profileId, String jsonContacts);

    FusionUrls fetchFusionUrls(String profileId);

    ChartsPreferences fetchChartsPreferences(String profileId);

}
