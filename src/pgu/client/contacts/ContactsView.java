package pgu.client.contacts;

import pgu.shared.dto.ContactsForCharts;
import pgu.shared.model.Country2ContactNames;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {

    void showCharts(ContactsForCharts contactsForCharts);

    void showChartsPanel();

    void setContactNames(Country2ContactNames names);

    void setPresenter(ContactsActivity contactsActivity);

    boolean areContactsSetInView();

    String getJsonRawContacts();

    void showContacts();

    void onFetchChartsPreferencesSuccess(String jsonChartsPreferences);

    void onFetchFusionUrlsSuccess(String jsonFusionUrls);

}
