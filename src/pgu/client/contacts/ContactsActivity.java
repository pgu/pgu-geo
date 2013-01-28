package pgu.client.contacts;

import java.util.ArrayList;

import pgu.client.app.AppContext;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.ContactsLoadedEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.contacts.event.SaveContactsNumberByCountryEvent;
import pgu.client.contacts.event.SaveFusionUrlsEvent;
import pgu.client.service.ContactsServiceAsync;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.ContactsForCharts;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ContactsActivity extends AbstractActivity implements //
SaveFusionUrlsEvent.Handler //
, SaveContactsNumberByCountryEvent.Handler //
, ChartsApiLoadedEvent.Handler //
, ContactsLoadedEvent.Handler //
{

    private final ClientFactory                  clientFactory;
    private final ContactsView                   view;
    private final AppContext                     ctx;
    private final ClientHelper                    u     = new ClientHelper();
    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();
    private EventBus                             eventBus;

    private final LinkedinServiceAsync           linkedinService;
    private final ContactsServiceAsync           contactsService;

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory, final AppContext ctx) {
        this.clientFactory = clientFactory;
        this.ctx = ctx;
        view = clientFactory.getContactsView();
        contactsService = clientFactory.getContactsService();

        linkedinService = clientFactory.getLinkedinService();
    }

    private boolean hasToSetContacts = false;

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        hRegs.clear();
        hRegs.add(view.addSaveFusionUrlsHandler(this));
        hRegs.add(view.addSaveContactsNumberByCountryHandler(this));

        hRegs.add(eventBus.addHandler(ChartsApiLoadedEvent.TYPE, this));

        hRegs.add(eventBus.addHandler(ContactsLoadedEvent.TYPE, this));

        panel.setWidget(view.asWidget());

        if (isAppReady(ctx)) {
            setContacts();

        } else {
            hasToSetContacts = true;
        }

    }

    private void setContacts() {
        u.fire(eventBus, new ShowWaitingIndicatorEvent());
        view.showLoadingPanel();

        // TODO PGU Nov 10, 2012 get the profile id if not set
        if (clientFactory.getAppState().getUserId() == null) {
            clientFactory.getAppState().setUserId("Qjrp4c3fc3");
        }

        // TODO PGU Nov 20, 2012 use pgu_geo.contacts
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
    public void onStop() {

        for (HandlerRegistration hReg : hRegs) {
            hReg.removeHandler();
            hReg = null;
        }

        hRegs.clear();

        super.onStop();
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
        setContactsAsync();
    }

    @Override
    public void onContactsLoaded(final ContactsLoadedEvent event) {
        setContactsAsync();
    }

    private void setContactsAsync() {
        if (hasToSetContacts && isAppReady(ctx)) {
            hasToSetContacts = false;
            setContacts();
        }
    }

    public boolean areExternalApisLoaded(final AppContext ctx) {
        return ctx.isChartsApiLoaded();
    }

    private boolean isAppReady(final AppContext ctx) {
        return ctx.areContactsLoaded() && areExternalApisLoaded(ctx);
    }

    public void fetchContactsNames() {
        // TODO PGU Jan 28, 2013
        // TODO PGU Jan 28, 2013 obsolete: move the computation of the contacts repartition
        // TODO PGU Jan 28, 2013
        //        linkedinService.fetchContactsNames( //
        //                clientFactory.getAppState().getUserId(), //
        //                new AsyncCallbackApp<Country2ContactNames>(eventBus) {
        //
        //                    @Override
        //                    public void onSuccess(final Country2ContactNames result) {
        //                        view.setContactNames(result);
        //                    }
        //
        //                });
    }

    public void saveChartsPreferences(final String jsonChartTypes) {
        contactsService.saveChartsPreferences( //
                ctx.getProfileId() //
                , jsonChartTypes //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

}
