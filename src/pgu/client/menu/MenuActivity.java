package pgu.client.menu;

import pgu.client.app.event.GoToContactsEvent;
import pgu.client.app.event.GoToProfileEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationSaveEvent;
import pgu.client.app.event.LocationSearchEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.shared.dto.LatLng;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;

public class MenuActivity implements MenuPresenter //
        , ShowWaitingIndicatorEvent.Handler //
        , HideWaitingIndicatorEvent.Handler //
        , LocationAddNewEvent.Handler //
        , LocationSearchEvent.Handler //
        , GoToProfileEvent.Handler //
        , GoToContactsEvent.Handler //
{

    private final MenuView      view;
    private EventBus            eventBus;
    private final LoginInfo     loginInfo;
    private final ClientUtils   u = new ClientUtils();
    private final ClientFactory clientFactory;
    private String              itemId;

    public MenuActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getMenuView();
        loginInfo = clientFactory.getLoginInfo();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        setAppTitle();

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(LocationAddNewEvent.TYPE, this);
        eventBus.addHandler(LocationSearchEvent.TYPE, this);
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
    public void onLocationSearch(final LocationSearchEvent event) {
        itemId = event.getItemId();
        view.getLocationSearchWidget().setText(event.getText());
    }

    @Override
    public void saveLocation(final LatLng latLng, final String locationLabel) {
        GWT.log("save " + //
                itemId + //
                ", " + locationLabel + //
                ", the found location: " + //
                latLng //
        );

        u.fire(eventBus, new LocationSaveEvent(latLng, itemId, locationLabel));
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
        itemId = event.getItemId();

        view.showMap();
        view.getLocationSearchWidget().setText("");
        view.getLocationSearchWidget().setFocus(true);
        view.getSaveWidget().setVisible(true);
        view.scrollToTop();

    }

}
