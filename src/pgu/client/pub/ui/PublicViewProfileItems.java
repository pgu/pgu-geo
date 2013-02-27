package pgu.client.pub.ui;

import pgu.client.app.utils.LocationsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewProfileItems {

    private final LocationsHelper locations = new LocationsHelper();
    private final PublicViewMarkers markers = new PublicViewMarkers();

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

                                google.maps.event.addListener(marker, 'click', //
                                    function() {
                                        view.@pgu.client.pub.ui.PublicViewImpl::showItemsForLocation(Ljava/lang/String;)
                                             (location_name);
                                    }
                                );

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


}
