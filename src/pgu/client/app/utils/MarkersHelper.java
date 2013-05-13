package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class MarkersHelper {

    static {
        initJS();
    }

    private static native void initJS() /*-{
        $wnd.pgu_geo.icon_types = { white: ['fff', 'e6e6e6'], // white
                grey: ['444', '222'], // grey
                blue_info: ['5bc0de', '2f96b4'], // blue info
                green: ['62c462', '51a351'], // green
                yellow: ['fbb450', 'f89406'], // yellow
                red: ['ee5f5b', 'bd362f'], // red
                blue: ['0088cc', '0044cc'] // blue
        };
    }-*/;

    private final LocationsHelper locations = new LocationsHelper();
    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    private boolean isLocationInReferential(final String location_name) {
        return locations.isLocationInReferential(location_name);
    }

    private JavaScriptObject getGeopoint(final String location_name) {
        return locations.getGeopoint(location_name);
    }

    public JavaScriptObject createMarker(final JavaScriptObject map, final String location_name) {
        return createMarker(map, location_name, null /* no type */ );
    }

    public native JavaScriptObject createMarker(JavaScriptObject map, String location_name, String type) /*-{

        var geopoint_is_available = this.@pgu.client.app.utils.MarkersHelper::isLocationInReferential(Ljava/lang/String;)
                                         (location_name);

        if (geopoint_is_available) {

            var
                geopoint = this.@pgu.client.app.utils.MarkersHelper::getGeopoint(Ljava/lang/String;)
                                (location_name)
              , lat = geopoint.lat
              , lng = geopoint.lng
            ;

            return this.@pgu.client.app.utils.MarkersHelper::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                        (map, location_name, lat, lng, type);

        } else {
            throw "No geopoint for " + location_name;
        }
    }-*/;

    public JavaScriptObject createMarkerWithGeopoint(final JavaScriptObject map, final String location_name, final String lat, final String lng) {
        return createMarkerWithGeopoint(map, location_name, lat, lng, null /* no type */);
    }

    public native JavaScriptObject createMarkerWithGeopoint(JavaScriptObject map, String location_name, String lat, String lng, String type) /*-{

        var
            google = this.@pgu.client.app.utils.MarkersHelper::google()
                          ()
        ;

        var latLng = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));

        var icon = {
                path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
                fillOpacity: 0.8,
                strokeOpacity: 0.8,
                strokeWeight: 2,
                scale: 6
        };

        var complete_icon = null;

        if (type) {
            complete_icon = JSON.parse(JSON.stringify(icon));
            var colors;

            if (type === 'current') {
                colors = $wnd.pgu_geo.icon_types.yellow;

            } else if (type === 'experience') {
                colors = $wnd.pgu_geo.icon_types.blue;

            } else if (type === 'education') {
                colors = $wnd.pgu_geo.icon_types.green;

            } else {
                colors = $wnd.pgu_geo.icon_types.red;
            }

            complete_icon.fillColor = '#' + colors[0];
            complete_icon.strokeColor = '#' + colors[1];
        }

        var marker_options = {
            map : map,
            position : latLng,
            animation : google.maps.Animation.DROP,
            title : location_name
        };

        if (complete_icon) {
            marker_options.icon = complete_icon;
        }

        var marker = new google.maps.Marker(marker_options);
        return marker;
    }-*/;

}
