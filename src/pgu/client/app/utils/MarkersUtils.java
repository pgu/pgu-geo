package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;


public class MarkersUtils {

    static {
        initMovieMarkers();
    }

    private static native void initMovieMarkers() /*-{
        $wnd.pgu_geo.movie_markers = [];
    }-*/;

    private static native JavaScriptObject movieMarkers() /*-{
        return $wnd.pgu_geo.movie_markers;
    }-*/;

    // https://developers.google.com/maps/documentation/javascript/overlays#RemovingOverlays
    //Removes the overlays from the map, but keeps them in the array
    public static native void clearMarkers() /*-{
        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        for (i in markers) {
          markers[i].setMap(null);
        }
    }-*/;

    // Shows any overlays currently in the array
    public static native void showMarkers() /*-{
        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        for (i in markers) {
            markers[i].setMap(map);
        }
    }-*/;

    // Deletes all markers in the array by removing references to them
    public static native void deleteMarkers() /*-{
        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
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

            var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
            var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);

            markers.push(marker);
            return marker;

        } else {
            throw "No geopoint for " + location_name;
        }
    }-*/;

    public static native JavaScriptObject createMarkerOnProfileMap(String location_name, String lat, String lng) /*-{
        var map = @pgu.client.profile.ui.ProfileUtils::profileMap()();

        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);

        markers.push(marker);
        return marker;

    }-*/;

    public static native JavaScriptObject createMarkerOnPublicMap(String location_name, String lat, String lng) /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,location_name,lat,lng);

        markers.push(marker);
        return marker;

    }-*/;

    public static native JavaScriptObject createUnknownMarkerOnPublicMap() /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        var markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,'Unknown location','0','0');

        markers.push(marker);
        return marker;
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

        return marker;
    }-*/;


}
