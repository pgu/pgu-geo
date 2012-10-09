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
    //    public static native void showMarkers() /*-{
    //        var markers = $wnd.pgu_geo.markersArray;
    //        for (i in markers) {
    //            markers[i].setMap(map);
    //        }
    //    }-*/;

    // Deletes all markers in the array by removing references to them
    public static native void deleteMarkers() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;

    public static native JavaScriptObject createMarkerOnProfileMap(String location_name) /*-{
        var map = @pgu.client.profile.ui.ProfileUtils::profileMap()();

        return @pgu.client.app.utils.MarkersUtils::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(map,location_name);
    }-*/;

    public static native JavaScriptObject createMarkerOnPublicMap(String location_name) /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        return @pgu.client.app.utils.MarkersUtils::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(map,location_name);
    }-*/;

    private static native JavaScriptObject createMarker(JavaScriptObject map, String location_name) /*-{

        var geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
        if (geopoint_is_available) {

            var
                geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name)
              , lat = geopoint.lat
              , lng = geopoint.lng
            ;

            return @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);

        } else {
            throw "No geopoint for " + location_name;
        }
    }-*/;

    public static native JavaScriptObject createMarkerOnProfileMap(String location_name, String lat, String lng) /*-{
        var map = @pgu.client.profile.ui.ProfileUtils::profileMap()();

        @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);
    }-*/;

    public static native JavaScriptObject createMarkerOnPublicMap(String location_name, String lat, String lng) /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);
    }-*/;

    public static native JavaScriptObject createUnknownMarkerOnPublicMap() /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        return @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,'Unknown location','0','0');
    }-*/;

    private static native JavaScriptObject createMarkerWithGeopoint(JavaScriptObject map, String location_name, String lat, String lng) /*-{

        var
            google = @pgu.client.app.utils.GoogleUtils::google()()
        ;

        var latLng = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));

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
