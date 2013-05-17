package pgu.client.pub.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.MapHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewMap {

    private final GoogleHelper google = new GoogleHelper();
    private final MapHelper map = new MapHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    private JavaScriptObject buildMap(final String containerId) {
        return map.buildMap(containerId);
    }

    public native void initPublicProfileMap() /*-{
        $wnd.console.log('initPublicProfileMap');


        if ($wnd.pgu_geo.public_profile_map) {

            var google = this.@pgu.client.pub.ui.PublicViewMap::google()
                              ();

            var map = $wnd.pgu_geo.public_profile_map;
            map.setCenter(new google.maps.LatLng(0, 0));
            map.setZoom(2);

            return; // already initialized, just reset
        }

        $wnd.pgu_geo.public_profile_map = this.@pgu.client.pub.ui.PublicViewMap::buildMap(Ljava/lang/String;)
                                               ('pgu_geo_public_profile_map');
    }-*/;

    public native JavaScriptObject getPublicMap() /*-{
        return $wnd.pgu_geo.public_profile_map;
    }-*/;

    public native void setPreferences(String map_preferences) /*-{

        if (!map_preferences) {
            $wnd.console.log('no map_preferences');
            return;
        }

        var google = this.@pgu.client.pub.ui.PublicViewMap::google()
                          ();

        var
            public_map = $wnd.pgu_geo.public_profile_map
          , prefs = JSON.parse(map_preferences)
          , center = new google.maps.LatLng(parseFloat(prefs.center_lat), parseFloat(prefs.center_lng))
        ;

        public_map.setZoom(prefs.zoom);
        public_map.setCenter(center);

    }-*/;

}
