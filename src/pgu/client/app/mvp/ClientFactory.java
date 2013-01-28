package pgu.client.app.mvp;

import pgu.client.app.AppState;
import pgu.client.contacts.ContactsView;
import pgu.client.menu.MenuView;
import pgu.client.oauth.OAuthView;
import pgu.client.profile.EditLocationView;
import pgu.client.profile.ProfileView;
import pgu.client.service.ContactsServiceAsync;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.LoginServiceAsync;
import pgu.client.service.ProfileServiceAsync;
import pgu.client.service.PublicProfileServiceAsync;

public interface ClientFactory extends BaseClientFactory {

    AppState getAppState();

    MenuView getMenuView();

    LinkedinServiceAsync getLinkedinService();

    LoginServiceAsync getLoginService();

    ContactsView getContactsView();

    ProfileView getProfileView();

    OAuthView getOAuthView();

    EditLocationView getEditLocationView();

    PublicProfileServiceAsync getPublicProfileService();

    ProfileServiceAsync getProfileService();

    ContactsServiceAsync getContactsService();

}
