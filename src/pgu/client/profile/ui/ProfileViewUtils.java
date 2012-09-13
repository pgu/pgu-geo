package pgu.client.profile.ui;

import pgu.client.app.utils.LocationsUtils;
import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class ProfileViewUtils {

    static int delayForCallingGeocoder = 0;

    public static void initDelayForCallingGeocoder() {
        delayForCallingGeocoder = 3000;
    }

    public static void incrementDelayForCallingGeocoder() {
        delayForCallingGeocoder += 300;
    }

    public static native void initCaches() /*-{
		$wnd.pgu_geo.cache_location2anchorIds = {};
        $wnd.pgu_geo.item_configs = [];
    }-*/;

    public static String createExperienceTable(final JavaScriptObject jsonExperiences) {
        return createTable(ItemType.experience, jsonExperiences, "No experience has been found");
    }

    public static String createEducationTable(final JavaScriptObject jsonEducations) {
        return createTable(ItemType.education, jsonEducations, "No education has been found");
    }

    public static native String createTable(String type, JavaScriptObject json_items, String empty_message) /*-{

		var items = json_items || {};
		if (items.values) {

			var values = items.values;
			var table = [];

			var tableHead = @pgu.client.profile.ui.ProfileViewUtils::createTableHead(Ljava/lang/String;)(type);
			table.push(tableHead);

			for ( var i in values) {
				var tableRow = @pgu.client.profile.ui.ProfileViewUtils::createTableRow(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type, values[i]);
				table.push(tableRow);
			}

			var tableFoot = @pgu.client.profile.ui.ProfileViewUtils::createTableFoot()();
			table.push(tableFoot);

            $wnd.pgu_geo.item_configs.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );

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

		if (@pgu.client.profile.ui.ProfileViewUtils::isEdu(Ljava/lang/String;)(type)) {
			title = 'Education';

		} else if (@pgu.client.profile.ui.ProfileViewUtils::isXp(Ljava/lang/String;)(type)) {
			title = 'Position';

		}

		return ''
				+ '<table class="table table-bordered table-striped"> '
				+ '   <thead>                                         '
				+ '      <tr>                                         '
				+ '          <th>Locations</th>                       '
				+ '          <th>Dates</th>                           '
				+ '          <th>' + title + '</th>                   '
				+ '          <th></th>                                '
				+ '      </tr>                                        '
				+ '  </thead>                                         '
				+ '  <tbody>                                          '
				+ '';

    }-*/;

    public static native String createTableFoot() /*-{
		return '' +
		'  </tbody>                                        ' +
		'</table>                                          ' +
		'';

    }-*/;

    public static native String createTableRow(final String type, final JavaScriptObject item) /*-{
		var item_config = {};
		item_config.id = type + ',' + item.id;
		item_config.locations = @pgu.client.profile.ui.ProfileViewUtils::createListLocations(Ljava/lang/String;)(item_config.id);
		item_config.dates = @pgu.client.profile.ui.ProfileDateUtils::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)(item);

		if (@pgu.client.profile.ui.ProfileViewUtils::isEdu(Ljava/lang/String;)(type)) {

			item_config.short_content = @pgu.client.profile.ui.ProfileViewUtils::labelEduTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
			item_config.content_title = "Education";
			item_config.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.notes);

		} else if (@pgu.client.profile.ui.ProfileViewUtils::isXp(Ljava/lang/String;)(type)) {

			item_config.short_content = @pgu.client.profile.ui.ProfileViewUtils::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
			item_config.content_title = "Experience";
			item_config.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.summary);

		} else {

			item_config.short_content = "";
			item_config.content_title = "";
			item_config.long_content = "";

			@pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown type " + type);
		}

		if (item.startDate //
				&& item.startDate.year) {

			var startDate = item.startDate;
			var month = startDate.month || 1;

			item_config.startD = new Date(startDate.year, month - 1, 1);
		}

		$wnd.pgu_geo.item_configs.push(item_config);

		return ''
				+ '<tr>                                                                                '
				+ '  <td>                                                                              '
				+ '    <ul id=locations_"' + item_config.id + '" class="nav nav-pills">                '
				+ item_config.locations
				+ '    </ul>                                                                           '
				+ '    <i class="icon-plus-sign icon-large add-location"                               '
				+ '      onclick="javascript:pgu_geo.addNewLocation(\'' + item_config.id + '\');"      '
				+ '      >                                                                             '
				+ '    </i>                                                                            '
				+ '  </td>                                                                             '
				+ '  <td>' + item_config.dates + '</td>                                                '
				+ '  <td>' + item_config.short_content + '</td>                                        '
				+ '  <td style="cursor:pointer"                                                        '
				+ '      onclick="javascript:$(\'#info_' + item_config.id + '\').popover(\'toggle\');" '
				+ '      >                                                                             '
				+ '    <i id="info_' + item_config.id + '" class="icon-info-sign icon-large"           '
				+ '      data-animation="true"                                                         '
				+ '      data-html="true"                                                              '
				+ '      data-placement="left"                                                         '
				+ '      data-title="' + item_config.content_title + '"                                '
				+ '      data-content="' + item_config.long_content + '"                               '
				+ '     ></i>                                                                          '
				+ '   </td>                                                                            '
				+ '</tr>                                                                               '
				+ '';
    }-*/;

    public static native String labelXpTitle(JavaScriptObject position) /*-{
		//  SFEIR<br/>Senior Web Java J2EE Engineer Developer
		var title = [];

		if (position.company && position.company.name) {

			title.push(position.company.name);
		}

		if (position.title) {
			title.push(position.title);
		}

		return title.join('<br/>');
    }-*/;

    public static native String labelEduTitle(JavaScriptObject education) /*-{
		// Universität Rostock<br/>International Trade
		var title = [];

		if (education.schoolName) {
			title.push(education.schoolName);
		}

		if (education.fieldOfStudy) {
			title.push(education.fieldOfStudy);
		}

		return title.join('<br/>');
    }-*/;

    public static native String createListLocations(String item_config_id) /*-{
		var
		  location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(item_config_id)
		, list = []
		, cache_anchor = $wnd.pgu_geo.cache_location2anchorIds
		;

        for (var k =0, len=location_names.length; k <len; k++) {

			var location_name = location_names[k];
			var anchor_id = "loc_" + item_config_id + "_" + k;

			var anchor_ids = cache_anchor[location_name];
			if (!anchor_ids) {
			    cache_anchor[location_name] = [];
			}
			cache_anchor[location_name].push(anchor_id);

			var el = '' +
			'      <li class="locationLi">                                        ' +
			'        <a id="' + anchor_id + '"                                    ' +
			'           href="javascript:;"                                       ' +
			'           onclick="javascript:' +
			'pgu_geo.editLocation(\'' + item_config_id + '\', \'' + location_name + '\');' +
			'           return false;">' + location_name +
			'        </a>                                                         ' +
			'      </li>                                                          ' +
			'';

			list.push(el);


			if (!@pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name)) {
				var delayMillis = @pgu.client.profile.ui.ProfileViewUtils::delayForCallingGeocoder;
				@pgu.client.profile.ui.ProfileViewUtils::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,delayMillis);
				@pgu.client.profile.ui.ProfileViewUtils::incrementDelayForCallingGeocoder()();
			}

		}

		return list.join('');
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

        if (LocationsUtils.isLocationInReferential(locationName)) {
            return;
        }

        geocode(locationName);

    }

    public static native void geocode(String location_name) /*-{
        var
          geocoder = $wnd.geocoder
        , google = $wnd.google
        , map = $wnd.map
        ;

        geocoder
            .geocode(
                    {
                        'address' : location_name
                    },
                    function(results, status) {

                        if (status == google.maps.GeocoderStatus.OK) {

                            var loc = results[0].geometry.location
                            , lat = '' + loc.lat()
                            , lng = '' + loc.lng()
                            ;

                            @pgu.client.app.utils.LocationsUtils::updateLocationReferential(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);

                        } else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {

                            //                              var anchor = $('#' + anchor_id);
                            //                              anchor.addClass('locationNotFound');
                            //                              anchor.attr('title', 'Unknown location');

                            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown location: "
                                    + location_name + ", " + status);

                        } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {

                            @pgu.client.profile.ui.ProfileViewUtils::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,1000);

                            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("over_query_limit... " + location_name);

                        } else {

                            //                              var anchor = $('#' + anchor_id);
                            //                              anchor
                            //                                      .addClass('locationNotFound_technicalError');
                            //                              anchor.attr('title',
                            //                                      'Location not found because of a technical exception: '
                            //                                              + status);

                            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Oups: " + status);
                        }

                    }
            );
        }-*/;

    public static native int nbItems() /*-{
        return $wnd.pgu_geo.item_configs.length;
    }-*/;

    public static native JavaScriptObject getItemConfig(int i) /*-{
        return $wnd.pgu_geo.item_configs[i];
    }-*/;

}

