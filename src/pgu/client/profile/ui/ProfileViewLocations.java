package pgu.client.profile.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.LocationsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewLocations {

    private final GoogleHelper google = new GoogleHelper();
    private final LocationsHelper locations = new LocationsHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    public native void initCaches( //
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

    public native JavaScriptObject cacheItems() /*-{
        return $wnd.pgu_geo.cache_items;
    }-*/;

    public native void addCurrentLocationToCache(String location_name, ProfileViewImpl view) /*-{
        if (location_name) {
            var
                cache = $wnd.pgu_geo.cache_items
              , key = @pgu.shared.utils.ItemType::current
            ;

            var locations = cache[key] || [];
            if (locations.length === 0) {

                var new_location = [];
                new_location.push(location_name);
                cache[key] = new_location;

            } else if (locations.length === 1) {

                var curr_location = locations[0];
                if (curr_location !== location_name) {

                    this.@pgu.client.profile.ui.ProfileViewLocations::removeLocationFromItem(Ljava/lang/String;Ljava/lang/String;)
                         (key, location_name);

                    var new_location = [];
                    new_location.push(location_name);
                    cache[key] = new_location;
                }

            } else {
                throw {
                    name: 'current location'
                  , msg: 'More than one current location'
                  , locations: locations
                }
            }

            var callback = function(status) {

                $wnd.console.log('addCurrentLocationToCache');

                var google = this.@pgu.client.profile.ui.ProfileViewLocations::google()
                                  ();

                if (status === google.maps.GeocoderStatus.OK) {

                    view.@pgu.client.profile.ui.ProfileViewImpl::createMarkerOnProfileMap(Ljava/lang/String;)
                         (location_name);
                }
            };

            view.@pgu.client.profile.ui.ProfileViewImpl::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
                 (location_name, callback);

        }
    }-*/;

    public native void removeLocationFromItem(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.cache_items
          , cache_referential = $wnd.pgu_geo.cache_referential
        ;

        this.@pgu.client.profile.ui.ProfileViewLocations::removeLocationFromItem(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)
             (cache_items, cache_referential, item_config_id, location_name);
    }-*/;

    private void removeLocationFromItem(final JavaScriptObject cache_items, final JavaScriptObject cache_referential, final String item_config_id, final String location_name) {
        locations.removeLocationFromItem(cache_items, cache_referential, item_config_id, location_name);
    }

    public native void updateLocationsCacheFromPositions() /*-{

        var
            p = $wnd.pgu_geo.profile
          , positions = p.positions || {}
          , position_values = positions.values || []
        ;

        for (var i = 0, len = position_values.length; i < len; i++) {
            var
                position = position_values[i]
              , experience_id = position.id
              , location = position.location || {}
              , location_names = location.name
            ;

            if (location_names) {
                var locations = location_names.split(";");
                for (var j = 0, lenL = locations.length; j < lenL; j++) {
                    var
                        raw_location = locations[j]
                      , location_name = raw_location.trim()
                    ;

                    this.@pgu.client.profile.ui.ProfileViewLocations::addExperienceLocationToCache(DLjava/lang/String;)
                         (experience_id, location_name);
                }
            }
        }

    }-*/;

    private native void addExperienceLocationToCache(double experience_id, String location_name) /*-{
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

            view.@pgu.client.profile.ui.ProfileViewImpl::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
                 (location_name);

        }
    }-*/;

    public native void showProfileItemsOnProfileMap() /*-{
        var
            cache_referential = $wnd.pgu_geo.cache_referential
          , cache_items = $wnd.pgu_geo.cache_items
        ;

        for (var key in cache_items) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_items.hasOwnProperty(key)) {

                var locations = cache_items[key];
                for ( var j = 0, len = locations.length; j < len; j++) {

                    var location = locations[j];
                    view.@pgu.client.profile.ui.ProfileViewImpl::createMarkerOnProfileMap(Ljava/lang/String;)
                         (location);
                }
            }
        }

    }-*/;

    public native void removeUnusedLocations() /*-{

        var
            cache_referential = $wnd.pgu_geo.cache_referential
          , cache_items = $wnd.pgu_geo.cache_items
        ;

        var locations_from_items = [];

        // for each item, avoir un array de location names
        for (var key in cache_items) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_items.hasOwnProperty(key)) {

                var locations = cache_items[key];
                locations_from_items = locations_from_items.concat(locations);
            }
        }

        // for each location of the referential, indexof location in array, if == -1, remove location from referential
        var unused_locations = [];

        for (var location_name in cache_referential) {
            if ('__gwt_ObjectId' === location_name) {
                continue;
            }
            if (cache_referential.hasOwnProperty(location_name)) {

                if (location_from_items.indexOf(location_name) === -1) {
                    unused_locations.push(location_name);
                }
            }
        }

        for (var i = 0; i < unused_locations.length; i++) {
            var unused_location = unused_locations[i];
            delete cache_referential[unused_location];
        }

    }-*/;

}
