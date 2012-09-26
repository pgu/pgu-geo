package pgu.client.menu.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.MarkersUtils;
import pgu.client.app.utils.MovieUtils;
import pgu.client.menu.MenuPresenter;
import pgu.client.menu.MenuView;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavSearch;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
    NavLink                   adminBtn, logoutBtn, goToProfileBtn, goToContactsBtn, goToAppstatsBtn, mapSizeBtn //
    , past2prstBtn, prst2pastBtn //
    , stepBwdBtn, stepFwdBtn //
    , playBtn, pauseBtn, stopBtn //
    , clearMarkersBtn, openPublicProfile //
    ;
    @UiField
    NavSearch                 locationSearchBox;
    @UiField
    Button                    locationSearchBtn, locationSaveBtn //
    ;
    @UiField
    Nav                       profilePlayMenu;

    private MenuPresenter     presenter;

    private final ClientUtils u              = new ClientUtils();
    private boolean           isMapDisplayed = true;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        locationSearchBox.getTextBox().addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(final KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    event.preventDefault();
                    event.stopPropagation();

                    searchLocation();
                }
            }
        });

        mapSizeBtn.getElement().setAttribute("data-toggle", "collapse");
        mapSizeBtn.getElement().setAttribute("data-target", "#pgu_geo_profile_map_container");

        mapSizeBtn.setIcon(IconType.RESIZE_SMALL);
        mapSizeBtn.setText("Hide map");
        isMapDisplayed = true;

        logoutBtn.setVisible(false);

        goToProfileBtn.setVisible(false);
        goToContactsBtn.setVisible(false);
        goToAppstatsBtn.setVisible(false);

        progressBar.setVisible(false);
        locationSaveBtn.setVisible(false);

        past2prstBtn.setTitle(MSG_FROM_PAST_TO_PRESENT);
        prst2pastBtn.setTitle(MSG_FROM_PRESENT_TO_PAST);

        isPastToPresent = true;
        past2prstBtn.setActive(true);

        stepBwdBtn.setTitle(MSG_GO_TO_PREVIOUS_LOCATION);
        stepFwdBtn.setTitle(MSG_GO_TO_NEXT_LOCATION);
        stepBwdBtn.setVisible(false);

        playBtn.setTitle(MSG_PLAY_PROFILE_LOCATIONS);
        pauseBtn.setTitle(MSG_PAUSE);

        getProfilePlayMenuWidget().init();
    }

    private static final String MSG_FROM_PAST_TO_PRESENT    = "From past to present";
    private static final String MSG_FROM_PRESENT_TO_PAST    = "From present to past";
    private static final String MSG_GO_TO_PREVIOUS_LOCATION = "Go to previous location";
    private static final String MSG_GO_TO_NEXT_LOCATION     = "Go to next location";
    private static final String MSG_PLAY_PROFILE_LOCATIONS  = "Play profile locations";
    private static final String MSG_PAUSE                   = "Pause";

    private String        lastSearchItemLocation      = null;

    @Override
    public void setPresenter(final MenuPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("openPublicProfile")
    public void clickOnOpenPublicProfile(final ClickEvent e) {
        presenter.openPublicProfile();
    }

    @UiHandler("clearMarkersBtn")
    public void clickOnClearMarkersBtn(final ClickEvent e) {
        MarkersUtils.deleteMarkers();
    }

    @UiHandler("locationSaveBtn")
    public void clickOnLocationSave(final ClickEvent e) {

        if (u.isVoid(lastSearchItemLocation)) {

            lastSearchItemLocation = null;
            return;
        }

        presenter.saveLocation(lastSearchItemLocation);
    }

    @UiHandler("locationSearchBtn")
    public void clickOnLocationSearch(final ClickEvent e) {
        searchLocation();
    }

    private void searchLocation() {
        final String locationText = locationSearchBox.getTextBox().getText();

        if (u.isVoid(locationText)) {
            lastSearchItemLocation = null;
            return;
        }

        MenuViewUtils.searchLocationAndAddMarker(this, locationText);
    }

    @UiHandler("prst2pastBtn")
    public void clickOnPrst2Past(final ClickEvent e) {

        prst2pastBtn.setActive(true);

        past2prstBtn.setActive(false);
        isPastToPresent = false;
    }

    @UiHandler("past2prstBtn")
    public void clickOnPast2Prst(final ClickEvent e) {

        past2prstBtn.setActive(true);
        isPastToPresent = true;

        prst2pastBtn.setActive(false);
    }

    @UiHandler("stopBtn")
    public void clickOnStopProfile(final ClickEvent e) {
        clickOnPause();
        isPausing = false;

        MarkersUtils.deleteMarkers();
        MovieUtils.initIndex(isPastToPresent);
    }

    @UiHandler("stepBwdBtn")
    public void clickOnStepBwdBtn(final ClickEvent e) {
        clickOnPause();
        MovieUtils.decrementIndex(isPastToPresent);
        showProfileItemOnMap();
    }

    @UiHandler("stepFwdBtn")
    public void clickOnStepFwdBtn(final ClickEvent e) {
        clickOnPause();
        MovieUtils.incrementIndex(isPastToPresent);
        showProfileItemOnMap();
    }

    @UiHandler("pauseBtn")
    public void clickOnPauseProfile(final ClickEvent e) {
        clickOnPause();
    }

    private void clickOnPause() {
        pauseBtn.setVisible(false);
        isPlayingProfile = false;
        isPausing = true;

        playBtn.setVisible(true);
    }

    private boolean isPastToPresent  = true;
    private boolean isPlayingProfile = false;
    private boolean isPausing        = false;

    @UiHandler("playBtn")
    public void clickOnPlayProfile(final ClickEvent e) {
        playBtn.setVisible(false);
        pauseBtn.setVisible(true);

        isPlayingProfile = true;

        if (!isPausing) {
            MarkersUtils.deleteMarkers();
            MovieUtils.initIndex(isPastToPresent);
        }

        isPausing = false;

        Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

            @Override
            public boolean execute() {
                return goToNextProfileItemOnMap();
            }

        }, 3000);

        goToNextProfileItemOnMap();
    }

    private boolean goToNextProfileItemOnMap() {

        if (isPlayingProfile) {

            MovieUtils.incrementIndex(isPastToPresent);
            final boolean isDone = showProfileItemOnMap();

            return !isDone;
        }

        return false;
    }

    private boolean showProfileItemOnMap() {
        final boolean isDone = MovieUtils.showProfileItemOnMap(isPastToPresent);

        if (isDone) {
            clickOnPause();
            isPausing = false;
        }

        stepFwdBtn.setVisible(MovieUtils.showFwdBtn(isPastToPresent));
        stepBwdBtn.setVisible(MovieUtils.showBwdBtn(isPastToPresent));

        return isDone;
    }

    public void cacheLastSearchedLocation(final String name) {
        lastSearchItemLocation = name;
    }

    @UiHandler("mapSizeBtn")
    public void clickMapSize(final ClickEvent e) {

        if (isMapDisplayed) {
            mapSizeBtn.setIcon(IconType.RESIZE_FULL);
            mapSizeBtn.setText("Show map");
            isMapDisplayed = false;
            profilePlayMenu.setVisible(false);

        } else {
            updateMenuOnDisplayingMap();

        }
    }

    @Override
    public void showMap() {
        Window.scrollTo(0, 0);

        if (isMapDisplayed) {
            return;
        }

        showMapProg();
        updateMenuOnDisplayingMap();
    }

    private void updateMenuOnDisplayingMap() {

        mapSizeBtn.setIcon(IconType.RESIZE_SMALL);
        mapSizeBtn.setText("Hide map");
        isMapDisplayed = true;
        profilePlayMenu.setVisible(true);
    }

    public static native void showMapProg() /*-{
		$wnd.$('#pgu_geo_profile_map_container').collapse('show');
    }-*/;

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
    public LocationSearchWidget getLocationSearchWidget() {
        return new LocationSearchWidget() {

            @Override
            public String getText() {
                return locationSearchBox.getTextBox().getText();
            }

            @Override
            public void setText(final String text) {
                locationSearchBox.getTextBox().setText(text);
            }

            @Override
            public void setFocus(final boolean isFocused) {
                locationSearchBox.getTextBox().setFocus(isFocused);
            }

        };
    }

    @Override
    public ProfilePlayMenuWidget getProfilePlayMenuWidget() {
        return new ProfilePlayMenuWidget() {

            @Override
            public boolean isVisible() {
                return profilePlayMenu.isVisible();
            }

            @Override
            public void setVisible(final boolean visible) {
                profilePlayMenu.setVisible(visible);
            }

            @Override
            public void init() {
                playBtn.setVisible(true);
                pauseBtn.setVisible(false);
            }

        };
    }

    @Override
    public HasVisibility getSaveWidget() {
        return locationSaveBtn;
    }

    @Override
    public void showOnMap(final String locationName) {
        showMap();

        MarkersUtils.createMarkerOnProfileMap(locationName);
    }

    public void showNotificationWarning(final String msg) {
        presenter.showNotificationWarning(msg);
    }

}
