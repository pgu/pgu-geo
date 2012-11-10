package pgu.client.contacts;

import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {

    void showCharts(Country2ContactNumber country2contactNumber);

    void showLoadingPanel();

    void showChartsPanel();

}
