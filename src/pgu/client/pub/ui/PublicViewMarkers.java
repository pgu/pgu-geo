package pgu.client.pub.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.MarkersHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewMarkers {

    private final GoogleHelper google = new GoogleHelper();
    private final PublicViewMap map = new PublicViewMap();
    private final MarkersHelper markers = new MarkersHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    private JavaScriptObject getPublicMap() {
        return map.getPublicMap();
    }

    public native JavaScriptObject createMarkerWithGeopoint(JavaScriptObject map, String location_name, String lat, String lng) /*-{

        var google = this.@pgu.client.pub.ui.PublicViewMarkers::google()();

        var latLng = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));

        var marker = new google.maps.Marker({
            map : map,
            position : latLng,
            animation : google.maps.Animation.DROP,
            title : location_name
        });

        return marker;
    }-*/;

    public native void deleteMovieMarkers() /*-{
        var movie_markers = $wnd.pgu_geo.movie_markers;

        if (!movie_markers) {
            return;
        }

        this.@pgu.client.pub.ui.PublicViewMarkers::deleteMarkers(Lcom/google/gwt/core/client/JavaScriptObject;)
             (movie_markers);
    }-*/;

    private native void deleteMarkers(JavaScriptObject markers) /*-{

        for (var i = 0; i < markers.length; i++) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;

    public native JavaScriptObject createMovieMarkerOnPublicMap(String location_name) /*-{
        var public_map = this.@pgu.client.pub.ui.PublicViewMarkers::getPublicMap()
                              ();

        var marker = this.@pgu.client.pub.ui.PublicViewMarkers::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)
                          (public_map,location_name);

        var movie_markers = this.@pgu.client.pub.ui.PublicViewMarkers::movieMarkers()
                                 ();
        movie_markers.push(marker);

        return marker;
    }-*/;

    public native JavaScriptObject createMovieUnknownMarkerOnPublicMap() /*-{
        var public_map = this.@pgu.client.pub.ui.PublicViewMarkers::getPublicMap()
                              ();

        var marker = this.@pgu.client.pub.ui.PublicViewMarkers::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                          (public_map,'Unknown location','0','0');

        var movie_markers = this.@pgu.client.pub.ui.PublicViewMarkers::movieMarkers()
                                 ();
        movie_markers.push(marker);

        return marker;
    }-*/;

    private JavaScriptObject createMarker(final JavaScriptObject map, final String location_name) {
        return markers.createMarker(map, location_name);
    }

    private native JavaScriptObject movieMarkers() /*-{

        if (!$wnd.pgu_geo.movie_markers) {
            $wnd.pgu_geo.movie_markers = [];
        }

        return $wnd.pgu_geo.movie_markers;
    }-*/;

}
