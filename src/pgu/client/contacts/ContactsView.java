package pgu.client.contacts;

import pgu.shared.dto.Connections;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactsView extends IsWidget {

    void setPresenter(ContactsPresenter presenter);

    void setConnections(Connections connections);

}
