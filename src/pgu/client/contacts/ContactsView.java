package pgu.client.contacts;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {

    void showChartsPanel();

    void setPresenter(ContactsActivity contactsActivity);

    boolean areContactsSetInView();

    String getJsonRawContacts();

    void showContacts();

    void onFetchChartsPreferencesSuccess(String jsonChartsPreferences);

    void onFetchFusionUrlsSuccess(String jsonFusionUrls);

}
