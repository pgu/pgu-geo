package pgu.client.profile.ui;

import java.util.HashMap;

import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkersUtils;
import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;
import pgu.client.profile.event.FetchCustomLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent.Handler;
import pgu.client.profile.event.SavePublicLocationsEvent;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.shared.utils.PublicProfileItem;

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

        final String mapPreferences = ProfileUtils.getCurrentMapPreferences();

        fireEvent(new SaveMapPreferencesEvent(mapPreferences));
    }

    @UiHandler("showAllBtn")
    public void clickOnShowAllBtn(final ClickEvent e) {
        // clear current markers
        MarkersUtils.deleteSearchMarkers();

        // show profile_item markers
        LocationsUtils.showProfileItemsOnProfileMap();
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
        MarkersUtils.createMarkerOnProfileMap(locationName);
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

        ProfileViewUtils.searchLocationAndAddMarker(this, locationText);
    }


    @UiHandler("clearSearchMarkersBtn")
    public void clickOnClearMarkersBtn(final ClickEvent e) {
        MarkersUtils.deleteSearchMarkers();
    }

    private static boolean isEduPublic = false;
    private static boolean isExpPublic = false;

    @UiHandler("expPublicState")
    public void clickOnExpPublicState(final ClickEvent e) {

        updatePublicHeader(!isExpPublic, PublicProfileItem.experiences);
        presenter.updatePublicProfile(PublicProfileItem.experiences);
    }

    @UiHandler("eduPublicState")
    public void clickOnEduPublicState(final ClickEvent e) {

        updatePublicHeader(!isEduPublic, PublicProfileItem.educations);
        presenter.updatePublicProfile(PublicProfileItem.educations);
    }

    public void updatePublicHeader(final boolean isPublic, final String publicProfileItem) {
        GWT.log("update " + isPublic + ", " + publicProfileItem);

        if (PublicProfileItem.experiences.equals(publicProfileItem)) {
            expPublicState.setIcon(isPublic ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isExpPublic = isPublic;

        } else if (PublicProfileItem.educations.equals(publicProfileItem)) {
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

        // TODO PGU Nov 23, 2012 public specialties?
        PublicProfileUtils.setSpecialties(htmlSpecialties);
    }

    private void setCurrentLocation(final String locationName) {

        final boolean hasLocation = !u.isVoid(locationName);
        locContainer.setText(hasLocation ? locationName : "");

        if (hasLocation) {
            LocationsUtils.addCurrentLocationToCache(locationName);
        }
    }

    private void setSummary(final String htmlSummary) {
        summaryBasic.getElement().getFirstChildElement().setAttribute("data-content", htmlSummary);
    }

    public void setProfileLanguages(final String lg_html) {
        lgContainer.clear();
        lgContainer.add(new HTML(lg_html));
    }

    private native void setProfile() /*-{

		@pgu.client.profile.ui.ProfileViewUtils::initDelayForCallingGeocoder()();

		// TODO review the way to handle the public profile
		@pgu.client.profile.ui.PublicProfileUtils::initBasePublicProfile()();

        var
            p = $wnd.pgu_geo.profile //
          , first_name = p.firstName || '' //
          , last_name = p.lastName || '' //
          , headline = p.headline || '' //
          , current_location = p.location || {} //
          , current_location_name = current_location.name || '' //
          , specialties = p.specialties || '' //
          , summary = p.summary || '' //
        ;

        this.@pgu.client.profile.ui.ProfileViewImpl::setName(Ljava/lang/String;Ljava/lang/String;)
        (first_name, last_name);

        this.@pgu.client.profile.ui.ProfileViewImpl::setHeadline(Ljava/lang/String;)
        (headline);

        this.@pgu.client.profile.ui.ProfileViewImpl::setCurrentLocation(Ljava/lang/String;)
        (current_location_name);

        this.@pgu.client.profile.ui.ProfileViewImpl::setSpecialties(Ljava/lang/String;)
        (specialties);

        var html_summary = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)
        (summary);
        this.@pgu.client.profile.ui.ProfileViewImpl::setSummary(Ljava/lang/String;)
        (html_summary);

        var languages = p.languages || {};
        var language_values = languages.values || [];

        var cache_lg = {};
        for (var i = 0, len = language_values.length; i < len; i++) {

            var //
            language_value = language_values[i] //
            //
            , language = language_value.language || {} //
            , language_name = language.name || '' //
            //
            , language_proficiency = language_value.proficiency || {} //
            , language_level = language_proficiency.level || '' //
            ;

            if (cache_lg.hasOwnProperty(language_level)) {
                cache_lg.get(language_level).push(language_name);

            } else {
                cache_lg[language_level] = [].concat(language_name);

            }
        }

        // sort language names
        for (var key in cache_lg) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_lg.hasOwnProperty(key)) {
                cache_lg[key].sort();
            }
        }

        var lg_levels = [
              {lvl: 'native_or_bilingual', nb: 4}
            , {lvl: 'full_professional', nb: 3}
            , {lvl: 'professional_working', nb: 2}
            , {lvl: 'limited_working', nb: 1}
            , {lvl: 'elementary', nb: 0}
        ];

        var lg_rows = [];

        for (var i = 0, len = lg_levels.length; i < len; i++) {
            var lg_level = lg_levels[i];

            if (cache_lg.hasOwnProperty(lg_level.lvl)) {
                //
                var trophies = [];
                var trophies_nb = lg_level.nb;

                for (var k = 0; k < trophies_nb; k++) {
                    trophies.push('<i class=\"icon-trophy\"></i>');
                }
                var trophies_html = trophies.join('');

                //
                var lg_names = cache_lg[lg_level.lvl];

                for (var j = 0, lenN = lg_names.length; j < lenN; j++) {
                    var name = lg_names[j];

                    var row = '<div>' + name + '</div><div>' + trophies_html + '</div>';
                    lg_rows.push(row);
                }
            }
        }

        // TODO save the resulting html of lg for the public profile ?
        this.@pgu.client.profile.ui.ProfileViewImpl::setProfileLanguages(Ljava/lang/String;)
        (lg_rows.join(''));

        // TODO PGU
        // TODO PGU
        // TODO PGU
        // TODO PGU

        var j_profile = $wnd.pgu_geo.profile;

		@pgu.client.profile.ui.ProfilePositionsUtils::updateProfilePositions(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)
		(this,j_profile);

		// TODO display "wish" locations
		// TODO display "holidays" locations

		var positions = j_profile.positions;
		$doc.getElementById('pgu_geo.profile:xp_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createExperienceTable(Lcom/google/gwt/core/client/JavaScriptObject;)
		(positions);

		var educations = j_profile.educations;
		$doc.getElementById('pgu_geo.profile:edu_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createEducationTable(Lcom/google/gwt/core/client/JavaScriptObject;)
		(educations);

    }-*/;

    @Override
    public void showPublicPreferences(final String publicPreferences) {

        final String prefs = u.isVoid(publicPreferences) ? "" : publicPreferences;
        PublicProfileUtils.showPublicPreferences(this, prefs);
    }

    @Override
    public String getPublicProfile() {
        return PublicProfileUtils.getPublicProfile();
    }

    @Override
    public void confirmChangeOnPublicProfile(final String publicProfileItem) {

        Tooltip tooltip;
        if (PublicProfileItem.experiences.equals(publicProfileItem)) {
            tooltip = expPublicTooltip;

        } else if (PublicProfileItem.educations.equals(publicProfileItem)) {
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

    public static void updateCachePublicPreferences() {
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItem.experiences, isExpPublic);
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItem.educations, isEduPublic);
    }

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
    public HandlerRegistration addSavePublicLocationsHandler(final SavePublicLocationsEvent.Handler handler) {
        return addHandler(handler, SavePublicLocationsEvent.TYPE);
    }

    @Override
    public HandlerRegistration addFetchCustomLocationsHandler(final FetchCustomLocationsEvent.Handler handler) {
        return addHandler(handler, FetchCustomLocationsEvent.TYPE);
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

        ProfileUtils.initProfileMap();
        ProfileViewUtils.initCaches();

        setProfile();

        fireEvent(new FetchCustomLocationsEvent());
        fireEvent(new FetchPublicPreferencesEvent());

        // save locations async when no locations are detected
        fireEvent(new SavePublicLocationsEvent());
        // TODO PGU Nov 22, 2012 update public profile
        fireEvent(new SavePublicProfileEvent());
    }

}
