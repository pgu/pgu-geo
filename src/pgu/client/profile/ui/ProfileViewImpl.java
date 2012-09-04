package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map.Entry;

import pgu.client.app.utils.ClientUtils;
import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;
import pgu.shared.dto.ItemLocation;
import pgu.shared.dto.Profile;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.github.gwtbootstrap.client.ui.constants.Trigger;
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
        // TODO PGU Sep 4, 2012 a completer les languages levels
        native_or_bilingual(4) //
        , full_professional(3) //
        , professional_working(2) //
        , unknown(0);

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

        summaryBasic.setTrigger(Trigger.MANUAL);
        summaryBasic.setAnimation(true);
        summaryBasic.setPlacement(Placement.LEFT);
        summaryBasic.setHeading("Summary");

        // TODO PGU Sep 4, 2012 is this id still useful?
        locContainer.getElement().setId("el_profile_location");

        // http://www.linkedin.com/pub/pascal-guilcher/2/3b1/955
        exportMethod();
    }

    private static ProfilePresenter staticPresenter = null;

    public native static void exportMethod() /*-{
		$wnd.addNewLocation = $entry(@pgu.client.profile.ui.ProfileViewImpl::addNewLocation(Ljava/lang/String;));
		$wnd.editLocation = $entry(@pgu.client.profile.ui.ProfileViewImpl::editLocation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;));
    }-*/;

    public static void editLocation(final String itemId, final String locName, final String locLat, final String locLng) {
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

        setProfile(this, profile.getJson(), profile.getItemId2locations());
    }

    private void setPersonName(final String firstname, final String lastname) {
        nameBasic.setText(firstname + " " + lastname);
    }

    private void setPersonHeadline(final String headline) {
        headlineBasic.setText(headline);
    }

    private void setPersonSpecialties(final String specialtiesLabel) {
        for (final String specialty : specialtiesLabel.split(", ")) {

            if (u.isVoid(specialty)) {
                continue;
            }

            spContainer.add(new HTML(specialty));
        }
    }

    private void setPersonLocation(final String locationName) {
        locContainer.setText(u.isVoid(locationName) ? "" : locationName);
    }

    private void setPersonSummary(final String summary) {
        summaryBasic.setText(u.markdown(summary));
    }

    private void clearPersonLanguages() {
        lgContainer.clear();
        level2languages.clear();
    }

    private void addPersonLanguage(final String languageName, final String languageLevel) {

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
            return LanguageLevel.unknown;
        }
    }

    private static final Comparator<String> LEXICO = new Comparator<String>() {

                                                       @Override
                                                       public int compare(final String s1, final String s2) {
                                                           return s1.compareToIgnoreCase(s2);
                                                       }

                                                   };

    private void showPersonLanguages() {

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

    private native void setProfile(ProfileViewImpl view, String profile, String itemId2locations) /*-{

		var j_profile = JSON.parse(profile);
		$wnd.cache_itemId2locations = JSON.parse(itemId2locations);

		////////////////////////
		var //
		first_name = j_profile.firstName || '' //
		, last_name = j_profile.lastName || '' //
		, headline = j_profile.headline || '' //
		, specialties = j_profile.specialties || '' //
		, location_name = '' //
		, summary = j_profile.summary || '' //
		, languages = j_profile.languages || {} //
		;

		var profile_location = j_profile.location || {};
		location_name = profile_location.name;

		view.@pgu.client.profile.ui.ProfileViewImpl::setPersonName(Ljava/lang/String;Ljava/lang/String;)(first_name, last_name);
		view.@pgu.client.profile.ui.ProfileViewImpl::setPersonHeadline(Ljava/lang/String;)(headline);
		view.@pgu.client.profile.ui.ProfileViewImpl::setPersonSpecialties(Ljava/lang/String;)(specialties);
		view.@pgu.client.profile.ui.ProfileViewImpl::setPersonLocation(Ljava/lang/String;)(location_name);
		view.@pgu.client.profile.ui.ProfileViewImpl::setPersonSummary(Ljava/lang/String;)(summary);

		view.@pgu.client.profile.ui.ProfileViewImpl::clearPersonLanguages()();
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
			view.@pgu.client.profile.ui.ProfileViewImpl::addPersonLanguage(Ljava/lang/String;Ljava/lang/String;)(language_name, language_level);
		}
		view.@pgu.client.profile.ui.ProfileViewImpl::showPersonLanguages()();

		////////////////////////

		$doc.getElementById('profile:xp_table').innerHTML = //
		$wnd.createTable( //
		'xp' //
		, j_profile.positions //
		, 'No experience has been found');

		////////////////////////

		$doc.getElementById('profile:edu_table').innerHTML = //
		$wnd.createTable( //
		'edu' //
		, j_profile.educations //
		, 'No education has been found');

    }-*/;

}
