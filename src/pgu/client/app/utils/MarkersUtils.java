package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;


public class MarkersUtils {

    private static native JavaScriptObject searchMarkers() /*-{

        if (!$wnd.pgu_geo.search_markers) {
            $wnd.pgu_geo.search_markers = [];
        }

        return $wnd.pgu_geo.search_markers;
    }-*/;

    private static native JavaScriptObject movieMarkers() /*-{

        if (!$wnd.pgu_geo.movie_markers) {
            $wnd.pgu_geo.movie_markers = [];
        }

        return $wnd.pgu_geo.movie_markers;
    }-*/;

    public static native void deleteSearchMarkers() /*-{
        var search_markers = @pgu.client.app.utils.MarkersUtils::searchMarkers()();

        @pgu.client.app.utils.MarkersUtils::deleteMarkers(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        search_markers);
    }-*/;

    public static native void deleteMovieMarkers() /*-{
        var movie_markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();

        @pgu.client.app.utils.MarkersUtils::deleteMarkers(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        movie_markers);
    }-*/;

    // Deletes all markers in the array by removing references to them
    private static native void deleteMarkers(JavaScriptObject markers) /*-{

        for (var i = 0; i < markers.length; i++) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;

    // TODO revoir ça...
    public static native JavaScriptObject createMarkerOnProfileMap(String location_name) /*-{
        var profile_map = @pgu.client.profile.ui.ProfileUtils::profileMap()();

        var marker = @pgu.client.app.utils.MarkersUtils::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
                     profile_map,location_name);

        var search_markers = @pgu.client.app.utils.MarkersUtils::searchMarkers()();
        search_markers.push(marker);

        return marker;
    }-*/;

    public static native JavaScriptObject createMarkerOnProfileMap(String location_name, String lat, String lng) /*-{
        var profile_map = @pgu.client.profile.ui.ProfileUtils::profileMap()();

        var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
                     profile_map,location_name,lat,lng);

        var search_markers = @pgu.client.app.utils.MarkersUtils::searchMarkers()();
        search_markers.push(marker);

        return marker;
    }-*/;

    public static native JavaScriptObject createMovieMarkerOnPublicMap(String location_name) /*-{
        var public_map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        var marker = @pgu.client.app.utils.MarkersUtils::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
                     public_map,location_name);

        var movie_markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        movie_markers.push(marker);

        return marker;
    }-*/;

    public static native JavaScriptObject createMovieUnknownMarkerOnPublicMap() /*-{
        var public_map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
                     public_map,'Unknown location','0','0');

        var movie_markers = @pgu.client.app.utils.MarkersUtils::movieMarkers()();
        movie_markers.push(marker);

        return marker;
    }-*/;

    public static native JavaScriptObject createMarker(JavaScriptObject map, String location_name) /*-{

        var geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
        if (geopoint_is_available) {

            var
                geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name)
              , lat = geopoint.lat
              , lng = geopoint.lng
            ;

            return @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
                   map,location_name,lat,lng);

        } else {
            throw "No geopoint for " + location_name;
        }
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
