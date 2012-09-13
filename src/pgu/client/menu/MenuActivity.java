package pgu.client.menu;

import pgu.client.app.event.GoToContactsEvent;
import pgu.client.app.event.GoToProfileEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.menu.ui.MenuViewUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.LoginInfo;

import com.google.web.bindery.event.shared.EventBus;

public class MenuActivity implements MenuPresenter //
, ShowWaitingIndicatorEvent.Handler //
, HideWaitingIndicatorEvent.Handler //
, LocationAddNewEvent.Handler //
, LocationShowOnMapEvent.Handler //
, GoToProfileEvent.Handler //
, GoToContactsEvent.Handler //
{

    private final MenuView             view;
    private EventBus                   eventBus;
    private final LoginInfo            loginInfo;
    private final ClientUtils          u = new ClientUtils();
    private final ClientFactory        clientFactory;
    private String                     itemConfigId;
    private final LinkedinServiceAsync linkedinService;

    public MenuActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getMenuView();
        loginInfo = clientFactory.getLoginInfo();
        linkedinService = clientFactory.getLinkedinService();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        setAppTitle();

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(LocationAddNewEvent.TYPE, this);
        eventBus.addHandler(LocationShowOnMapEvent.TYPE, this);
        eventBus.addHandler(GoToProfileEvent.TYPE, this);
        eventBus.addHandler(GoToContactsEvent.TYPE, this);

        view.getProfileWidget().setVisible(true);
        view.getContactsWidget().setVisible(true);

        final boolean isAdmin = loginInfo.isLoggedIn();

        view.getLoginWidget().setVisible(!isAdmin);
        view.getLogoutWidget().setVisible(isAdmin);

        if (isAdmin) {
            view.getLogoutWidget().setHref(loginInfo.getLogoutUrl());

        } else {
            view.getLoginWidget().setHref(loginInfo.getLoginUrl());
        }

        final boolean isSuperAdmin = isAdmin //
                && "guilcher.pascal.dev@gmail.com".equals(loginInfo.getEmailAddress());

        view.getAppstatsWidget().setVisible(isSuperAdmin);

    }

    public void setAppTitle() {
        // TODO PGU Aug 13, 2012 add feature of open id with linkedin
        // TODO PGU Aug 13, 2012 set name of the linked logged user
    }

    @Override
    public void goToProfile() {
        u.fire(eventBus, new GoToProfileEvent());
    }

    @Override
    public void goToContacts() {
        u.fire(eventBus, new GoToContactsEvent());
    }

    @Override
    public void onHideWaitingIndicator(final HideWaitingIndicatorEvent event) {
        view.getWaitingIndicator().setVisible(false);
    }

    @Override
    public void onShowWaitingIndicator(final ShowWaitingIndicatorEvent event) {
        view.getWaitingIndicator().setVisible(true);
    }

    @Override
    public void onLocationShowOnMap(final LocationShowOnMapEvent event) {
        view.showOnMap(event.getItemLocation());
    }

    @Override
    public void onGoToContacts(final GoToContactsEvent event) {
        view.getProfilePlayMenuWidget().setVisible(false);
    }

    @Override
    public void onGoToProfile(final GoToProfileEvent event) {
        view.getProfilePlayMenuWidget().init();
        view.getProfilePlayMenuWidget().setVisible(true);
    }

    @Override
    public void onLocationAddNew(final LocationAddNewEvent event) {
        itemConfigId = event.getItemConfigId();

        view.showMap();
        view.getLocationSearchWidget().setText("");
        view.getLocationSearchWidget().setFocus(true);
        view.getSaveWidget().setVisible(true);

    }

    @Override
    public void saveLocation(final String itemLocation) {

        if (u.isVoid(itemConfigId)) {
            return;
        }

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        // TODO PGU Sep 13, 2012 if same lat/lng, do something else

        MenuViewUtils.addNewLocation(this, itemConfigId, itemLocation);
    }

    public void saveLocationService(final String itemLocation) {

        linkedinService.saveLocations( //
                //
                clientFactory.getAppState().getUserId() //
                , LocationsUtils.json_items2locations() //
                , LocationsUtils.json_referentialLocations() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());

                        view.getSaveWidget().setVisible(false);
                        u.fire(eventBus, new LocationsSuccessSaveEvent(itemConfigId));

                        final StringBuilder msg = new StringBuilder();
                        msg.append("The location \"");
                        msg.append(itemLocation);
                        msg.append("\" has been successfully added.");

                        u.fire(eventBus, new NotificationEvent(Level.SUCCESS, msg.toString()));
                    }
                    // TODO PGU Sep 13, 2012 TODO on failure remove the location from the caches
                });

    }

    @Override
    public void showNotificationWarning(final String msg) {
        u.fire(eventBus, new NotificationEvent(Level.WARNING, msg));
    }

}
