package pgu.client.contacts;

import pgu.client.contacts.event.FetchContactsNamesEvent;
import pgu.shared.dto.ContactsForCharts;
import pgu.shared.model.Country2ContactNames;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget //
, FetchContactsNamesEvent.HasFetchContactsNamesHandlers //
{

    void showCharts(ContactsForCharts contactsForCharts);

    void showLoadingPanel();

    void showChartsPanel();

    void setContactNames(Country2ContactNames names);

}
