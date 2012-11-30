package pgu.client.profile.ui;

import java.util.HashMap;

import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.JsonHelper;
import pgu.client.app.utils.MarkdownUtils;
import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;
import pgu.client.profile.event.FetchProfileLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveLocationsEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent.Handler;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;
import pgu.shared.utils.PublicProfileItemType;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ProfileViewImpl extends Composite implements ProfileView {

    private static ProfileViewImplUiBinder uiBinder = GWT.create(ProfileViewImplUiBinder.class);

    interface ProfileViewImplUiBinder extends UiBinder<Widget, ProfileViewImpl> {
    }

    @UiField
    Heading                                                 nameBasic;
    @UiField
    Paragraph                                               headlineBasic;
    @UiField
    Popover                                                 summaryBasic;
    @UiField
    Button                                                  summaryBasicBtn;

    @UiField(provided = true)
    Section                                                 overviewSection, experienceSection, educationSection;
    @UiField
    HTMLPanel                                               lgContainer, spContainer;
    @UiField
    Button expPublicState, eduPublicState //
    , clearSearchMarkersBtn //
    , locationSaveBtn //
    , showAllBtn //
    , mapPreferencesBtn //
    ;
    @UiField
    Button locationSearchBtn //
    ;
    @UiField
    TextBox locationSearchBox;
    @UiField
    Tooltip expPublicTooltip, eduPublicTooltip;

    @UiField
    NavLink                                                 locContainer;

    private final ClientUtils                               u               = new ClientUtils();

    private String              lastSearchItemLocation      = null;
    private boolean             isMapDisplayed = true;

    private final ProfileViewTables    viewTables = new ProfileViewTables();
    private final ProfileViewMap       viewMap    = new ProfileViewMap();
    private final ProfileViewToPublic  viewPublic = new ProfileViewToPublic();
    private final ProfileViewLocations viewLocations = new ProfileViewLocations();
    private final ProfileViewMarkers   viewMarkers = new ProfileViewMarkers();
    private final ProfileViewGeocoder  viewGeocoder = new ProfileViewGeocoder();
    private final ProfileViewLanguages viewLanguages = new ProfileViewLanguages();

    private final ProfileLocationsHelper      locationsHelper = new ProfileLocationsHelper();
    private final JsonHelper           json = new JsonHelper();

    public ProfileViewImpl() {

        overviewSection = new Section("profile:overview");
        experienceSection = new Section("profile:experience");
        educationSection = new Section("profile:education");

        initWidget(uiBinder.createAndBindUi(this));
        exportMethods();

        isMapDisplayed = true;
        locationSaveBtn.setVisible(false);

    }

    @UiHandler("mapPreferencesBtn")
    public void clickOnMapPreferences(final ClickEvent e) {

        final String mapPreferences = viewMap.getCurrentMapPreferences();

        fireEvent(new SaveMapPreferencesEvent(mapPreferences));
    }

    @UiHandler("showAllBtn")
    public void clickOnShowAllBtn(final ClickEvent e) {
        // clear current markers
        viewMarkers.deleteSearchMarkers();

        // show profile_item markers
        viewLocations.showProfileItemsOnProfileMap();
    }

    @UiHandler("locationSearchBox")
    public void keydownOnSearch(final KeyDownEvent event) {

        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

            event.preventDefault();
            event.stopPropagation();

            searchLocation();
        }
    }

    @UiHandler("locationSearchBtn")
    public void clickOnLocationSearch(final ClickEvent e) {
        searchLocation();
    }

    @UiHandler("locationSaveBtn")
    public void clickOnLocationSave(final ClickEvent e) {

        if (u.isVoid(lastSearchItemLocation)) {

            lastSearchItemLocation = null;
            return;
        }

        final String lat = loc2lat.get(lastSearchItemLocation);
        final String lng = loc2lng.get(lastSearchItemLocation);

        fireEvent(new SaveLocationEvent(lastSearchItemLocation, lat, lng));
    }

    @Override
    public void showOnMap(final String locationName) {
        Window.scrollTo(0, 0);
        viewMarkers.createMarkerOnProfileMap(locationName, this);
    }

    private final HashMap<String, String> loc2lat = new HashMap<String, String>();
    private final HashMap<String, String> loc2lng = new HashMap<String, String>();

    public void cacheLastSearchedLocation(final String name, final String lat, final String lng) {
        lastSearchItemLocation = name;
        loc2lat.put(name, lat);
        loc2lng.put(name, lng);
    }

    private void searchLocation() {
        final String locationText = locationSearchBox.getText();

        if (u.isVoid(locationText)) {
            lastSearchItemLocation = null;
            return;
        }

        viewMarkers.searchLocationAndAddMarker(this, locationText);
    }


    @UiHandler("clearSearchMarkersBtn")
    public void clickOnClearMarkersBtn(final ClickEvent e) {
        viewMarkers.deleteSearchMarkers();
    }

    private static boolean isEduPublic = false;
    private static boolean isExpPublic = false;

    @UiHandler("expPublicState")
    public void clickOnExpPublicState(final ClickEvent e) {

        updatePublicHeader(!isExpPublic, PublicProfileItemType.experiences);
        presenter.updatePublicProfile(PublicProfileItemType.experiences);
    }

    @UiHandler("eduPublicState")
    public void clickOnEduPublicState(final ClickEvent e) {

        updatePublicHeader(!isEduPublic, PublicProfileItemType.educations);
        presenter.updatePublicProfile(PublicProfileItemType.educations);
    }

    public void updatePublicHeader(final boolean isPublic, final String publicProfileItem) {
        GWT.log("update " + isPublic + ", " + publicProfileItem);

        if (PublicProfileItemType.experiences.equals(publicProfileItem)) {
            expPublicState.setIcon(isPublic ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isExpPublic = isPublic;

        } else if (PublicProfileItemType.educations.equals(publicProfileItem)) {
            eduPublicState.setIcon(isPublic ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isEduPublic = isPublic;

        }
    }

    private static ProfilePresenter staticPresenter;

    public native void exportMethods() /*-{
      $wnd.pgu_geo.add_new_location = $entry(@pgu.client.profile.ui.ProfileViewImpl::addNewLocation(Ljava/lang/String;));
      $wnd.pgu_geo.edit_location = $entry(@pgu.client.profile.ui.ProfileViewImpl::editLocation(Ljava/lang/String;Ljava/lang/String;));
    }-*/;

    public static void editLocation(final String itemConfigId, final String locName) {
        staticPresenter.editLocation(itemConfigId, locName);
    }

    public static void addNewLocation(final String itemConfigId) {
        staticPresenter.addNewLocation(itemConfigId);
    }

    @UiHandler("summaryBasicBtn")
    public void clickSummaryBasic(final ClickEvent e) {
        summaryBasic.toggle();
    }

    @UiHandler("locContainer")
    public void clickLocContainer(final ClickEvent e) {
        fireEvent(new LocationShowOnMapEvent(locContainer.getText()));
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        staticPresenter = presenter;
        this.presenter = presenter;
    }

    private void setName(final String firstname, final String lastname) {
        nameBasic.setText(firstname + " " + lastname);
    }

    private void setHeadline(final String headline) {
        headlineBasic.setText(headline);
    }

    private void setSpecialties(final String specialtiesLabel) {
        final StringBuilder sb = new StringBuilder();

        final String[] rawLabels = specialtiesLabel.split(",");
        for (final String specialty : rawLabels) {

            if (u.isVoid(specialty)) {
                continue;
            }

            sb.append("<div>");
            sb.append(specialty.trim());
            sb.append("</div>");
        }

        final String htmlSpecialties = sb.toString();
        spContainer.add(new HTML(htmlSpecialties));
    }

    private void setCurrentLocation(final String locationName) {
        locContainer.setText(u.isVoid(locationName) ? "" : locationName);
    }

    private void setSummary(final String summary) {
        final String htmlSummary = MarkdownUtils.markdown(summary);
        summaryBasic.getElement().getFirstChildElement().setAttribute("data-content", htmlSummary);
    }

    public void setLanguages(final JavaScriptObject language_values) {

        final String lgHtml = viewLanguages.createLanguagesHtml(language_values);

        lgContainer.clear();
        lgContainer.add(new HTML(lgHtml));
    }

    private native void setProfile() /*-{

        var
            p = $wnd.pgu_geo.profile
          , first_name = p.firstName || ''
          , last_name = p.lastName || ''
          , headline = p.headline || ''
          , current_location = p.location || {}
          , current_location_name = current_location.name || ''
          , specialties = p.specialties || ''
          , summary = p.summary || ''
          , languages = p.languages || {} //
          , language_values = languages.values || [] //
          , positions = p.positions || {}
          , educations = p.educations || {}
        ;

        this.@pgu.client.profile.ui.ProfileViewImpl::setName(Ljava/lang/String;Ljava/lang/String;)
             (first_name, last_name);

        this.@pgu.client.profile.ui.ProfileViewImpl::setHeadline(Ljava/lang/String;)
             (headline);

        this.@pgu.client.profile.ui.ProfileViewImpl::setCurrentLocation(Ljava/lang/String;)
             (current_location_name);

        this.@pgu.client.profile.ui.ProfileViewImpl::setSpecialties(Ljava/lang/String;)
             (specialties);

        this.@pgu.client.profile.ui.ProfileViewImpl::setSummary(Ljava/lang/String;)
             (summary);

        this.@pgu.client.profile.ui.ProfileViewImpl::setLanguages(Lcom/google/gwt/core/client/JavaScriptObject;)
             (language_values);

        this.@pgu.client.profile.ui.ProfileViewImpl::setExperienceTable(Lcom/google/gwt/core/client/JavaScriptObject;)
		     (positions);

		this.@pgu.client.profile.ui.ProfileViewImpl::setEducationTable(Lcom/google/gwt/core/client/JavaScriptObject;)
		     (educations);

		// TODO display "wish" locations
		// TODO display "holidays" locations

        this.@pgu.client.profile.ui.ProfileViewImpl::setProfileAfter()
             ();

    }-*/;

    public void setExperienceTable(final JavaScriptObject positions) {
        final String html = viewTables.createExperienceTable(positions);
        setExperienceTableHtml(html);
    };

    private native void setExperienceTableHtml(String html) /*-{
        $doc.getElementById('pgu_geo.profile:xp_table').innerHTML = html;
    }-*/;

    public void setEducationTable(final JavaScriptObject educations) {
        final String html = viewTables.createEducationTable(educations);
        setEducationTableHtml(html);
    }

    private native void setEducationTableHtml(String html) /*-{
        $doc.getElementById('pgu_geo.profile:edu_table').innerHTML = html;
    }-*/;

    @Deprecated
    @Override
    public String getPublicProfile() {
        return PublicProfileUtils.getPublicProfile();
    }

    @Override
    public void confirmChangeOnPublicProfile(final String publicProfileItem) {

        Tooltip tooltip;
        if (PublicProfileItemType.experiences.equals(publicProfileItem)) {
            tooltip = expPublicTooltip;

        } else if (PublicProfileItemType.educations.equals(publicProfileItem)) {
            tooltip = eduPublicTooltip;
        } else {
            throw new IllegalArgumentException("item? " + publicProfileItem);
        }

        final Tooltip _tooltip = tooltip;
        _tooltip.show();
        new Timer(){

            @Override
            public void run() {
                _tooltip.hide();
            }

        }.schedule(3000);
    }

    @Deprecated
    public static void updateCachePublicPreferences() {
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItemType.experiences, isExpPublic);
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItemType.educations, isEduPublic);
    }

    @Deprecated
    @Override
    public String getPublicPreferences() {
        return PublicProfileUtils.json_publicPreferences();
    }


    @Override
    public HandlerRegistration addSaveLocationHandler(final SaveLocationEvent.Handler handler) {
        return addHandler(handler, SaveLocationEvent.TYPE);
    }

    @Override
    public HandlerRegistration addLocationShowOnMapHandler(final LocationShowOnMapEvent.Handler handler) {
        return addHandler(handler, LocationShowOnMapEvent.TYPE);
    }

    @Override
    public HandlerRegistration addSaveMapPreferencesHandler(final Handler handler) {
        return addHandler(handler, SaveMapPreferencesEvent.TYPE);
    }

    @Override
    public HandlerRegistration addFetchPublicPreferencesHandler(final FetchPublicPreferencesEvent.Handler handler) {
        return addHandler(handler, FetchPublicPreferencesEvent.TYPE);
    }

    @Override
    public HandlerRegistration addSaveLocationsHandler(final SaveLocationsEvent.Handler handler) {
        return addHandler(handler, SaveLocationsEvent.TYPE);
    }

    @Override
    public HandlerRegistration addFetchProfileLocationsHandler(final FetchProfileLocationsEvent.Handler handler) {
        return addHandler(handler, FetchProfileLocationsEvent.TYPE);
    }

    @Override
    public HandlerRegistration addSavePublicProfileHandler(final SavePublicProfileEvent.Handler handler) {
        return addHandler(handler, SavePublicProfileEvent.TYPE);
    }

    @Override
    public void hideSaveWidget() {
        locationSaveBtn.setVisible(false);
    }

    @Override
    public void showSaveWidget() {
        locationSaveBtn.setVisible(true);

        Window.scrollTo(0, 0);
        locationSearchBox.setFocus(true);
    }

    @Override
    public void showNotificationWarning(final String msg) {
        GWT.log(msg);
        // TODO PGU textbox with the warning msg
    }

    private boolean isProfileSetInView = false;

    @Override
    public void showProfile() {
        if (isProfileSetInView) {
            return;
        }

        isProfileSetInView = true;

        viewMap.initProfileMap();
        viewTables.initCacheAnchor();
        viewTables.initCacheItemConfigs();

        setProfile();
    }

    private void setProfileAfter() {
        fireEvent(new FetchProfileLocationsEvent());
        fireEvent(new FetchPublicPreferencesEvent());

        // TODO PGU
        // TODO PGU
        // TODO PGU
        // TODO PGU
        fireEvent(new SavePublicProfileEvent());
    }

    @Override
    public void setProfileLocations(final ProfileLocations profileLocations) {

        viewLocations.initCaches(profileLocations.getItems2locations(), profileLocations.getReferentialLocations());
        final String locationName = locContainer.getText();

        if (!u.isVoid(locationName)) {
            viewLocations.addCurrentLocationToCache(locationName, this);
        }

        viewLocations.updateLocationsCacheFromPositions();
        viewTables.updateTablesWithLocations(this);

        new Timer() { // TODO HACK: fire an event when all locations are done

            @Override
            public void run() {
                fireEvent(new SaveLocationsEvent());
            }

        }.schedule(3000);
    }

    @Override
    public void removeUnusedLocations() {
        viewLocations.removeUnusedLocations();
    }

    @Override
    public void setPublicPreferencesInfo(final PublicPreferences result) {
        viewPublic.showPublicPreferences(this, result.getValues());
    }

    @Override
    public void refreshHtmlLocationsForItem(final String itemConfigId) {
        viewTables.refreshHtmlLocationsForItem(itemConfigId, this);
    }

    @Override
    public String getJsonPublicProfile() {

        final String specialtiesHtml = spContainer.getElement().getInnerHTML();
        final String locationName = locContainer.getText();

        final String fmtSummary = summaryBasic.getElement().getFirstChildElement().getAttribute("data-content");
        final String languagesHtml = lgContainer.getElement().getInnerHTML();

        return viewPublic.getJsonPublicProfile(specialtiesHtml //
                , locationName //
                , fmtSummary //
                , languagesHtml //
                );
    }

    @Override
    public native String getJsonRawProfile() /*-{
        var p = $wnd.pgu_geo.profile;

        return this.@pgu.client.profile.ui.ProfileViewImpl::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                    (p);
    }-*/;

    private String stringify(final JavaScriptObject jso) {
        return json.stringify(jso);
    }

    //    public static native JavaScriptObject copyProfile() /*-{
    //    return JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
    //                      ($wnd.pgu_geo.profile));
    //    }-*/;

    public void searchGeopoint(final String location_name, final JavaScriptObject callback) {
        viewGeocoder.searchGeopoint(location_name, callback, this);
    }

    public void createMarkerOnProfileMap(final String location_name) {
        viewMarkers.createMarkerOnProfileMap(location_name, this);
    }

    public JavaScriptObject geocoder() {
        return viewGeocoder.geocoder();
    }

    public JavaScriptObject profileMap() {
        return viewMap.profileMap();
    }

    public JavaScriptObject cacheItems() {
        return viewLocations.cacheItems();
    }

    public void addGeopointToCache(final String location_name, final String lat,final String lng) {
        viewLocations.addGeopointToCache(location_name, lat, lng);
    }

    @Override
    public void addGeopointToCopyCache(final String location_name, final String lat, final String lng) {
        viewLocations.addGeopointToCopyCache(location_name, lat, lng);
    }

}
