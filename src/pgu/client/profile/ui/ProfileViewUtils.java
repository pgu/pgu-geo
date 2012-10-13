package pgu.client.profile.ui;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

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

            // TODO PGU clean this
            $wnd.pgu_geo.item_configs.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );

            @pgu.client.profile.ui.PublicProfileUtils::sortProfileItems(Ljava/lang/String;)(type);

			return table.join('');

		} else {
			return empty_message;
		}

    }-*/;

    public static native String createTableHead(final String type) /*-{
		var title = '';

		if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)(type)) {
			title = 'Education';

		} else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {
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

		var item_config = @pgu.client.app.utils.ProfileItemsUtils::toProfileItem(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type,item);
		var item_locations = @pgu.client.profile.ui.ProfileViewUtils::createListLocations(Ljava/lang/String;)(item_config.id);

		$wnd.pgu_geo.item_configs.push(item_config);

		return ''
				+ '<tr>                                                                                '
				+ '  <td>                                                                              '
				+ '    <ul id="locations_' + item_config.id + '" class="nav nav-pills">                '
				+ item_locations
				+ '    </ul>                                                                           '
				+ '    <i class="icon-plus-sign icon-large add-location"                               '
				+ '      onclick="javascript:pgu_geo.add_new_location(\'' + item_config.id + '\');"      '
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

    public static native String createListLocations(String item_config_id) /*-{
		var
		  location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(item_config_id)
		, list = []
		, cache_anchor = $wnd.pgu_geo.cache_location2anchorIds
		;

        for (var i=0, len=location_names.length; i <len; i++) {

			var location_name = location_names[i];
			var anchor_id = "loc_" + item_config_id + "_" + i;

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
			'pgu_geo.edit_location(\'' + item_config_id + '\', \'' + location_name + '\');' +
			'           return false;">' + location_name +
			'        </a>                                                         ' +
			'      </li>                                                          ' +
			'';

			list.push(el);
		}

		return list.join('');
    }-*/;

    // TODO to delete
    public static native int nbItems() /*-{
        return $wnd.pgu_geo.item_configs.length;
    }-*/;

    // TODO to delete
    public static native JavaScriptObject getItemConfig(int i) /*-{
        return $wnd.pgu_geo.item_configs[i];
    }-*/;

    public static native void refreshHtmlLocationsForItem(final String item_config_id) /*-{

        var html_locations = @pgu.client.profile.ui.ProfileViewUtils::createListLocations(Ljava/lang/String;)(item_config_id);
        $doc.getElementById("locations_" + item_config_id).innerHTML = html_locations;

    }-*/;


}

