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
import pgu.shared.model.UserAndLocations;
import pgu.shared.utils.ItemType;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
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
		var geopoint = $wnd.pgu_geo.cache_referentialLocations[location_name];
		if (geopoint == undefined) {
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
        final UserAndLocations userAndLocations = profile.getUserAndLocations();
        u.initCacheItems2Locations(userAndLocations.getItems2locations());
        u.initCacheReferentialLocations(userAndLocations.getReferentialLocations());
        initCacheLocation2AnchorIds();

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
        summaryBasic.getElement().getFirstChildElement().setAttribute("data-content", u.markdown(summary));
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

		////////////////////////
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

		$wnd.pgu_geo.delay_to_call_geocoder = 3000;

		var positions = j_profile.positions;
		$doc.getElementById('pgu_geo.profile:xp_table').innerHTML = //
		view.@pgu.client.profile.ui.ProfileViewImpl::createExperienceTable(Lcom/google/gwt/core/client/JavaScriptObject;)(positions);

		var educations = j_profile.educations;
		$doc.getElementById('pgu_geo.profile:edu_table').innerHTML = //
		view.@pgu.client.profile.ui.ProfileViewImpl::createEducationTable(Lcom/google/gwt/core/client/JavaScriptObject;)(educations);

		// TODO refresh css anchors according to locations found or not

    }-*/;

    public String createExperienceTable(final JavaScriptObject jsonExperiences) {
        return createTable(ItemType.experience, jsonExperiences, "No experience has been found");
    }

    public String createEducationTable(final JavaScriptObject jsonEducations) {
        return createTable(ItemType.education, jsonEducations, "No education has been found");
    }

    private native String createTable(String type, JavaScriptObject json_items, String empty_message) /*-{

		var items = json_items || {};
		if (items.values) {

			var values = items.values;
			var table = [];

			var tableHead = @pgu.client.profile.ui.ProfileViewImpl::createTableHead(Ljava/lang/String;)(type);
			table.push(tableHead);

			for ( var i in values) {
				var tableRow = @pgu.client.profile.ui.ProfileViewImpl::createTableRow(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type, values[i]);
				table.push(tableRow);
			}

			var tableFoot = @pgu.client.profile.ui.ProfileViewImpl::createTableFoot()();
			table.push(tableFoot);

			@pgu.client.profile.ui.ProfileViewImpl::sortItemConfigsByDate()();

			return table.join('');

		} else {
			return empty_message;
		}

    }-*/;

    public static boolean isEdu(final String itemType) {
        return ItemType.education.equals(itemType);
    }

    public static boolean isXp(final String itemType) {
        return ItemType.experience.equals(itemType);
    }

    public static native String createTableHead(final String type) /*-{
		var title = '';

		if (@pgu.client.profile.ui.ProfileViewImpl::isEdu(Ljava/lang/String;)(type)) {
			title = 'Education';

		} else if (@pgu.client.profile.ui.ProfileViewImpl::isXp(Ljava/lang/String;)(type)) {
			title = 'Position';

		}

		return '' + '<table class="table table-bordered table-striped"> '
				+ '   <thead>                                         '
				+ '      <tr>                                         '
				+ '          <th>Locations</th>                        '
				+ '          <th>Dates</th>                           '
				+ '          <th>' + title + '</th>                   '
				+ '          <th></th>                                '
				+ '      </tr>                                        '
				+ '  </thead>                                         '
				+ '  <tbody>                                          ' + '';

    }-*/;

    public static native String createTableFoot() /*-{
		return '' + //
		'  </tbody>                                        ' + //
		'</table>                                          ' + //
		'';

    }-*/;

    public static native String createTableRow(final String type, final JavaScriptObject item) /*-{
		var rowConfig = {};
		rowConfig.id = item.id;
		rowConfig.locations = @pgu.client.profile.ui.ProfileViewImpl::createListLocations(Ljava/lang/String;Ljava/lang/String;)(type,item.id);
		//		rowConfig.dates = labelDates(item);
		// TODO PGU...

    }-*/;

    public static native String createListLocations(String type, String item_id) /*-{
		var location_names = @pgu.client.app.utils.ClientUtils::getLocationNamesForItem(Ljava/lang/String;Ljava/lang/String;)(type, item_id);

		var list = [];

		for ( var i in location_names) {

			var location_name = location_names[i];
			var anchor_id = "loc_" + item_id + "_" + i;

			var anchor_ids = $wnd.pgu_geo.cache_location2anchorIds[location_name]
					|| [];
			anchor_ids.push(anchor_id);
			$wnd.pgu_geo.cache_location2anchorIds[location_name] = anchor_ids;

			var el = '' + //
			'      <li class="locationLi">          ' + //
			'        <a id="' + anchor_id + '"      ' + //
			'           href="javascript:;"         ' + //
			'           onclick="javascript:' + //
			'pgu_geo.editLocation(\'' + item_id + '\', \'' + location_name
					+ '\');' + //
					'return false;"' + //
					' >' + location_name + '</a>             ' + //
					'      </li>                            ' + //
					'';

			list.push(el);

			var geopoint = $wnd.pgu_geo.cache_referentialLocations[location_name];
			if (!geopoint) {

				var delayMillis = $wnd.pgu_geo.delay_to_call_geocoder;
				@pgu.client.profile.ui.ProfileViewImpl::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,delayMillis);

				$wnd.pgu_geo.delay_to_call_geocoder += 300;
			}

		}

		return list.join('');
    }-*/;

    private native void initCacheLocation2AnchorIds() /*-{
		$wnd.pgu_geo.cache_location2anchorIds = {};
    }-*/;

    public static void searchGeopointWithDelay(final String locationName, final int delayMillis) {
        new Timer() {

            @Override
            public void run() {
                Scheduler.get().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        searchGeopoint(locationName);
                    }
                });
            }

        }.schedule(delayMillis);
    }

    private static native boolean isGeocoderAvailable() /*-{
		return $wnd.geocoder === undefined;
    }-*/;

    public static void searchGeopoint(final String locationName) {

        if (!isGeocoderAvailable()) {
            searchGeopointWithDelay(locationName, 1000);
            return;
        }

        if (ClientUtils.isLocationInReferential(locationName)) {
            return;
        }

        geocode(locationName);

    }

    private static native void geocode(String locationName) /*-{
		$wnd.geocoder
				.geocode(

						{
							'address' : locationName
						}, //
						function(results, status) {

							if (status == google.maps.GeocoderStatus.OK) {

								var loc = results[0].geometry.location;
								var lat = loc.lat() + '';
								var lng = loc.lng() + '';

								@pgu.client.app.utils.ClientUtils::updateLocationReferential(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(locationName,lat,lng);

							} else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {

								//								var anchor = $('#' + anchor_id);
								//								anchor.addClass('locationNotFound');
								//								anchor.attr('title', 'Unknown location');
								$wnd.console.log("Unknown location: "
										+ locationName + ", " + status);

							} else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {

								@pgu.client.profile.ui.ProfileViewImpl::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,1000);

								$wnd.console.log("over_query_limit... "
										+ itemLocation.name);

							} else {

								//								var anchor = $('#' + anchor_id);
								//								anchor
								//										.addClass('locationNotFound_technicalError');
								//								anchor.attr('title',
								//										'Location not found because of a technical exception: '
								//												+ status);
								$wnd.console.log("Oups: " + status);
							}

						}

				);
    }-*/;

}
