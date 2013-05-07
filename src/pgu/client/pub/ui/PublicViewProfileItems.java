package pgu.client.pub.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.LocationsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewProfileItems {

    private final LocationsHelper locations = new LocationsHelper();
    private final PublicViewMarkers markers = new PublicViewMarkers();
    private final PublicViewMap map = new PublicViewMap();
    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    private JavaScriptObject getLocationNames(final String item_config_id) {
        return locations.getLocationNames(item_config_id);
    }

    private boolean isLocationInReferential(final String location_name) {
        return locations.isLocationInReferential(location_name);
    }

    private JavaScriptObject getGeopoint(final String location_name) {
        return locations.getGeopoint(location_name);
    }

    private JavaScriptObject createMarkerWithGeopoint(final JavaScriptObject map, final String location_name, final String lat, final String lng) {
        return markers.createMarkerWithGeopoint(map, location_name, lat, lng);
    }

    private JavaScriptObject getPublicMap() {
        return map.getPublicMap();
    }

    public native void setProfileItems() /*-{

        var profile = $wnd.pgu_geo.public_profile;

        $wnd.pgu_geo.type_2_profile_items = {};

        var
            type_2_profile_items = $wnd.pgu_geo.type_2_profile_items
          , experience = @pgu.shared.utils.ItemType::experience
          , education = @pgu.shared.utils.ItemType::education
        ;

        if (profile.positions) {
            type_2_profile_items[experience] = profile.positions;
        }

        if (profile.educations) {
            type_2_profile_items[education] = profile.educations;
        }

        // check if profile has several sections
        var nb_sections = 0;
        for (var type in type_2_profile_items) {

            if ('__gwt_ObjectId' === type) {
                continue;
            }

            if (type_2_profile_items.hasOwnProperty(type)) {
                nb_sections++;
            }

            if (nb_sections == 2) {
                break;
            }
        }

        if (nb_sections == 2) {

            var all_items = [];
            for (var type in type_2_profile_items) {

                if ('__gwt_ObjectId' === type) {
                    continue;
                }

                if (type_2_profile_items.hasOwnProperty(type)) {
                    all_items = all_items.concat(type_2_profile_items[type]);
                }
            }

            for (var i = 0, len = all_items.length; i < len; i++) {
                var item = all_items[i];
                item.startD = new Date(item.startD);
            }

            type_2_profile_items['all'] = all_items; // we sort 'all' items only as the others already have an order from linkedin
            this.@pgu.client.pub.ui.PublicViewProfileItems::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)
                 (all_items);
        }

    }-*/;

    private native void sortProfileItemsByDateFromOldToNew(JavaScriptObject profile_items) /*-{
        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
    }-*/;

    public native boolean hasAllOption() /*-{
        return $wnd.pgu_geo.type_2_profile_items.hasOwnProperty('all');
    }-*/;

    public native boolean hasExperienceOption() /*-{
        return $wnd.pgu_geo.type_2_profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::experience);
    }-*/;

    public native boolean hasEducationOption() /*-{
        return $wnd.pgu_geo.type_2_profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::education);
    }-*/;

    public native void setSelectedProfileItems(final String selectedItemType) /*-{
        $wnd.pgu_geo.selected_profile_items = $wnd.pgu_geo.type_2_profile_items[selectedItemType];
    }-*/;

    public native int nbSelectedItems() /*-{
        var selected_profile_items = $wnd.pgu_geo.selected_profile_items;

        if (selected_profile_items) {
            return selected_profile_items.length;
        }

        return 0;
    }-*/;

    public native void initCachesLocation2MarkerAndItems(PublicViewImpl view) /*-{

        var google = this.@pgu.client.pub.ui.PublicViewProfileItems::google()
                          ();

        $wnd.pgu_geo.type_2_locations = {};
        $wnd.pgu_geo.location_2_marker = {};
        $wnd.pgu_geo.location_2_items = {};

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        var type_2_profile_items = $wnd.pgu_geo.type_2_profile_items;

        for (var type in type_2_profile_items) {

            if ('__gwt_ObjectId' === type) {
                continue;
            }

            if ('all' === type) {
                continue;
            }

            if (type_2_profile_items.hasOwnProperty(type)) {

                if (!cache_type.hasOwnProperty(type)) {
                    cache_type[type] = [];
                }

                var
                    profile_items = type_2_profile_items[type]
                ;

                for (var k = 0; k < profile_items.length; k++) {

                    var
                        profile_item = profile_items[k]
                      , location_names = this.@pgu.client.pub.ui.PublicViewProfileItems::getLocationNames(Ljava/lang/String;)
                                              (profile_item.id)
                    ;

                    var click_on_show_items = function(loc_name) {
                        return function() {
                                view.@pgu.client.pub.ui.PublicViewImpl::showItemsForLocation(Ljava/lang/String;)
                                     (loc_name);
                        }
                    }

                    for (var i = 0; i < location_names.length; i++) {

                        var
                            location_name = location_names[i]
                          , geopoint_is_available = this.@pgu.client.pub.ui.PublicViewProfileItems::isLocationInReferential(Ljava/lang/String;)
                                                         (location_name)
                        ;

                        if (geopoint_is_available) {

                            if (!cache_marker.hasOwnProperty(location_name)) {

                                var
                                    geopoint = this.@pgu.client.pub.ui.PublicViewProfileItems::getGeopoint(Ljava/lang/String;)
                                                    (location_name)
                                  , lat = geopoint.lat
                                  , lng = geopoint.lng
                                ;

                                var marker = this.@pgu.client.pub.ui.PublicViewProfileItems::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                                                  (null,location_name,lat,lng);

                                google.maps.event.addListener(marker, 'click', click_on_show_items(location_name));

                                // TODO PGU counter on how many items are associated to this marker and
                                // then overrides the marker's title? marker.setTitle(location_name + ': <b>3</b>');

                                cache_marker[location_name] = marker;
                            }

                            if (cache_type[type].indexOf(location_name) == -1) {
                                cache_type[type].push(location_name);
                            }

                            if (!cache_items.hasOwnProperty(location_name)) {
                                cache_items[location_name] = [].concat(profile_item);

                            } else {
                                cache_items[location_name].push(profile_item);
                            }

                        }
                    }
                }

            }
        }

        for (var location_name in cache_items) {

            if ('__gwt_ObjectId' === location_name) {
                continue;
            }

            var items = cache_items[location_name];
            this.@pgu.client.pub.ui.PublicViewProfileItems::sortProfileItemsByDateFromNewToOld(Lcom/google/gwt/core/client/JavaScriptObject;)
                 (items);
        }

    }-*/;

    public native void sortProfileItemsByDateFromNewToOld(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return b.startD.getTime() - a.startD.getTime() } );
    }-*/;

    public native void displayProfileMarkers(final String selectedItemType) /*-{
        var public_map = this.@pgu.client.pub.ui.PublicViewProfileItems::getPublicMap()
                              ();

        this.@pgu.client.pub.ui.PublicViewProfileItems::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)
             (public_map, selectedItemType);
    }-*/;

    public native void setMapOnProfileMarkers(JavaScriptObject map, String selectedItemType) /*-{

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
                    this.@pgu.client.pub.ui.PublicViewProfileItems::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)
                         (map, location_names);
                }
            }

        } else {

            var location_names = cache_type[selectedItemType];
            this.@pgu.client.pub.ui.PublicViewProfileItems::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)
                 (map, location_names);
        }

    }-*/;

    public native void setMapOnProfileMarkersInternal(JavaScriptObject map, JavaScriptObject location_names) /*-{

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

    public native void hideProfileMarkers(String selectedItemType) /*-{
        this.@pgu.client.pub.ui.PublicViewProfileItems::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)
             (null, selectedItemType);
    }-*/;

    public native void showMovieProfileItemLocations(final int token, final JavaScriptObject map) /*-{

        var selected_profile_items = $wnd.pgu_geo.selected_profile_items;
        if (!selected_profile_items) {
            return;
        }

        var
            profile_item = selected_profile_items[token]
          , location_names = this.@pgu.client.pub.ui.PublicViewProfileItems::getLocationNames(Ljava/lang/String;)
                                  (profile_item.id)
          , info_content = []
          , first_marker = null
        ;

        info_content.push('<div>');

        if (profile_item.short_content) {
            info_content.push('<p style="margin-top:5px"><strong>' + profile_item.short_content + '</strong></p>');
        }

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];

            var marker = this.@pgu.client.pub.ui.PublicViewProfileItems::createMovieMarkerOnPublicMap(Ljava/lang/String;)
                              (location_name);
            if (i === 0) {
                first_marker = marker;
            }

            info_content.push('<p style="font-size:large">' + location_name + '</p>');
        }

//        if ('<div>' !== info_content.join('')) {
//            info_content.push('<br/>');
//        }

//        if (profile_item.dates) {
//            info_content.push('<p>' + profile_item.dates + '</p>');
//            info_content.push('<br/>');
//        }

        info_content.push('</div>');

        if (!$wnd.pgu_geo.public_profile_info_window) {
            var google = this.@pgu.client.pub.ui.PublicViewProfileItems::google()
                              ();
            $wnd.pgu_geo.public_profile_info_window = new google.maps.InfoWindow();
        }

        if (first_marker == null) {
            first_marker = this.@pgu.client.pub.ui.PublicViewProfileItems::createMovieUnknownMarkerOnPublicMap()
                                ();
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

    private JavaScriptObject createMovieMarkerOnPublicMap(final String location_name) {
        return markers.createMovieMarkerOnPublicMap(location_name);
    }

    public native String getSelectedProfileItemDescription(int token) /*-{
        var
            selected_profile_items = $wnd.pgu_geo.selected_profile_items
          , profile_item = selected_profile_items[token]
        ;

        var info = [
            '<p><strong>' + profile_item.short_content + '</strong></p>'
          , '<p>' + profile_item.dates.replace('<br/>', ' - ') + '</p>'
          , '<p>' + profile_item.long_content + '</p>'
        ];

        return info.join('');
    }-*/;

    public native void fillViewWithProfileItems(final PublicViewImpl view, final String location_name) /*-{

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

            if (item.short_content) {
                title.push('<p><strong>');
                title.push(item.short_content);
                title.push('</strong></p>');
            }

            if (item.dates) {
                title.push('<p>');
                title.push(item.dates.replace('<br/>', ' - ').replace('<br/>', '  '));
                title.push('</p>');
            }

            view.@pgu.client.pub.ui.PublicViewImpl::fillWithProfileItem(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                 (id, title.join(''), content);
        }

    }-*/;

    private JavaScriptObject createMovieUnknownMarkerOnPublicMap() {
        return markers.createMovieUnknownMarkerOnPublicMap();
    }

}
