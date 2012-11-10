package pgu.client.contacts;

import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {

    void setPresenter(ContactsPresenter presenter);

    void showCharts(Country2ContactNumber country2contactNumber);

    void showLoadingPanel();

    void showChartsPanel();

}
