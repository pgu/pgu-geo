package pgu.client.menu.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.menu.MenuPresenter;
import pgu.client.menu.MenuView;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl extends Composite implements MenuView {

    private static MenuViewImplUiBinder uiBinder = GWT.create(MenuViewImplUiBinder.class);

    interface MenuViewImplUiBinder extends UiBinder<Widget, MenuViewImpl> {
    }

    @UiField
    Brand                     appTitle;
    @UiField
    ProgressBar               progressBar;
    @UiField
    NavLink                   adminBtn, logoutBtn, goToProfileBtn, goToContactsBtn, goToAppstatsBtn;

    private MenuPresenter     presenter;
    private final ClientUtils u = new ClientUtils();

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        logoutBtn.setVisible(false);

        goToProfileBtn.setVisible(false);
        goToContactsBtn.setVisible(false);
        goToAppstatsBtn.setVisible(false);
        progressBar.setVisible(false);

    }

    @Override
    public void setPresenter(final MenuPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("goToContactsBtn")
    public void clickGoToContacts(final ClickEvent e) {
        presenter.goToContacts();
    }

    @UiHandler("goToProfileBtn")
    public void clickGoToProfile(final ClickEvent e) {
        presenter.goToProfile();
    }

    @UiHandler("goToAppstatsBtn")
    public void clickGoToAppstats(final ClickEvent e) {
        Window.open("appstats/", "appstats", null);
    }

    @Override
    public HasVisibility getWaitingIndicator() {
        return progressBar;
    }

    @Override
    public LogWidget getLoginWidget() {
        return new LogWidget() {

            @Override
            public void setTargetHistoryToken(final String targetHistoryToken) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void setHref(final String href) {
                adminBtn.setHref(href);
            }

            @Override
            public String getTargetHistoryToken() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getHref() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isVisible() {
                return adminBtn.isVisible();
            }

            @Override
            public void setVisible(final boolean visible) {
                adminBtn.setVisible(visible);
            }

        };
    }

    @Override
    public LogWidget getLogoutWidget() {
        return new LogWidget() {

            @Override
            public void setTargetHistoryToken(final String targetHistoryToken) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void setHref(final String href) {
                logoutBtn.setHref(href);
            }

            @Override
            public String getTargetHistoryToken() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getHref() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isVisible() {
                return logoutBtn.isVisible();
            }

            @Override
            public void setVisible(final boolean visible) {
                logoutBtn.setVisible(visible);
            }

        };
    }

    @Override
    public HasVisibility getProfileWidget() {
        return goToProfileBtn;
    }

    @Override
    public HasVisibility getAppstatsWidget() {
        return goToAppstatsBtn;
    }

    @Override
    public HasVisibility getContactsWidget() {
        return goToContactsBtn;
    }

}
