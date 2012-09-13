package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;


public class MarkersUtils {

    static {
        initMarkersArray();
    }

    private static native void initMarkersArray() /*-{
        $wnd.pgu_geo.markersArray = [];
    }-*/;

    // https://developers.google.com/maps/documentation/javascript/overlays#RemovingOverlays
    //Removes the overlays from the map, but keeps them in the array
    public static native void clearMarkers() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
          markers[i].setMap(null);
        }
    }-*/;

    // Shows any overlays currently in the array
    public static native void showMarkers() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
            markers[i].setMap(map);
        }
    }-*/;

    // Deletes all markers in the array by removing references to them
    public static native void deleteMarkers() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;

    public static native JavaScriptObject createMarker(String location_name) /*-{

        var google = $wnd.google;
        var map = $wnd.map;

        var geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name);
        var latLng = new google.maps.LatLng(parseFloat(geopoint.lat), parseFloat(geopoint.lng));

        var marker = new google.maps.Marker({
            map : map,
            position : latLng,
            animation : google.maps.Animation.DROP,
            title : location_name
        });

        $wnd.pgu_geo.markersArray.push(marker);

        return marker;

    }-*/;

}
