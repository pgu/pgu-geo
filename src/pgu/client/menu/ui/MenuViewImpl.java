package pgu.client.menu.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.menu.MenuPresenter;
import pgu.client.menu.MenuView;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavSearch;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
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
    NavLink                   adminBtn, logoutBtn, goToProfileBtn, goToContactsBtn, goToAppstatsBtn, mapSizeBtn;
    @UiField
    NavSearch                 locationSearchBox;
    @UiField
    Button                    locationSearchBtn, locationSaveBtn;

    private MenuPresenter     presenter;
    private final ClientUtils u              = new ClientUtils();
    private boolean           isMapDisplayed = true;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        mapSizeBtn.getElement().setAttribute("data-toggle", "collapse");
        mapSizeBtn.getElement().setAttribute("data-target", "#map_canvas_container");

        mapSizeBtn.setIcon(IconType.RESIZE_SMALL);
        mapSizeBtn.setText("Hide map");
        isMapDisplayed = true;

        logoutBtn.setVisible(false);

        goToProfileBtn.setVisible(false);
        goToContactsBtn.setVisible(false);
        goToAppstatsBtn.setVisible(false);

        progressBar.setVisible(false);
        locationSaveBtn.setVisible(false);
    }

    @Override
    public void setPresenter(final MenuPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("locationSaveBtn")
    public void clickOnLocationSave(final ClickEvent e) {
        GWT.log("save " + //
                itemId + //
                ", " + locationSearchBox.getTextBox().getText() + //
                ", the found location..." //
        );

        presenter.saveLocationItem(itemId, locationSearchBox.getTextBox().getText());
        locationSaveBtn.setVisible(false);

    }

    @UiHandler("locationSearchBtn")
    public void clickOnLocationSearch(final ClickEvent e) {
        final String locationText = locationSearchBox.getTextBox().getText();
        if (locationText.isEmpty()) {
            return;
        }
        searchLocationAndAddMarker(this, locationText);
    }

    public void showSaveBtn() {
        if (u.isVoid(itemId)) {
            return;
        }

        locationSaveBtn.setVisible(true);
    }

    public static native void searchLocationAndAddMarker(MenuViewImpl menu, String locationText) /*-{

		$wnd.geocoder
				.geocode(
						{
							'address' : locationText
						},
						function(results, status) {

							if (status != $wnd.google.maps.GeocoderStatus.OK) {
								$wnd
										.alert("Geocode was not successful for the following reason: "
												+ status);
								return;
							}

							var loc = results[0].geometry.location;

							$wnd.map.setCenter(loc);
							$wnd.console.log(loc);

							var marker = new $wnd.google.maps.Marker({
								map : $wnd.map,
								position : results[0].geometry.location,
								animation : $wnd.google.maps.Animation.DROP,
								title : locationText
							});

							menu.@pgu.client.menu.ui.MenuViewImpl::showSaveBtn()();

						});

    }-*/;

    @UiHandler("mapSizeBtn")
    public void clickMapSize(final ClickEvent e) {

        if (isMapDisplayed) {
            mapSizeBtn.setIcon(IconType.RESIZE_FULL);
            mapSizeBtn.setText("Show map");
            isMapDisplayed = false;

        } else {
            mapSizeBtn.setIcon(IconType.RESIZE_SMALL);
            mapSizeBtn.setText("Hide map");
            isMapDisplayed = true;

        }
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

    @Override
    public HasText getLocationSearchWidget() {
        return locationSearchBox.getTextBox();
    }

    private String itemId;

    @Override
    public void setItemId(final String id) {
        itemId = id;
    }

}
