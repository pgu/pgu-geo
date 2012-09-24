package pgu.client.app.mvp;

import pgu.client.app.AppState;
import pgu.client.contacts.ContactsView;
import pgu.client.menu.MenuView;
import pgu.client.oauth.OAuthView;
import pgu.client.profile.EditLocationView;
import pgu.client.profile.ProfileView;
import pgu.client.pub.PublicMenuView;
import pgu.client.pub.PublicView;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.LoginServiceAsync;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.LoginInfo;

public interface ClientFactory extends BaseClientFactory {

    void setLoginInfo(LoginInfo loginInfo);

    AppState getAppState();

    MenuView getMenuView();

    LoginInfo getLoginInfo();

    LinkedinServiceAsync getLinkedinService();

    LoginServiceAsync getLoginService();

    ContactsView getContactsView();

    ProfileView getProfileView();

    OAuthView getOAuthView();

    EditLocationView getEditLocationView();

    PublicView getPublicView();

    PublicProfileServiceAsync getPublicProfileService();

    PublicMenuView getPublicMenuView();
}
