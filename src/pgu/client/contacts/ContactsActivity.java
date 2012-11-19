package pgu.client.contacts;

import java.util.ArrayList;

import pgu.client.Pgu_geo;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.contacts.event.FetchContactsNamesEvent;
import pgu.client.contacts.event.SaveChartsPreferencesEvent;
import pgu.client.contacts.event.SaveContactsNumberByCountryEvent;
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
, SaveContactsNumberByCountryEvent.Handler //
, ShowdownLoadedEvent.Handler //
, MapsApiLoadedEvent.Handler //
, ChartsApiLoadedEvent.Handler //
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

    private boolean hasToSetContacts = false;

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;

        hRegs.clear();
        hRegs.add(view.addFetchContactsNamesHandler(this));
        hRegs.add(view.addSaveChartsPreferencesHandler(this));
        hRegs.add(view.addSaveFusionUrlsHandler(this));
        hRegs.add(view.addSaveContactsNumberByCountryHandler(this));

        hRegs.add(eventBus.addHandler(ShowdownLoadedEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(MapsApiLoadedEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(ChartsApiLoadedEvent.TYPE, this));

        panel.setWidget(view.asWidget());

        if (areApisLoaded()) {
            setContacts();

        } else {
            hasToSetContacts = true;
        }

    }

    private boolean areApisLoaded() {
        return Pgu_geo.isShowdownLoaded //
                && Pgu_geo.isChartsApiLoaded //
                && Pgu_geo.isMapsApiLoaded //
                ;
    }

    private void setContacts() {
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

    @Override
    public void onSaveContactsNumberByCountry(final SaveContactsNumberByCountryEvent event) {
        linkedinService.saveContactsNumberByCountry( //
                clientFactory.getAppState().getUserId() //
                , event.getContactsNumberByCountry() //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    @Override
    public void onChartsApiLoaded(final ChartsApiLoadedEvent event) {
        if (hasToSetContacts && areApisLoaded()) {
            setContacts();
        }
    }

    @Override
    public void onMapsApiLoaded(final MapsApiLoadedEvent event) {
        if (hasToSetContacts && areApisLoaded()) {
            setContacts();
        }
    }

    @Override
    public void onShowdownLoaded(final ShowdownLoadedEvent event) {
        if (hasToSetContacts && areApisLoaded()) {
            setContacts();
        }
    }

}
