package pgu.client.menu.ui;

import pgu.client.menu.MenuView;
import pgu.client.menu.event.GoToAppStatsEvent;
import pgu.client.menu.event.GoToContactsEvent;
import pgu.client.menu.event.GoToProfileEvent;
import pgu.client.menu.event.GoToPublicProfileEvent;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class MenuViewImpl extends Composite implements MenuView {

    private static MenuViewImplUiBinder uiBinder = GWT.create(MenuViewImplUiBinder.class);

    interface MenuViewImplUiBinder extends UiBinder<Widget, MenuViewImpl> {
    }

    @UiField
    Brand                     appTitle;
    @UiField
    NavLink
    adminBtn //
    , logoutBtn //
    , goToProfileBtn //
    , goToContactsBtn //
    , goToAppstatsBtn //
    , openPublicProfile //
    ;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        logoutBtn.setVisible(false);
        goToAppstatsBtn.setVisible(false);
    }

    @UiHandler("openPublicProfile")
    public void clickOnOpenPublicProfile(final ClickEvent e) {
        fireEvent(new GoToPublicProfileEvent());
    }

    @UiHandler("goToContactsBtn")
    public void clickGoToContacts(final ClickEvent e) {
        fireEvent(new GoToContactsEvent());
    }

    @UiHandler("goToProfileBtn")
    public void clickGoToProfile(final ClickEvent e) {
        fireEvent(new GoToProfileEvent());
    }

    @UiHandler("goToAppstatsBtn")
    public void clickGoToAppstats(final ClickEvent e) {
        fireEvent(new GoToAppStatsEvent());
    }

    @Override
    public void setIsAdmin(final boolean isAdmin) {
        adminBtn.setVisible(!isAdmin);
        logoutBtn.setVisible(isAdmin);
    }

    @Override
    public void setLogoutUrl(final String logoutUrl) {
        logoutBtn.setHref(logoutUrl);
    }

    @Override
    public void setLoginUrl(final String loginUrl) {
        adminBtn.setHref(loginUrl);
    }

    @Override
    public void setIsSuperAdmin(final boolean isSuperAdmin) {
        goToAppstatsBtn.setVisible(isSuperAdmin);
    }

    @Override
    public HandlerRegistration addGoToProfileHandler(final GoToProfileEvent.Handler handler) {
        return addHandler(handler, GoToProfileEvent.TYPE);
    }

    @Override
    public HandlerRegistration addGoToContactsHandler(final GoToContactsEvent.Handler handler) {
        return addHandler(handler, GoToContactsEvent.TYPE);
    }

    @Override
    public HandlerRegistration addGoToPublicProfileHandler(final GoToPublicProfileEvent.Handler handler) {
        return addHandler(handler, GoToPublicProfileEvent.TYPE);
    }

    @Override
    public HandlerRegistration addGoToAppStatsHandler(final GoToAppStatsEvent.Handler handler) {
        return addHandler(handler, GoToAppStatsEvent.TYPE);
    }

}
