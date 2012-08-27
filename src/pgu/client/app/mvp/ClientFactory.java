package pgu.client.app.mvp;

import pgu.client.app.AppState;
import pgu.client.app.AppView;
import pgu.client.contacts.ContactsView;
import pgu.client.menu.MenuView;
import pgu.client.oauth.OAuthView;
import pgu.client.profile.EditLocationView;
import pgu.client.profile.ProfileView;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.LoginServiceAsync;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {

    EventBus getEventBus();

    PlaceController getPlaceController();

    void setLoginInfo(LoginInfo loginInfo);

    AppView getAppView();

    AppState getAppState();

    MenuView getMenuView();

    LoginInfo getLoginInfo();

    LinkedinServiceAsync getLinkedinService();

    LoginServiceAsync getLoginService();

    ContactsView getContactsView();

    ProfileView getProfileView();

    OAuthView getOAuthView();

    EditLocationView getEditLocationView();
}
