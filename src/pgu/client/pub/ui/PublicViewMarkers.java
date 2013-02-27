package pgu.client.pub.ui;

import pgu.client.app.utils.GoogleHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewMarkers {

    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
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

}
