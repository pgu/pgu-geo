package pgu.client.app.utils;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsUtils {

    private static native JavaScriptObject profileItems() /*-{
        return $wnd.pgu_geo.profile_items;
    }-*/;

    private static native JavaScriptObject selectedProfileItems() /*-{
        return $wnd.pgu_geo.selected_profile_items;
    }-*/;

    public static native void setProfileItems(final JavaScriptObject profile) /*-{

        $wnd.pgu_geo.profile_items = {};

        var
            profile_items = $wnd.pgu_geo.profile_items
          , experience = @pgu.shared.utils.ItemType::experience
          , education = @pgu.shared.utils.ItemType::education
        ;

        if (profile.positions) {
            profile_items[experience] = profile.positions;
        }

        if (profile.educations) {
            profile_items[education] = profile.educations;
        }

        // check if profile has several sections
        var nb_sections = 0;
        for (var key in profile_items) {

            if ('__gwt_ObjectId' === key) {
                continue;
            }

            if (profile_items.hasOwnProperty(key)) {
                nb_sections++;
            }

            if (nb_sections == 2) {
                break;
            }
        }

        if (nb_sections == 2) {

            var all_items = [];
            for (var key in profile_items) {

                if ('__gwt_ObjectId' === key) {
                    continue;
                }

                if (profile_items.hasOwnProperty(key)) {
                    all_items = all_items.concat(profile_items[key]);
                }
            }

            for (var i = 0, len = all_items.length; i < len; i++) {
                var item = all_items[i];
                item.startD = new Date(item.startD);
            }

            profile_items['all'] = all_items; // we sort 'all' items only as the others already have an order from linkedin
            @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDate(Lcom/google/gwt/core/client/JavaScriptObject;)(all_items);
        }

    }-*/;

    private static native void sortProfileItemsByDate(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
    }-*/;

    private static boolean isEdu(final String itemType) {
        return ItemType.education.equals(itemType);
    }

    private static boolean isXp(final String itemType) {
        return ItemType.experience.equals(itemType);
    }

    private static native String labelXpTitle(JavaScriptObject position) /*-{
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

    private static native String labelEduTitle(JavaScriptObject education) /*-{
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


    public static native JavaScriptObject toProfileItem(String type, JavaScriptObject item) /*-{

        var profile_item = {};
        profile_item.id = type + '_' + item.id;

        profile_item.dates = @pgu.client.profile.ui.ProfileDateUtils::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
        profile_item.startD = @pgu.client.profile.ui.ProfileDateUtils::getStartDate(Lcom/google/gwt/core/client/JavaScriptObject;)(item);

        if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelEduTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Education";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.notes);

            @pgu.client.profile.ui.PublicProfileUtils::addEducation(Lcom/google/gwt/core/client/JavaScriptObject;)(profile_item);

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Experience";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.summary);

            @pgu.client.profile.ui.PublicProfileUtils::addExperience(Lcom/google/gwt/core/client/JavaScriptObject;)(profile_item);

        } else {

            profile_item.short_content = "";
            profile_item.content_title = "";
            profile_item.long_content = "";

            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown type " + type);
        }

        return profile_item;
    }-*/;

    public static native boolean hasAllOption() /*-{
        var profile_items = @pgu.client.app.utils.ProfileItemsUtils::profileItems()();
        return profile_items.hasOwnProperty('all');
    }-*/;

    public static native boolean hasExperienceOption() /*-{
        var profile_items = @pgu.client.app.utils.ProfileItemsUtils::profileItems()();
        return profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::experience);
    }-*/;

    public static native boolean hasEducationOption() /*-{
        var profile_items = @pgu.client.app.utils.ProfileItemsUtils::profileItems()();
        return profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::education);
    }-*/;

    public static native void setSelectedProfileItems(final String selectedItemType) /*-{
        var profile_items = @pgu.client.app.utils.ProfileItemsUtils::profileItems()();
        $wnd.pgu_geo.selected_profile_items = profile_items[selectedItemType];
    }-*/;

    public static native int nbSelectedItems() /*-{
        var selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()();

        if (selected_profile_items) {
            return selected_profile_items.length;
        }

        return 0;
    }-*/;

    public static native void showProfileItemLocations(final int token, final JavaScriptObject map) /*-{

        var selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()();
        if (!selected_profile_items) {
            return;
        }

        var
            profile_item = selected_profile_items[token]
          , location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(profile_item.id)
          , info_content = []
          , first_marker = null
        ;

        info_content.push('<div>');

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];

            var marker = @pgu.client.app.utils.MarkersUtils::createMarkerOnPublicMap(Ljava/lang/String;)(location_name);
            if (i === 0) {
                first_marker = marker;
            }

            info_content.push('<div>' + location_name + '</div>');
        }

        if ('<div>' !== info_content.join('')) {
            info_content.push('<br/>');
        }

        if (profile_item.dates) {
            info_content.push('<div>' + profile_item.dates + '</div>');
            info_content.push('<br/>');
        }

        if (profile_item.short_content) {
            info_content.push('<div>' + profile_item.short_content + '</div>');
        }

        info_content.push('</div>');

        if (!$wnd.pgu_geo.public_profile_info_window) {
            var google = @pgu.client.app.utils.GoogleUtils::google()();
            $wnd.pgu_geo.public_profile_info_window = new google.maps.InfoWindow();
        }

        if (first_marker == null) {
            first_marker = @pgu.client.app.utils.MarkersUtils::createUnknownMarkerOnPublicMap()();
        }

        var info_content_str = info_content.join('');

        var info = $wnd.pgu_geo.public_profile_info_window;
        info.setContent(info_content_str);

        if (info_content_str === '<div></div>') {
            info.close();

        } else {
            info.open(map, first_marker);
        }

    }-*/;

    public static native String getSelectedProfileItemDescription(int token) /*-{
        var
            selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()()
          , profile_item = selected_profile_items[token]
        ;

        return profile_item.long_content;
    }-*/;

    public static native JavaScriptObject cacheLocation2itemAndMarkers() /*-{
        return $wnd.pgu_geo.location_2_marker_items;
    }-*/;

    public static native void initCacheLocation2itemAndMarkers() /*-{

        $wnd.pgu_geo.type_2_locations = {};
        $wnd.pgu_geo.location_2_marker = {};
        $wnd.pgu_geo.marker_2_items = {};

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_location = $wnd.pgu_geo.location_2_marker
          , cache_marker = $wnd.pgu_geo.marker_2_items
        ;
        // 'type' >> 'location' >> 'marker' >> 'liste de profile_items'

        var profile_items = @pgu.client.app.utils.ProfileItemsUtils::profileItems()();

        for (var type in profile_items) {

            if ('__gwt_ObjectId' === type) {
                continue;
            }

            if ('all' === type) {
                continue;
            }

            if (profile_items.hasOwnProperty(type)) {
                var
                    profile_item = profile_items[key]
                  , location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(profile_item.id)
                ;

                var location_2_items = {};

                for (var i = 0; i < location_names.length; i++) {
                    var location_name = location_names[i];

                    if (!location_2_items.hasOwnProperty(location_name)) {
                        location_2_items[location_name] = [];
                    }

                    var geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
                    if (geopoint_is_available) {

                        var
                            geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name)
                          , lat = geopoint.lat
                          , lng = geopoint.lng
                        ;

// TODO PGU marker.setClick(display profile_items)
// TODO PGU review data structures: 1location, 1marker, X profile_items
                        var marker_and_item = {};
                        marker_and_item['marker'] = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(null,location_name,lat,lng);
                        marker_and_item['item'] = profile_item;
                        location_2_items[location_name].push(marker_and_item);

                    }
                }

                cache[key] = location_2_items;
            }
        }


    }-*/;

    public static native void displayProfileMarkers(final String selectedItemType) /*-{
        var public_map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        public_map, selectedItemType);
    }-*/;

    public static native void hideProfileMarkers(String selectedItemType) /*-{
        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        null, selectedItemType);
    }-*/;

    private static native void setMapOnProfileMarkers(JavaScriptObject map, String selectedItemType) /*-{
        var cache = @pgu.client.app.utils.ProfileItemsUtils::cacheLocation2itemAndMarkers()();

        if ('all' === selectedItemType) {

            for (var key in cache) {
                if ('__gwt_ObjectId' === key) {
                    continue;
                }

                if (cache.hasOwnProperty(key)) {
                    var location_names = cache[key];
                    @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
                    map, location_names);
                }
            }

        } else {

            var location_names = cache[selectedItemType];
            @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
            map, location_names);

        }

    }-*/;

    private static native void setMapOnProfileMarkersInternal(JavaScriptObject map, JavaScriptObject location_names) /*-{

        for (var key in location_names) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }

            if (location_names.hasOwnProperty(key)) {
                var item_and_markers = location_names[key];

                for (var i = 0; i < item_and_markers.length; i++) {
                    var item_and_marker = item_and_markers[i];

                    item_and_marker['marker'].setMap(map);
                }
            }
        }

    }-*/;

}
