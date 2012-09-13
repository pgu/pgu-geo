package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map.Entry;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkdownUtils;
import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;
import pgu.shared.dto.ItemLocation;
import pgu.shared.dto.Profile;
import pgu.shared.model.UserAndLocations;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
    NavLink                                                 locContainer;

    private final ClientUtils                               u               = new ClientUtils();
    private final EnumMap<LanguageLevel, ArrayList<String>> level2languages = new EnumMap<LanguageLevel, ArrayList<String>>(
            LanguageLevel.class);
    private static final String                             trophy          = " <i class=\"icon-trophy\"></i> ";

    private enum LanguageLevel {
        native_or_bilingual(4) //
        , full_professional(3) //
        , professional_working(2) //
        , limited_working(1) //
        , elementary(0) //
        ;

        private int nbTrophies;

        LanguageLevel(final int nbTrophies) {
            this.nbTrophies = nbTrophies;
        }

        public int getNbTrophies() {
            return nbTrophies;
        }
    }

    public ProfileViewImpl() {

        overviewSection = new Section("profile:overview");
        experienceSection = new Section("profile:experience");
        educationSection = new Section("profile:education");

        initWidget(uiBinder.createAndBindUi(this));

        exportMethod();
    }

    private static ProfilePresenter staticPresenter;

    public native void exportMethod() /*-{
          $wnd.pgu_geo.addNewLocation = $entry(@pgu.client.profile.ui.ProfileViewImpl::addNewLocation(Ljava/lang/String;));
          $wnd.pgu_geo.editLocation = $entry(@pgu.client.profile.ui.ProfileViewImpl::editLocation(Ljava/lang/String;Ljava/lang/String;));
    }-*/;

    public static native void editLocation(final String item_id, final String location_name) /*-{
        // TODO review
		var geopoint = $wnd.pgu_geo.cache_referentialLocations[location_name];
		if (!geopoint) {
			return;
		}

		@pgu.client.profile.ui.ProfileViewImpl::editItemLocation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(item_id, location_name, geopoint.lat, geopoint.lng);
    }-*/;

    public static void editItemLocation(final String itemId, final String locName, final String locLat,
            final String locLng) {

        staticPresenter.editLocation(itemId, locName, locLat, locLng);
    }

    public static void addNewLocation(final String itemId) {
        staticPresenter.addNewLocation(itemId);
    }

    @UiHandler("summaryBasicBtn")
    public void clickSummaryBasic(final ClickEvent e) {
        summaryBasic.toggle();
    }

    @UiHandler("locContainer")
    public void clickLocContainer(final ClickEvent e) {

        // TODO PGU Aug 30, 2012 itemLocation
        final ItemLocation i = new ItemLocation();
        i.setName(locContainer.getText());

        presenter.showLocationOnMap(i);
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        staticPresenter = presenter;
        this.presenter = presenter;
    }

    @Override
    public void setProfile(final Profile profile) {
        final UserAndLocations ual = profile.getUserAndLocations();
        LocationsUtils.initCaches(ual.getItems2locations(), ual.getReferentialLocations());
        ProfileViewUtils.initCaches();

        setProfile(this, profile.getJson());
    }

    private void setProfileName(final String firstname, final String lastname) {
        nameBasic.setText(firstname + " " + lastname);
    }

    private void setProfileHeadline(final String headline) {
        headlineBasic.setText(headline);
    }

    private void setProfileSpecialties(final String specialtiesLabel) {
        for (final String specialty : specialtiesLabel.split(", ")) {

            if (u.isVoid(specialty)) {
                continue;
            }

            spContainer.add(new HTML(specialty));
        }
    }

    private void setProfileLocation(final String locationName) {
        locContainer.setText(u.isVoid(locationName) ? "" : locationName);
    }

    private void setProfileSummary(final String summary) {
        summaryBasic.getElement().getFirstChildElement().setAttribute("data-content", MarkdownUtils.markdown(summary));
    }

    private void clearProfileLanguages() {
        lgContainer.clear();
        level2languages.clear();
    }

    private void addProfileLanguage(final String languageName, final String languageLevel) {

        final LanguageLevel level = getLanguageLevel(languageLevel);
        addLanguageAndLevelToCache(languageName, level);
    }

    private void addLanguageAndLevelToCache(final String languageName, final LanguageLevel level) {
        if (level2languages.containsKey(level)) {
            level2languages.get(level).add(languageName);
        } else {
            final ArrayList<String> names = new ArrayList<String>();
            names.add(languageName);
            level2languages.put(level, names);
        }
    }

    private LanguageLevel getLanguageLevel(final String languageLevel) {
        try {
            return LanguageLevel.valueOf(languageLevel);

        } catch (final IllegalArgumentException e) {
            return LanguageLevel.elementary;
        }
    }

    private static final Comparator<String> LEXICO = new Comparator<String>() {

        @Override
        public int compare(final String s1, final String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    };

    private void showProfileLanguages() {

        for (final Entry<LanguageLevel, ArrayList<String>> e : level2languages.entrySet()) {
            final LanguageLevel level = e.getKey();
            final ArrayList<String> names = e.getValue();

            final int nbTrophies = level.getNbTrophies();
            Collections.sort(names, LEXICO);

            for (final String name : names) {
                addLanguageRow(nbTrophies, name);
            }
        }
    }

    private void addLanguageRow(final int nbTrophies, final String name) {

        final StringBuilder trophies = new StringBuilder();
        for (int i = 0; i < nbTrophies; i++) {
            trophies.append(trophy);
        }

        final Column labelCol = new Column(3);
        final Column levelCol = new Column(3);

        labelCol.getElement().setInnerHTML(name);
        levelCol.getElement().setInnerHTML(trophies.toString());

        final FluidRow row = new FluidRow();
        row.add(labelCol);
        row.add(levelCol);

        lgContainer.add(row);
    }

    private void setProfileId(final String id) {
        presenter.setProfileId(id);
    }

    private native void setProfile(ProfileViewImpl view, String profile) /*-{

		var j_profile = JSON.parse(profile);

		var //
		profile_id = j_profile.id || '' //
		, first_name = j_profile.firstName || '' //
		, last_name = j_profile.lastName || '' //
		, headline = j_profile.headline || '' //
		, specialties = j_profile.specialties || '' //
		, location_name = '' //
		, summary = j_profile.summary || '' //
		, languages = j_profile.languages || {} //
		;

		var profile_location = j_profile.location || {};
		location_name = profile_location.name;

		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileId(Ljava/lang/String;)(profile_id);
		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileName(Ljava/lang/String;Ljava/lang/String;)(first_name, last_name);
		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileHeadline(Ljava/lang/String;)(headline);
		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileSpecialties(Ljava/lang/String;)(specialties);
		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileLocation(Ljava/lang/String;)(location_name);
		view.@pgu.client.profile.ui.ProfileViewImpl::setProfileSummary(Ljava/lang/String;)(summary);

		view.@pgu.client.profile.ui.ProfileViewImpl::clearProfileLanguages()();
		var language_values = languages.values || [];
		for ( var i in language_values) {

			var //
			language_value = language_values[i] //
			//
			, language = language_value.language || {} //
			, language_name = language.name || '' //
			//
			, language_proficiency = language_value.proficiency || {} //
			, language_level = language_proficiency.level || '' //
			;
			view.@pgu.client.profile.ui.ProfileViewImpl::addProfileLanguage(Ljava/lang/String;Ljava/lang/String;)(language_name, language_level);
		}
		view.@pgu.client.profile.ui.ProfileViewImpl::showProfileLanguages()();

		//
		// tables...
		//
		@pgu.client.profile.ui.ProfileViewUtils::initDelayForCallingGeocoder()();
		// TODO search geocode for profile location

		var positions = j_profile.positions;
		$doc.getElementById('pgu_geo.profile:xp_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createExperienceTable(Lcom/google/gwt/core/client/JavaScriptObject;)(positions);

		var educations = j_profile.educations;
		$doc.getElementById('pgu_geo.profile:edu_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createEducationTable(Lcom/google/gwt/core/client/JavaScriptObject;)(educations);

		// TODO refresh css anchors according to locations found or not

    }-*/;

}
