package pgu.client.service;

import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.FusionUrls;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContactsServiceAsync {

    void saveChartsPreferences(String userId, String jsonChartTypes, AsyncCallback<Void> asyncCallbackApp);

    void saveFusionUrls(String userId, String jsonFusionUrls, AsyncCallback<Void> callback);

    void saveContactsNumberByCountry(String profileId, String jsonContactsNumberByCountry, AsyncCallback<Void> callback);

    void saveContacts(String profileId, String jsonContacts, AsyncCallback<Void> asyncCallbackApp);

    void fetchFusionUrls(String profileId, AsyncCallback<FusionUrls> asyncCallbackApp);

    void fetchChartsPreferences(String profileId, AsyncCallback<ChartsPreferences> asyncCallbackApp);

}
