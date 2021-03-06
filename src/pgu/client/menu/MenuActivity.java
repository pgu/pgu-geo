package pgu.client.menu;

import pgu.client.app.AppState;
import pgu.client.app.event.FetchLoginInfoEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.contacts.ContactsPlace;
import pgu.client.menu.event.GoToAppStatsEvent;
import pgu.client.menu.event.GoToContactsEvent;
import pgu.client.menu.event.GoToProfileEvent;
import pgu.client.menu.event.GoToPublicProfileEvent;
import pgu.client.profile.ProfilePlace;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class MenuActivity implements //
GoToProfileEvent.Handler //
, GoToContactsEvent.Handler //
, GoToPublicProfileEvent.Handler //
, GoToAppStatsEvent.Handler //
, FetchLoginInfoEvent.Handler //
{

    private final MenuView        view;
    private final ClientFactory   clientFactory;
    private final PlaceController placeController;

    public MenuActivity(final MenuView view, final ClientFactory clientFactory) {
        this.view = view;
        this.clientFactory = clientFactory;
        placeController = clientFactory.getPlaceController();
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        view.addGoToProfileHandler(this);
        view.addGoToContactsHandler(this);
        view.addGoToPublicProfileHandler(this);
        view.addGoToAppStatsHandler(this);
        eventBus.addHandler(FetchLoginInfoEvent.TYPE, this);

        panel.setWidget(view.asWidget());
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
            baseUrl = "http://127.0.0.1:8888/Pgu_geo.html?gwt.codesvr=127.0.0.1:9997";
        } else {
            baseUrl = GWT.getHostPageBaseURL();
        }

        final String publicUrl = baseUrl + "#!public:" + linkedInSuffix;
        Window.open(publicUrl, "public_profile", null);
    }

    @Override
    public void onGoToAppStats(final GoToAppStatsEvent event) {
        Window.open("appstats/", "appstats", null);
    }

    @Override
    public void onFetchLoginInfo(final FetchLoginInfoEvent event) {

        final LoginInfo loginInfo = event.getLoginInfo();

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

}
