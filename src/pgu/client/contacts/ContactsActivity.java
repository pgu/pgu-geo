package pgu.client.contacts;

import java.util.ArrayList;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.contacts.event.FetchContactsNamesEvent;
import pgu.client.contacts.event.SaveChartsPreferencesEvent;
import pgu.client.contacts.event.SaveFusionUrlsEvent;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.ContactsForCharts;
import pgu.shared.model.Country2ContactNames;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ContactsActivity extends AbstractActivity implements //
FetchContactsNamesEvent.Handler //
, SaveChartsPreferencesEvent.Handler //
, SaveFusionUrlsEvent.Handler //
{

    private final ClientFactory        clientFactory;
    private final ContactsView         view;
    private final LinkedinServiceAsync linkedinService;

    private final ClientUtils          u = new ClientUtils();

    private EventBus                   eventBus;

    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getContactsView();
        linkedinService = clientFactory.getLinkedinService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;

        hRegs.clear();
        hRegs.add(view.addFetchContactsNamesHandler(this));
        hRegs.add(view.addSaveChartsPreferencesHandler(this));
        hRegs.add(view.addSaveFusionUrlsHandler(this));

        panel.setWidget(view.asWidget());

        u.fire(eventBus, new ShowWaitingIndicatorEvent());
        view.showLoadingPanel();

        // TODO PGU Nov 10, 2012 get the profile id if not set
        if (clientFactory.getAppState().getUserId() == null) {
            clientFactory.getAppState().setUserId("Qjrp4c3fc3");
        }

        linkedinService.fetchConnections( //
                clientFactory.getAppState().getAccessToken() //
                , clientFactory.getAppState().getUserId() //
                , new AsyncCallbackApp<ContactsForCharts>(eventBus) {

                    @Override
                    public void onSuccess(final ContactsForCharts country2contactNumber) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());
                        view.showChartsPanel();

                        view.showCharts(country2contactNumber);
                    }

                });
    }

    @Override
    public void onFetchContactsNames(final FetchContactsNamesEvent event) {
        linkedinService.fetchContactsNames( //
                clientFactory.getAppState().getUserId(), //
                new AsyncCallbackApp<Country2ContactNames>(eventBus) {

                    @Override
                    public void onSuccess(final Country2ContactNames result) {
                        view.setContactNames(result);
                    }

                }
                );
    }

    @Override
    public void onStop() {

        for (HandlerRegistration hReg : hRegs) {
            hReg.removeHandler();
            hReg = null;
        }

        hRegs.clear();

        super.onStop();
    }

    @Override
    public void onSaveChartsPreferences(final SaveChartsPreferencesEvent event) {
        linkedinService.saveChartsPreferences( //
                clientFactory.getAppState().getUserId() //
                , event.getChartTypes() //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    @Override
    public void onSaveFusionUrls(final SaveFusionUrlsEvent event) {
        linkedinService.saveFusionUrls( //
                clientFactory.getAppState().getUserId() //
                , event.getFusionUrls() //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

}
