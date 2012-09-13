package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class LocationsUtils {

    // item2locations: {"education,1":["Paris","Nantes"],"experience,1":["Madrid"]}
    //    referential: {"Paris":{"lat":1.2323,"lng":4.5555},"Nantes":{"lat":9.99,"lng":2.22}]
    public static native void initCaches( //
            final String items2locations //
            , final String referential //
            ) /*-{

        $wnd.pgu_geo.cache_items2locations = JSON.parse(items2locations);
        $wnd.pgu_geo.cache_referentialLocations = JSON.parse(referential);

    }-*/;


    public static native JavaScriptObject getLocationNames(String fullId) /*-{
        return $wnd.pgu_geo.cache_items2locations[fullId] || [];
    }-*/;

    public static native JavaScriptObject getLocationNamesForItem(String type, String itemId) /*-{
        var fullId = type + ',' + itemId;
        return $wnd.pgu_geo.cache_items2locations[fullId] || [];
    }-*/;

    public static native boolean isLocationInReferential(String locationName) /*-{
        return undefined !== $wnd.pgu_geo.cache_referentialLocations[locationName];
    }-*/;

    public static native void updateLocationReferential(String locationName, String lat, String lng) /*-{

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
