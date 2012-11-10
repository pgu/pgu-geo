package pgu.client.contacts;

import pgu.client.contacts.event.FetchContactsNamesEvent;
import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget //
, FetchContactsNamesEvent.HasFetchContactsNamesHandlers //
{

    void showCharts(Country2ContactNumber country2contactNumber);

    void showLoadingPanel();

    void showChartsPanel();

}
