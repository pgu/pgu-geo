package pgu.client.app.utils;

import pgu.client.pub.ui.PublicViewImpl;
import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsUtils {

    private static native JavaScriptObject type2profileItems() /*-{
        return $wnd.pgu_geo.type_2_profile_items;
    }-*/;

    private static native JavaScriptObject selectedProfileItems() /*-{
        return $wnd.pgu_geo.selected_profile_items;
    }-*/;

    private static boolean isEdu(final String itemType) {
        return ItemType.education.equals(itemType);
    }

    private static boolean isXp(final String itemType) {
        return ItemType.experience.equals(itemType);
    }

    public static native void showMovieProfileItemLocations(final int token, final JavaScriptObject map) /*-{

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

            var marker = @pgu.client.app.utils.MarkersUtils::createMovieMarkerOnPublicMap(Ljava/lang/String;)(location_name);
            if (i === 0) {
                first_marker = marker;
            }

            info_content.push('<div><b>' + location_name + '</b></div>');
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
            first_marker = @pgu.client.app.utils.MarkersUtils::createMovieUnknownMarkerOnPublicMap()();
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

    public static native void displayProfileMarkers(final String selectedItemType) /*-{
        var public_map = @pgu.client.pub.ui.PublicViewMap::publicProfileMap()();

        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        public_map, selectedItemType);
    }-*/;

    public static native void hideProfileMarkers(String selectedItemType) /*-{
        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        null, selectedItemType);
    }-*/;

    private static native void setMapOnProfileMarkers(JavaScriptObject map, String selectedItemType) /*-{

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        if ('all' === selectedItemType) {

            for (var type in cache_type) {
                if ('__gwt_ObjectId' === type) {
                    continue;
                }

                if (cache_type.hasOwnProperty(type)) {
                    var location_names = cache_type[type];
                    @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
                    map, location_names);
                }
            }

        } else {

            var location_names = cache_type[selectedItemType];
            @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
            map, location_names);

        }

    }-*/;

    private static native void setMapOnProfileMarkersInternal(JavaScriptObject map, JavaScriptObject location_names) /*-{

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        for (var i = 0; i < location_names.length; i++) {
            var
                location_name = location_names[i]
              , marker = cache_marker[location_name]
            ;

            marker.setMap(map);
        }

    }-*/;

    public static native void fillViewWithProfileItems(final PublicViewImpl view, final String location_name) /*-{

        var
            cache_items = $wnd.pgu_geo.location_2_items
          , items = cache_items[location_name]
        ;

        for ( var i = 0; i < items.length; i++) {
            var item = items[i];

            var
                id = item.id
              , title = []
              , content = item.long_content
            ;

            if (item.dates) {
                title.push(item.dates);
                title.push(' - ');
            }

            if (item.short_content) {
                title.push(item.short_content);
            }

            view.@pgu.client.pub.ui.PublicViewImpl::fillWithProfileItem(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
            id, title.join(''), content);
        }

    }-*/;


}
