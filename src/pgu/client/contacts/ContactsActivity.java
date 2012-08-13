package pgu.client.contacts;

import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ContactsActivity extends AbstractActivity implements ContactsPresenter {

    private final ClientFactory clientFactory;
    private final ContactsView  view;

    private final ClientUtils   u = new ClientUtils();

    private EventBus            eventBus;

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getContactsView();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        panel.setWidget(view.asWidget());
    }

}
