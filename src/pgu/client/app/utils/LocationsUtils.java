package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class LocationsUtils {

    // item2locations: {"education,1":["Paris","Nantes"],"experience,1":["Madrid"]}
    //    referential: {"Paris":{"lat":1.2323,"lng":4.5555},"Nantes":{"lat":9.99,"lng":2.22}]
    public static native void initCaches( //
            final String items2locations //
            , final String referential //
            ) /*-{

        if (!items2locations) {
            $wnd.pgu_geo.cache_items2locations = {};
        } else {
            $wnd.pgu_geo.cache_items2locations = JSON.parse(items2locations);
        }

        if (!referential) {
            $wnd.pgu_geo.cache_referentialLocations = {};
        } else {
            $wnd.pgu_geo.cache_referentialLocations = JSON.parse(referential);
        }


    }-*/;

    public static native void addCurrentLocationToCache(String location_name) /*-{
        if (location_name) {
            var
                cache = $wnd.pgu_geo.cache_items2locations
              , key = 'current_location'
            ;

            var locations = cache[key] || [];
            if (locations.length === 0) {

                var new_location = [];
                new_location.push(location_name);
                cache[key] = new_location;

            } else if (locations.length === 1) {

                var curr_location = locations[0];
                if (curr_location !== location_name) {

                    @pgu.client.app.utils.LocationsUtils::removeLocationFromItem(Ljava/lang/String;Ljava/lang/String;)(key,location_name);

                    var new_location = [];
                    new_location.push(location_name);
                    cache[key] = new_location;
                }

            } else {
                throw "More than one current location: " + locations;
            }

            @pgu.client.app.utils.GeocoderUtils::searchGeopoint(Ljava/lang/String;)(location_name);
        }
    }-*/;

    public static native void addExperienceLocationToCache(double experience_id, String location_name) /*-{
        if (location_name) {
            var
                cache = $wnd.pgu_geo.cache_items2locations
              , key = 'experience_' + experience_id
              , locations = cache[key] || []
              , has_location = false
            ;

            for (var i in locations) {
                var location = locations[i];
                if (location === location_name) {
                    has_location = true;
                    break;
                }
            }

            if (!has_location) {
                locations.push(location_name);
                cache[key] = locations;
            }

            @pgu.client.app.utils.GeocoderUtils::searchGeopoint(Ljava/lang/String;)(location_name);
        }
    }-*/;

    public static native JavaScriptObject getOtherLocationNames(String item_config_id) /*-{

        var referential = $wnd.pgu_geo.cache_referentialLocations;
        var item_locations = $wnd.pgu_geo.cache_items2locations[item_config_id];

        var other_location_names = [];

        for (var key in referential) {
            if (referential.hasOwnProperty(key)) {

                if ($wnd.$.inArray(key, item_locations) == -1) {
                    other_location_names.push(key);
                }
            }
        }

        return other_location_names;
    }-*/;

    public static native void addLocation2Item(String item_config_id, String location_name) /*-{
        // TODO check for doublon
        $wnd.pgu_geo.cache_items2locations[item_config_id].push(location_name);
    }-*/;

    public static native void removeLocationFromItem(String item_config_id, String location_name) /*-{

        var cache = $wnd.pgu_geo.cache_items2locations;

        var location_names = cache[item_config_id];
        var updated_locations = [];

        for (var i in location_names) {
            var name = location_names[i];

            if (location_name !== name) {
                updated_locations.push(name);
            }
        }

        cache[item_config_id] = updated_locations;

        var has_at_least_once = false;

        mainloop: for (var key in cache) {
            if (cache.hasOwnProperty(key)) {

                var locations = cache[key];
                for (var j in locations) {

                    var location = locations[j];
                    if (location === location_name) {

                        has_at_least_once = true;
                        break mainloop;
                    }
                }
            }
        }

        if (!has_at_least_once) {
            delete $wnd.pgu_geo.cache_referentialLocations[location_name];
        }

    }-*/;

    public static native JavaScriptObject getLocationNames(String item_config_id) /*-{
        return $wnd.pgu_geo.cache_items2locations[item_config_id] || [];
    }-*/;

    public static native boolean isLocationInReferential(String location_name) /*-{
        var cache = $wnd.pgu_geo.cache_referentialLocations;

        var hasProperty = cache.hasOwnProperty(location_name);
        if (!hasProperty) {
            return false;
        }

        var geopoint = cache[location_name];
        if (!geopoint) {
            return false;
        }

        var
            lat = geopoint.lat
          , lng = geopoint.lng
        ;

        if (!lat || !lng) {
            return false
        }

        return true;
    }-*/;

    public static native void addGeopoint(String locationName, String lat, String lng) /*-{

        var location = {};
        location.lat = lat;
        location.lng = lng;

        $wnd.pgu_geo.cache_referentialLocations[locationName] = location;

    }-*/;

    public static native JavaScriptObject getGeopoint(String locationName) /*-{
        return $wnd.pgu_geo.cache_referentialLocations[locationName];
    }-*/;

    public static native String json_items2locations() /*-{
        return JSON.stringify($wnd.pgu_geo.cache_items2locations);
    }-*/;

    public static native String json_referentialLocations() /*-{
        return JSON.stringify($wnd.pgu_geo.cache_referentialLocations);
    }-*/;

}
