package pgu.client.contacts;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.model.Country2ContactNumber;

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

        // TODO PGU Nov 10, 2012 get the profile id if not set
        if (clientFactory.getAppState().getUserId() == null) {
            clientFactory.getAppState().setUserId("Qjrp4c3fc3");
        }

        linkedinService.fetchConnections( //
                clientFactory.getAppState().getAccessToken() //
                , clientFactory.getAppState().getUserId() //
                , new AsyncCallbackApp<Country2ContactNumber>(eventBus) {

                    @Override
                    public void onSuccess(final Country2ContactNumber country2contactNumber) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());

                        view.showCharts(country2contactNumber);
                    }

                });
    }

}
