package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class MapHelper {

    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    public native JavaScriptObject buildMap(String containerId) /*-{

        var div = $wnd.document.getElementById(containerId);
        var google = this.@pgu.client.app.utils.MapHelper::google()
                          ();

        google.maps.visualRefresh = true;
        var mapOptions = {
              zoom: 2,
              center: new google.maps.LatLng(0, 0),
              mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        ;

        return new google.maps.Map(div, mapOptions);
    }-*/;

}
