package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class LocationsHelper {

    public native boolean isLocationInReferential(String location_name) /*-{
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

    public native JavaScriptObject getGeopoint(String location_name) /*-{
        return $wnd.pgu_geo.cache_referential[location_name];
    }-*/;

}
