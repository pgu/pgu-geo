package pgu.client.menu;

import pgu.client.app.event.GoToContactsEvent;
import pgu.client.app.event.GoToProfileEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationSearchEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.shared.dto.LoginInfo;

import com.google.web.bindery.event.shared.EventBus;

public class MenuActivity implements MenuPresenter //
        , ShowWaitingIndicatorEvent.Handler //
        , HideWaitingIndicatorEvent.Handler //
        , LocationSearchEvent.Handler //
{

    private final MenuView    view;
    private EventBus          eventBus;
    private final LoginInfo   loginInfo;
    private final ClientUtils u = new ClientUtils();

    public MenuActivity(final ClientFactory clientFactory) {
        view = clientFactory.getMenuView();
        loginInfo = clientFactory.getLoginInfo();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        setAppTitle();

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(LocationSearchEvent.TYPE, this);

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
        view.getLocationSearchWidget().setText(event.getText());
    }

}
