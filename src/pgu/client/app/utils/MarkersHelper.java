package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class MarkersHelper {

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

    public native JavaScriptObject createMarker(JavaScriptObject map, String location_name) /*-{

        var geopoint_is_available = this.@pgu.client.app.utils.MarkersHelper::isLocationInReferential(Ljava/lang/String;)
                                         (location_name);

        if (geopoint_is_available) {

            var
                geopoint = this.@pgu.client.app.utils.MarkersHelper::getGeopoint(Ljava/lang/String;)
                                (location_name)
              , lat = geopoint.lat
              , lng = geopoint.lng
            ;

            return this.@pgu.client.app.utils.MarkersHelper::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                        (map, location_name, lat, lng);

        } else {
            throw "No geopoint for " + location_name;
        }
    }-*/;

    public native JavaScriptObject createMarkerWithGeopoint(JavaScriptObject map, String location_name, String lat, String lng) /*-{

        var
            google = this.@pgu.client.app.utils.MarkersHelper::google()
                          ()
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
