package pgu.client.app.utils;

public class LocationsHelper {

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

                    this.@pgu.client.app.utils.LocationsHelper::removeLocationFromItem(Ljava/lang/String;Ljava/lang/String;)
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

                    @pgu.client.app.utils.MarkersUtils::createMarkerOnProfileMap(Ljava/lang/String;)
                    (location_name);
                }
            };

            @pgu.client.app.utils.GeocoderUtils::searchGeopoint(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)
           (location_name,callback);

        }
    }-*/;

    public native void removeLocationFromItem(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.cache_items
          , cache_referential = $wnd.pgu_geo.cache_referential
        ;

        @pgu.client.app.utils.LocationsUtils::removeLocationFromItemInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)
        (cache_items, cache_referential, item_config_id, location_name);

    }-*/;


}
