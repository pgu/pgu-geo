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
import pgu.client.signin.SigninView;

public interface ClientFactory extends BaseClientFactory {

    AppState getAppState();

    MenuView getMenuView();

    LinkedinServiceAsync getLinkedinService();

    LoginServiceAsync getLoginService();

    ContactsView getContactsView();

    ProfileView getProfileView();

    OAuthView getOAuthView();

    EditLocationView getEditLocationView();

    PublicView getPublicView();

    PublicProfileServiceAsync getPublicProfileService();

    PublicMenuView getPublicMenuView();

    SigninView getSigninView();
}
