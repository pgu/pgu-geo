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
import pgu.shared.dto.ContactsForCharts;
import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.FusionUrls;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ContactsActivity extends AbstractActivity implements //
ChartsApiLoadedEvent.Handler //
, ContactsLoadedEvent.Handler //
{

    private final ContactsView                   view;
    private final AppContext                     ctx;
    private final ClientHelper                    u     = new ClientHelper();
    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();
    private EventBus                             eventBus;

    private final ContactsServiceAsync           contactsService;

    private boolean hasToShowContacts = false;

    public ContactsActivity(final ContactsPlace place, final ClientFactory clientFactory, final AppContext ctx) {
        this.ctx = ctx;
        view = clientFactory.getContactsView();
        contactsService = clientFactory.getContactsService();
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
        final ContactsForCharts contactsForCharts = new ContactsForCharts();
        // TODO PGU Jan 28, 2013
        // TODO PGU Jan 28, 2013

        //        HERE contactsForCharts.getCountry2ContactNumber()

        view.showContacts(contactsForCharts);

        contactsService.fetchChartsPreferences( //
                ctx.getProfileId() //
                , new AsyncCallbackApp<ChartsPreferences>(eventBus) {

                    @Override
                    public void onSuccess(final ChartsPreferences result) {
                        view.setChartsPreferences(result.getValues());
                    }
                });

        contactsService.fetchFusionUrls( //
                ctx.getProfileId() //
                , new AsyncCallbackApp<FusionUrls>(eventBus) {

                    @Override
                    public void onSuccess(final FusionUrls result) {
                        view.setFusionUrls(result.getValues());
                    }
                });

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
