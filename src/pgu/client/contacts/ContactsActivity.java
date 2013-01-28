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
import pgu.client.service.ContactsServiceAsync;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.ContactsForCharts;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ContactsActivity extends AbstractActivity implements //
ChartsApiLoadedEvent.Handler //
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

    private boolean hasToShowContacts = false;

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory, final AppContext ctx) {
        this.clientFactory = clientFactory;
        this.ctx = ctx;
        view = clientFactory.getContactsView();
        contactsService = clientFactory.getContactsService();

        linkedinService = clientFactory.getLinkedinService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        hRegs.clear();

        hRegs.add(eventBus.addHandler(ChartsApiLoadedEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(ContactsLoadedEvent.TYPE, this));

        panel.setWidget(view.asWidget());

        if (isAppReady(ctx)) {
            showContacts();

        } else {
            hasToShowContacts = true;
        }

    }

    public void showWaitingIndicator() {
        u.fire(eventBus, new ShowWaitingIndicatorEvent());
    }

    public void hideWaitingIndicator() {
        u.fire(eventBus, new HideWaitingIndicatorEvent());
    }

    private void showContacts() {

        if (view.areContactsSetInView()) {
            return;
        }

        view.showLoadingPanel();

        // TODO PGU Jan 28, 2013
        // TODO PGU Jan 28, 2013
        // TODO PGU Nov 20, 2012 use pgu_geo.contacts
        // TODO PGU Jan 28, 2013 dispatch contacts by locations
        final ContactsForCharts country2contactNumber = new ContactsForCharts();
        // TODO PGU Jan 28, 2013
        // TODO PGU Jan 28, 2013

        view.showContacts(country2contactNumber);

        contactsService.saveContacts( //
                //
                ctx.getProfileId() //
                , getJsonContacts() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // do nothing
                    }
                });

        //        linkedinService.fetchConnections( //
        //                clientFactory.getAppState().getAccessToken() //
        //                , clientFactory.getAppState().getUserId() //
        //                , new AsyncCallbackApp<ContactsForCharts>(eventBus) {
        //
        //                    @Override
        //                    public void onSuccess(final ContactsForCharts country2contactNumber) {
        //                        u.fire(eventBus, new HideWaitingIndicatorEvent());
        //                        view.showChartsPanel();
        //
        //                        view.showCharts(country2contactNumber);
        //                    }
        //
        //                });
    }

    private String getJsonContacts() {
        return view.getJsonRawContacts();
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
    public void onChartsApiLoaded(final ChartsApiLoadedEvent event) {
        showContactsAsync();
    }

    @Override
    public void onContactsLoaded(final ContactsLoadedEvent event) {
        showContactsAsync();
    }

    private void showContactsAsync() {
        if (hasToShowContacts && isAppReady(ctx)) {
            hasToShowContacts = false;
            showContacts();
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

    public void saveFusionUrls(final String jsonFusionUrls) {
        contactsService.saveFusionUrls( //
                ctx.getProfileId() //
                , jsonFusionUrls //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    public void saveContactsNumberByCountry(final String jsonContactsNumberByCountry) {
        contactsService.saveContactsNumberByCountry( //
                ctx.getProfileId() //
                , jsonContactsNumberByCountry //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

}
