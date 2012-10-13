package pgu.client.menu;

import pgu.client.app.AppState;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.client.contacts.ContactsPlace;
import pgu.client.menu.event.GoToContactsEvent;
import pgu.client.menu.event.GoToProfileEvent;
import pgu.client.menu.event.GoToPublicProfileEvent;
import pgu.client.profile.ProfilePlace;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.web.bindery.event.shared.EventBus;

public class MenuActivity implements //
ShowWaitingIndicatorEvent.Handler //
, HideWaitingIndicatorEvent.Handler //
, LocationAddNewEvent.Handler //
, LocationShowOnMapEvent.Handler //
, GoToProfileEvent.Handler //
, GoToContactsEvent.Handler //
, GoToPublicProfileEvent.Handler //
{

    private final MenuView             view;
    private EventBus                   eventBus;
    private final LoginInfo            loginInfo;
    private final ClientUtils          u = new ClientUtils();
    private final ClientFactory        clientFactory;
    private final PlaceController placeController;

    public MenuActivity(final ClientFactory clientFactory, final PlaceController placeController) {
        this.clientFactory = clientFactory;
        view = clientFactory.getMenuView();
        loginInfo = clientFactory.getLoginInfo();
        this.placeController = placeController;
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(LocationAddNewEvent.TYPE, this);
        eventBus.addHandler(LocationShowOnMapEvent.TYPE, this);

        view.addGoToProfileHandler(this);
        view.addGoToContactsHandler(this);
        view.addGoToPublicProfileHandler(this);

        final boolean isAdmin = loginInfo.isLoggedIn();
        view.setIsAdmin(isAdmin);

        if (isAdmin) {
            view.setLogoutUrl(loginInfo.getLogoutUrl());

        } else {
            view.setLoginUrl(loginInfo.getLoginUrl());
        }


        final boolean isSuperAdmin = isAdmin //
                && "guilcher.pascal.dev@gmail.com".equals(loginInfo.getEmailAddress());

        view.setIsSuperAdmin(isSuperAdmin);
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
    public void onGoToContacts(final GoToContactsEvent event) {
        placeController.goTo(new ContactsPlace());
    }

    @Override
    public void onGoToProfile(final GoToProfileEvent event) {
        placeController.goTo(new ProfilePlace());
    }

    @Override
    public void onGoToPublicProfile(final GoToPublicProfileEvent event) {

        final AppState appState = clientFactory.getAppState();
        final String publicProfileUrl = appState.getPublicProfileUrl();

        final int length = "http://www.linkedin.com/".length();
        final String linkedInSuffix = publicProfileUrl.substring(length);

        String baseUrl = "";
        if (Location.getQueryString().contains("gwt.codesvr")) {
            baseUrl ="http://127.0.0.1:8888/Pgu_contacts.html?gwt.codesvr=127.0.0.1:9997";
        } else {
            baseUrl = GWT.getHostPageBaseURL();
        }

        final String publicUrl = baseUrl + "#PublicPlace:" + linkedInSuffix;
        Window.open(publicUrl, "public_profile", null);
    }

}
