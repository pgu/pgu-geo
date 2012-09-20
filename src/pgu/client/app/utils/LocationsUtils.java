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
            $wnd.pgu_geo.cache_items = {};
        } else {
            $wnd.pgu_geo.cache_items = JSON.parse(items2locations);
        }

        if (!referential) {
            $wnd.pgu_geo.cache_referential = {};
        } else {
            $wnd.pgu_geo.cache_referential = JSON.parse(referential);
        }

    }-*/;

    public static native void copyLocationCaches() /*-{
        $wnd.pgu_geo.copy_cache_items = JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.cache_items));


        $wnd.pgu_geo.copy_cache_referential = JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.cache_referential));

    }-*/;

    public static native void removeLocationFromCopyCaches(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.copy_cache_items
          , cache_referential = $wnd.pgu_geo.copy_cache_referential
        ;

        @pgu.client.app.utils.LocationsUtils::removeLocationFromItemInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)(cache_items, cache_referential, item_config_id, location_name);
    }-*/;

    public static native void addCurrentLocationToCache(String location_name) /*-{
        if (location_name) {
            var
                cache = $wnd.pgu_geo.cache_items
              , key = 'current' // see ItemType
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

// TODO show current location
            @pgu.client.app.utils.GeocoderUtils::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(location_name);
        }
    }-*/;

    public static native void addExperienceLocationToCache(double experience_id, String location_name) /*-{
        if (location_name) {
            var
                cache = $wnd.pgu_geo.cache_items
              , key = 'experience_' + experience_id
              , locations = cache[key] || []
              , has_location = false
            ;

            for ( var i = 0, len = locations.length; i < len; i++) {
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

// TODO track search to launch an event "end of search, cache is ready"
            @pgu.client.app.utils.GeocoderUtils::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(location_name);
        }
    }-*/;

    public static native JavaScriptObject getOtherLocationNames(String item_config_id) /*-{

        var
            cache_referential = $wnd.pgu_geo.cache_referential
          , cache_items = $wnd.pgu_geo.cache_items
          , item_locations = cache_items[item_config_id]
          , other_location_names = []
        ;

        for (var key in cache_referential) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_referential.hasOwnProperty(key)) {

                if ($wnd.$.inArray(key, item_locations) == -1) {
                    other_location_names.push(key);
                }
            }
        }

        return other_location_names;
    }-*/;

    public static native boolean isDoublon(String item_config_id, String location_name, String lat, String lng) /*-{
        var
            cache_referential = $wnd.pgu_geo.cache_referential
          , cache_items = $wnd.pgu_geo.cache_items
          , locations = cache_items[item_config_id];
        ;

        if (locations) {

            var is_doublon = false;

            for ( var i = 0, len = locations.length; i < len; i++) {
                var location = locations[i];

                if (location === location_name) {
                    return true;
                }

                var geopoint = cache_referential[location];

                if (geopoint.lat === lat //
                    && geopoint.lng === lng) {
                    return true;
                }
            }
        }
        return false;
    }-*/;

    public static native void addLocation2ItemInCopyCache(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.copy_cache_items
          , locations = cache_items[item_config_id]
        ;

        if (locations) {

            var is_doublon = false;
            for ( var i = 0, len = locations.length; i < len; i++) {
                var location = locations[i];

                if (location === location_name) {
                    is_doublon = true;
                    break;
                }
            }

            if (!is_doublon) {
                locations.push(location_name);
            }

        } else {
            locations = [];
            locations.push(location_name);
            cache_items[item_config_id] = locations;
        }

    }-*/;

    public static native void removeLocationFromItem(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.cache_items
          , cache_referential = $wnd.pgu_geo.cache_referential
        ;

        @pgu.client.app.utils.LocationsUtils::removeLocationFromItemInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)(cache_items, cache_referential, item_config_id, location_name);
    }-*/;

    static native void removeLocationFromItemInternal( //
            JavaScriptObject cache_items //
            , JavaScriptObject cache_referential //
            , String item_config_id //
            , String location_name) /*-{


        var location_names = cache_items[item_config_id];
        var updated_locations = [];

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var name = location_names[i];

            if (location_name !== name) {
                updated_locations.push(name);
            }
        }

        cache_items[item_config_id] = updated_locations;

        var has_at_least_once = false;

        mainloop: for (var key in cache_items) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_items.hasOwnProperty(key)) {

                var locations = cache_items[key];
                for ( var j = 0, len = locations.length; j < len; j++) {

                    var location = locations[j];
                    if (location === location_name) {

                        has_at_least_once = true;
                        break mainloop;
                    }
                }
            }
        }

        if (!has_at_least_once) {
            delete cache_referential[location_name];
        }

    }-*/;

    public static native JavaScriptObject getLocationNames(String item_config_id) /*-{
        return $wnd.pgu_geo.cache_items[item_config_id] || [];
    }-*/;

    public static native boolean isLocationInReferential(String location_name) /*-{
        var cache = $wnd.pgu_geo.cache_referential;

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

    public static native void addGeopointToCopyCache(String location_name, String lat, String lng) /*-{
        var cache = $wnd.pgu_geo.copy_cache_referential;
        @pgu.client.app.utils.LocationsUtils::addGeopointInternal(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(cache,location_name,lat,lng);
    }-*/;

    public static native void addGeopointToCache(String location_name, String lat, String lng) /*-{
        var cache = $wnd.pgu_geo.cache_referential;
        @pgu.client.app.utils.LocationsUtils::addGeopointInternal(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(cache,location_name,lat,lng);
    }-*/;

    public static native void addGeopointInternal(JavaScriptObject cache, String location_name, String lat, String lng) /*-{

        var location = {};
        location.lat = lat;
        location.lng = lng;

        cache[location_name] = location;

    }-*/;

    public static native JavaScriptObject getGeopoint(String location_name) /*-{
        return $wnd.pgu_geo.cache_referential[location_name];
    }-*/;

    public static native String json_copyCacheItems() /*-{
        return @pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
          $wnd.pgu_geo.copy_cache_items);
    }-*/;

    public static native String json_copyCacheReferential() /*-{
        return @pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
          $wnd.pgu_geo.copy_cache_referential);
    }-*/;

    public static native void replaceCachesByCopies() /*-{
        $wnd.pgu_geo.cache_items = $wnd.pgu_geo.copy_cache_items;
        $wnd.pgu_geo.cache_referential = $wnd.pgu_geo.copy_cache_referential;
    }-*/;

    public static native void deleteCopies() /*-{
        $wnd.pgu_geo.copy_cache_items = null;
        $wnd.pgu_geo.copy_cache_referential = null;
    }-*/;

}
