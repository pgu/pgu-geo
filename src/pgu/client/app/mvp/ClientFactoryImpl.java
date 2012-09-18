package pgu.client.app.mvp;

import pgu.client.app.AppState;
import pgu.client.app.AppView;
import pgu.client.app.ui.AppViewImpl;
import pgu.client.contacts.ContactsView;
import pgu.client.contacts.ui.ContactsViewImpl;
import pgu.client.menu.MenuView;
import pgu.client.menu.ui.MenuViewImpl;
import pgu.client.oauth.OAuthView;
import pgu.client.oauth.ui.OAuthViewImpl;
import pgu.client.profile.EditLocationView;
import pgu.client.profile.ProfileView;
import pgu.client.profile.ui.EditLocationViewImpl;
import pgu.client.profile.ui.ProfileViewImpl;
import pgu.client.pub.PublicView;
import pgu.client.pub.ui.PublicViewImpl;
import pgu.client.service.LinkedinService;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.LoginService;
import pgu.client.service.LoginServiceAsync;
import pgu.client.service.PublicProfileService;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

    private static EventBus                  eventBus             = new SimpleEventBus();
    private static PlaceController           placeController      = new PlaceController(eventBus);
    private static AppView                   appView              = new AppViewImpl();
    private static MenuView                  menuView             = new MenuViewImpl();
    private static ContactsView              contactsView         = new ContactsViewImpl();
    private static ProfileView               profileView          = new ProfileViewImpl();
    private static OAuthView                 oauthView            = new OAuthViewImpl();
    private static EditLocationView          editLocationView     = new EditLocationViewImpl();
    private static PublicView                publicView           = new PublicViewImpl();

    private static PublicProfileServiceAsync publicProfileService = GWT.create(PublicProfileService.class);
    private static LinkedinServiceAsync      linkedinService      = GWT.create(LinkedinService.class);
    private static LoginServiceAsync         loginService         = GWT.create(LoginService.class);

    private static AppState                  appState             = new AppState();
    private LoginInfo                        loginInfo;

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public AppView getAppView() {
        return appView;
    }

    @Override
    public AppState getAppState() {
        return appState;
    }

    @Override
    public MenuView getMenuView() {
        return menuView;
    }

    @Override
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    @Override
    public LinkedinServiceAsync getLinkedinService() {
        return linkedinService;
    }

    @Override
    public void setLoginInfo(final LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public LoginServiceAsync getLoginService() {
        return loginService;
    }

    @Override
    public ContactsView getContactsView() {
        return contactsView;
    }

    @Override
    public ProfileView getProfileView() {
        return profileView;
    }

    @Override
    public OAuthView getOAuthView() {
        return oauthView;
    }

    @Override
    public EditLocationView getEditLocationView() {
        return editLocationView;
    }

    @Override
    public PublicView getPublicView() {
        return publicView;
    }

    @Override
    public PublicProfileServiceAsync getPublicProfileService() {
        return publicProfileService;
    }

}
