package pgu.client.profile.ui;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewTables {

    public native void initCaches() /*-{
        $wnd.pgu_geo.cache_location2anchorIds = {};
        $wnd.pgu_geo.item_configs = [];
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

            var tableHead = this.@pgu.client.profile.ui.ProfileViewTables::createTableHead(Ljava/lang/String;)
                                 (type);
            table.push(tableHead);

            for (var i = 0, len = values.length; i < len; i++) {
                var value = values[i];

                var tableRow = this.@pgu.client.profile.ui.ProfileViewTables::createTableRow(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
                                    (type, value);

                table.push(tableRow);
            }

            var tableFoot = this.@pgu.client.profile.ui.ProfileViewTables::createTableFoot()
                                 ();
            table.push(tableFoot);

            // TODO PGU clean this
            $wnd.pgu_geo.item_configs.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );

            return table.join('');

        } else {
            return empty_message;
        }

    }-*/;

    private native String createTableHead(final String type) /*-{
        var title = '';

        if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)
            (type)) {
            title = 'Education';

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)
                   (type)) {
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

    private static native String createTableFoot() /*-{
        return '' +
        '  </tbody>                                        ' +
        '</table>                                          ' +
        '';

    }-*/;

    private native String createTableRow(final String type, final JavaScriptObject item) /*-{

        var item_config = this.@pgu.client.profile.ui.ProfileViewTables::toProfileItem(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
                          (type,item);
        $wnd.pgu_geo.item_configs.push(item_config);

        return ''
                + '<tr>                                                                                '
                + '  <td>                                                                              '
                + '    <ul id="locations_' + item_config.id + '" class="nav nav-pills">                '
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

    public native JavaScriptObject toProfileItem(String type, JavaScriptObject item) /*-{

        var profile_item = {};

        profile_item.id = type + '_' + item.id;
        profile_item.type = type;

        profile_item.dates = @pgu.client.profile.ui.ProfileDateUtils::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
        profile_item.startD = @pgu.client.profile.ui.ProfileDateUtils::getStartDate(Lcom/google/gwt/core/client/JavaScriptObject;)(item);

        if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelEduTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Education";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.notes);

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Experience";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.summary);

        } else {

            profile_item.short_content = "";
            profile_item.content_title = "";
            profile_item.long_content = "";

            throw {
                name: 'Unknown type'
              , msg: type
            }
        }

        return profile_item;
    }-*/;


    public native void updateTablesWithLocations() /*-{

        var items = $wnd.pgu_geo.item_configs;
        for (var i = 0, len = items.length; i < len; i++) {

            var item = items[i];
            this.@pgu.client.profile.ui.ProfileViewTables::refreshHtmlLocationsForItem(Ljava/lang/String;)
                 (item.id);
        }

    }-*/;

    public native void refreshHtmlLocationsForItem(final String item_config_id) /*-{

        var html_locations = this.@pgu.client.profile.ui.ProfileViewTables::createListLocations(Ljava/lang/String;)
                                  (item_config_id);
        $doc.getElementById('locations_' + item_config_id).innerHTML = html_locations;

    }-*/;


    private native String createListLocations(String item_config_id) /*-{
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

}
