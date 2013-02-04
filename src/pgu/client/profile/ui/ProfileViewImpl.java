package pgu.client.profile.ui;

import java.util.HashMap;

import pgu.client.app.utils.ClientHelper;
import pgu.client.app.utils.JsonHelper;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.MarkdownHelper;
import pgu.client.profile.ProfileActivity;
import pgu.client.profile.ProfileView;
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

    private final ClientHelper                               u               = new ClientHelper();

    private String              lastSearchItemLocation      = null;
    private String              itemConfigIdToSave      = null;

    private final ProfileViewTables    viewTables = new ProfileViewTables();
    private final ProfileViewMap       viewMap    = new ProfileViewMap();
    private final ProfileViewToPublic  viewPublic = new ProfileViewToPublic();
    private final ProfileViewLocations viewLocations = new ProfileViewLocations();
    private final ProfileViewMarkers   viewMarkers = new ProfileViewMarkers();
    private final ProfileViewGeocoder  viewGeocoder = new ProfileViewGeocoder();
    private final ProfileViewLanguages viewLanguages = new ProfileViewLanguages();

    private final MarkdownHelper         markdown = new MarkdownHelper();
    private final ProfileLocationsHelper locationsHelper = new ProfileLocationsHelper();
    private final JsonHelper             json = new JsonHelper();

    private final ClientHelper           clientHelper = new ClientHelper();

    public ProfileViewImpl() {

        overviewSection = new Section("profile:overview");
        experienceSection = new Section("profile:experience");
        educationSection = new Section("profile:education");

        initWidget(uiBinder.createAndBindUi(this));
        exportMethods();

        locationSaveBtn.setVisible(false);
    }

    @UiHandler("mapPreferencesBtn")
    public void clickOnMapPreferences(final ClickEvent e) {
        final String mapPreferences = viewMap.getCurrentMapPreferences();
        presenter.saveMapPreferences(mapPreferences, viewPublic.getPublicProfileUrl());
    }

    @UiHandler("showAllBtn")
    public void clickOnShowAllBtn(final ClickEvent e) {
        // clear current markers
        viewMarkers.deleteSearchMarkers();

        // show profile_item markers
        viewLocations.showProfileItemsOnProfileMap(this);
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

        final String locationName = lastSearchItemLocation;

        final String lat = loc2lat.get(locationName);
        final String lng = loc2lng.get(locationName);

        if (u.isVoid(itemConfigIdToSave)) {
            return;
        }

        final boolean isDoublon = viewLocations.isDoublon(itemConfigIdToSave, locationName, lat, lng);
        if (isDoublon) {
            presenter.sendNotif(Level.WARNING, //
                    "This location " + locationName + " is already associated to this item");
        } else {

            presenter.showWaitingIndicator();

            locationsHelper.copyLocationCaches();
            viewLocations.addGeopointToCopyCache(locationName, lat, lng);
            locationsHelper.addLocation2ItemInCopyCache(itemConfigIdToSave, locationName);

            presenter.saveLastSearchLocation(locationName, lat, lng, //
                    locationsHelper.json_copyCacheItems(), locationsHelper.json_copyCacheReferential(), //
                    itemConfigIdToSave);

            itemConfigIdToSave = null;
        }
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

    private boolean isEduPublic = false;
    private boolean isExpPublic = false;

    @UiHandler("expPublicState")
    public void clickOnExpPublicState(final ClickEvent e) {

        final boolean is_public = !isExpPublic; // toggle current state
        isExpPublic = is_public; // refresh state

        final String public_preference = PublicProfileItemType.positions;

        updatePublicHeader(public_preference, is_public, expPublicState);
    }

    @UiHandler("eduPublicState")
    public void clickOnEduPublicState(final ClickEvent e) {

        final boolean is_public = !isEduPublic; // toggle current state
        isEduPublic = is_public; // refresh state

        final String public_preference = PublicProfileItemType.educations;

        updatePublicHeader(public_preference, is_public, eduPublicState);
    }

    private void updatePublicHeader(final String public_preference, final boolean is_public, final Button btn) {

        btn.setIcon(is_public ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
        viewPublic.updatePublicPreference(public_preference, is_public);

        final String jsonPublicPreferences = viewPublic.getJsonPublicPreferences();
        presenter.savePublicPreferences(public_preference, jsonPublicPreferences);
    }

    public void setPublicHeader(final String public_preference, final boolean is_public) {

        if (PublicProfileItemType.educations.equals(public_preference)) {

            eduPublicState.setIcon(is_public ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isEduPublic = is_public;

        } else if (PublicProfileItemType.positions.equals(public_preference)) {

            expPublicState.setIcon(is_public ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isExpPublic = is_public;

        } else {
            throw new IllegalArgumentException("Unknown public preference: " + public_preference);
        }

    }

    private static ProfileActivity staticPresenter;
    private ProfileActivity presenter;

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

        final String locationName = locContainer.getText();
        showLocationOnMap(locationName);
    }

    private void showLocationOnMap(final String locationName) {
        Window.scrollTo(0, 0);
        viewMarkers.createMarkerOnProfileMap(locationName, this);
    }

    @Override
    public void setPresenter(final ProfileActivity presenter) {
        this.presenter = presenter;
        staticPresenter = presenter;
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
        final String htmlSummary = markdown.markdown(summary);
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

    @Override
    public void onSavePublicPreferencesSuccess(final String publicProfileItem) {

        Tooltip tooltip;
        if (PublicProfileItemType.positions.equals(publicProfileItem)) {
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

    private void hideSaveWidget() {
        locationSaveBtn.setVisible(false);
    }

    @Override
    public void showSaveWidget(final String itemConfigId) {
        itemConfigIdToSave = itemConfigId;
        locationSaveBtn.setVisible(true);

        Window.scrollTo(0, 0);
        locationSearchBox.setFocus(true);
    }

    @Override
    public void showNotificationWarning(final String msg) {
        clientHelper.console(msg);
        // TODO PGU textbox with the warning msg
    }

    private boolean isProfileSetInView = false;

    @Override
    public boolean isProfileSetInView() {
        return isProfileSetInView;
    }

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

        presenter.fetchPublicPreferences();
        presenter.fetchMapPreferences();
        presenter.fetchProfileLocations();

        presenter.updatePublicProfileSilently(getJsonPublicProfile(), viewPublic.getPublicProfileUrl());
    }

    @Override
    public void onFetchProfileLocationsSuccess(final ProfileLocations profileLocations) {

        viewLocations.initCaches(profileLocations.getItems2locations(), profileLocations.getReferentialLocations());
        final String locationName = locContainer.getText();

        if (!u.isVoid(locationName)) {
            viewLocations.addCurrentLocationToCache(locationName, this);
        }

        viewLocations.updateLocationsCacheFromPositions(this);
        viewTables.updateTablesWithLocations(this);

        new Timer() { // HACK: fire an event when all locations are done

            @Override
            public void run() {
                viewLocations.removeUnusedLocations();
                locationsHelper.copyLocationCaches();

                presenter.updateLocationsSilently(locationsHelper.json_copyCacheItems(), locationsHelper.json_copyCacheReferential());
            }

        }.schedule(3000);

    }

    private String getJsonPublicProfile() {

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
    public void onFetchMapPreferencesSuccess(final String values) {
        viewMap.setPreferences(values);
    }

    @Override
    public void onSaveLastSearchLocationSuccess(final String locationName, final String itemConfigId) {
        presenter.hideWaitingIndicator();

        locationsHelper.replaceCachesByCopies();
        hideSaveWidget();

        viewTables.refreshHtmlLocationsForItem(itemConfigId, this);

        final StringBuilder msg = new StringBuilder();
        msg.append("The location \"");
        msg.append(locationName);
        msg.append("\" has been successfully added.");

        presenter.sendNotif(Level.SUCCESS, msg.toString());
    }

    @Override
    public void onSaveLastSearchLocationFailure(final Throwable caught) {
        presenter.hideWaitingIndicator();
        locationsHelper.deleteCopies();
    }

    @Override
    public void onUpdateLocationsSilentlySuccess() {
        locationsHelper.replaceCachesByCopies();
    }

    @Override
    public void onUpdateLocationsSilentlyFailure(final Throwable caught) {
        locationsHelper.deleteCopies();
    }

    @Override
    public void onFetchPublicPreferencesSuccess(final PublicPreferences result) {
        viewPublic.showPublicPreferences(this, result.getValues());
    }

    @Override
    public void onLocationsSuccessSave(final String itemConfigId) {
        viewTables.refreshHtmlLocationsForItem(itemConfigId, this);
    }

    @Override
    public void onLocationSuccessDelete(final String itemConfigId) {
        viewTables.refreshHtmlLocationsForItem(itemConfigId, this);
    }

    @Override
    public void onLocationShowOnMap(final String locationName) {
        showLocationOnMap(locationName);
    }

}
