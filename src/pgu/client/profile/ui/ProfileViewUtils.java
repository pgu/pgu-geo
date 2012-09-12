package pgu.client.profile.ui;

import pgu.client.app.utils.ClientUtils;
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

    public static native void initCacheLocation2AnchorIds() /*-{
		$wnd.pgu_geo.cache_location2anchorIds = {};
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

			@pgu.client.profile.ui.ProfileViewUtils::sortItemConfigsByDate()();

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

		return '' + '<table class="table table-bordered table-striped"> '
				+ '   <thead>                                         '
				+ '      <tr>                                         '
				+ '          <th>Locations</th>                        '
				+ '          <th>Dates</th>                           '
				+ '          <th>' + title + '</th>                   '
				+ '          <th></th>                                '
				+ '      </tr>                                        '
				+ '  </thead>                                         '
				+ '  <tbody>                                          ' //
				+ '';

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
		rowConfig.locations = @pgu.client.profile.ui.ProfileViewUtils::createListLocations(Ljava/lang/String;Ljava/lang/String;)(type,item.id);
		rowConfig.dates = @pgu.client.profile.ui.ProfileDateUtils::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
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

        if (ClientUtils.isLocationInReferential(locationName)) {
            return;
        }

        geocode(locationName);

    }

    public static native void geocode(String locationName) /*-{
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

                            //                              var anchor = $('#' + anchor_id);
                            //                              anchor.addClass('locationNotFound');
                            //                              anchor.attr('title', 'Unknown location');
                            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown location: "
                                    + locationName + ", " + status);

                        } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {

                            @pgu.client.profile.ui.ProfileViewUtils::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,1000);

                            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("over_query_limit... "
                                    + itemLocation.name);

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

}
