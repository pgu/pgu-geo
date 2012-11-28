package pgu.client.profile.ui;

import pgu.client.app.utils.GeocoderHelper;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkersHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewLocations {

    private final MarkersHelper markers = new MarkersHelper();
    private final GeocoderHelper geocoder = new GeocoderHelper();

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

    public native void addCurrentLocationToCache(String location_name) /*-{
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
                    (key,location_name);

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

                var google = @pgu.client.app.utils.GoogleUtils::google()
                ();

                if (status === google.maps.GeocoderStatus.OK) {

                    this.@pgu.client.profile.ui.ProfileViewLocations::createMarkerOnProfileMap(Ljava/lang/String;)
                    (location_name);
                }
            };

            this.@pgu.client.profile.ui.ProfileViewLocations::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
            (location_name,callback);

        }
    }-*/;

    public native void removeLocationFromItem(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.cache_items
          , cache_referential = $wnd.pgu_geo.cache_referential
        ;

        this.@pgu.client.profile.ui.ProfileViewLocations::removeLocationFromItemInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)
        (cache_items, cache_referential, item_config_id, location_name);
    }-*/;

    private void createMarkerOnProfileMap(final String location_name) {
        markers.createMarkerOnProfileMap(location_name);
    }

    private void searchGeopoint(final String location_name, final JavaScriptObject callback) {
        geocoder.searchGeopoint(location_name, callback);
    }

    private void removeLocationFromItemInternal(final JavaScriptObject cache_items, final JavaScriptObject cache_referential, final String item_config_id, final String location_name) {
        LocationsUtils.removeLocationFromItemInternal(cache_items, cache_referential, item_config_id, location_name);
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

    private void addExperienceLocationToCache(final double experience_id, final String location_name) {
        // TODO PGU Nov 27, 2012 continue review
        // TODO PGU Nov 27, 2012 continue review
        // TODO PGU Nov 27, 2012 continue review
        // TODO PGU Nov 27, 2012 continue review
        // TODO PGU Nov 27, 2012 continue review

        // TODO PGU Nov 28, 2012 export this method here
        LocationsUtils.addExperienceLocationToCache(experience_id, location_name);
    }

}
