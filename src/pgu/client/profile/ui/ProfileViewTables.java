package pgu.client.profile.ui;

import pgu.client.app.utils.MarkdownHelper;
import pgu.client.app.utils.ProfileItemsHelper;
import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewTables {

    private final ProfileViewDates viewDates = new ProfileViewDates();
    private final ProfileItemsHelper profileItems = new ProfileItemsHelper();
    private final MarkdownHelper markdown = new MarkdownHelper();

    public native void initCacheAnchor() /*-{
        $wnd.pgu_geo.cache_location2anchorIds = {};
    }-*/;

    public native void initCacheItemConfigs() /*-{
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

    private boolean isEdu(final String type) {
        return profileItems.isEdu(type);
    }

    private boolean isXp(final String type) {
        return profileItems.isXp(type);
    }

    private native String createTableHead(final String type) /*-{
        var title = '';

        if (this.@pgu.client.profile.ui.ProfileViewTables::isEdu(Ljava/lang/String;)
                 (type)) {
            title = 'Education';

        } else if (this.@pgu.client.profile.ui.ProfileViewTables::isXp(Ljava/lang/String;)
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

    private String markdown(final String text) {
        return markdown.markdown(text);
    }

    public native JavaScriptObject toProfileItem(String type, JavaScriptObject item) /*-{

        var profile_item = {};

        profile_item.id = type + '_' + item.id;
        profile_item.type = type;

        profile_item.dates = this.@pgu.client.profile.ui.ProfileViewTables::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)
                                  (item);

        profile_item.startD = this.@pgu.client.profile.ui.ProfileViewTables::getStartDate(Lcom/google/gwt/core/client/JavaScriptObject;)
                                   (item);

        if (this.@pgu.client.profile.ui.ProfileViewTables::isEdu(Ljava/lang/String;)
                 (type)) {

            profile_item.content_title = "Education";
            profile_item.short_content = this.@pgu.client.profile.ui.ProfileViewTables::labelEduTitle(Lcom/google/gwt/core/client/JavaScriptObject;)
                                              (item);
            profile_item.long_content = this.@pgu.client.profile.ui.ProfileViewTables::markdown(Ljava/lang/String;)
                                             (item.notes);

        } else if (this.@pgu.client.profile.ui.ProfileViewTables::isXp(Ljava/lang/String;)
                        (type)) {

            profile_item.content_title = "Experience";
            profile_item.short_content = this.@pgu.client.profile.ui.ProfileViewTables::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)
                                              (item);
            profile_item.long_content = this.@pgu.client.profile.ui.ProfileViewTables::markdown(Ljava/lang/String;)
                                             (item.summary);

        } else {

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

        var location_names = $wnd.pgu_geo.cache_items[item_config_id] || [];

        var
            list = []
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

    private String labelDates(final JavaScriptObject item) {
        return viewDates.labelDates(item);
    }

    private JavaScriptObject getStartDate(final JavaScriptObject item) {
        return viewDates.getStartDate(item);
    }

    private native String labelEduTitle(JavaScriptObject education) /*-{
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

    private native String labelXpTitle(JavaScriptObject position) /*-{
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

}
