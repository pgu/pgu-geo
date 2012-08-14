package pgu.client.contacts;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.Connections;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ContactsActivity extends AbstractActivity implements ContactsPresenter {

    private final ClientFactory        clientFactory;
    private final ContactsView         view;
    private final LinkedinServiceAsync linkedinService;

    private final ClientUtils          u = new ClientUtils();

    private EventBus                   eventBus;

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getContactsView();
        linkedinService = clientFactory.getLinkedinService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        u.fire(eventBus, new ShowWaitingIndicatorEvent());
        linkedinService.fetchConnections( //
                clientFactory.getAppState().getOAuthCode() //
                , clientFactory.getAppState().getRequestToken() //
                , new AsyncCallbackApp<Connections>(eventBus) {

                    @Override
                    public void onSuccess(final Connections connections) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());

                        view.setConnections(connections);
                    }

                });
    }
}
