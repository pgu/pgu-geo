package pgu.client.pub.ui;

import pgu.client.app.utils.GoogleHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewMap {

    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    public native void initPublicProfileMap() /*-{
        $wnd.console.log('initPublicProfileMap');

        if ($wnd.pgu_geo.public_profile_map) {
            return; // already initialized
        }

        var div = $wnd.document.getElementById("pgu_geo_public_profile_map");

        var
            google = @pgu.client.app.utils.GoogleUtils::google()()
          , mapOptions = {
              zoom: 2,
              center: new google.maps.LatLng(0, 0),
              mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        ;

        $wnd.pgu_geo.public_profile_map = new google.maps.Map( //
            div //
            , mapOptions);

    }-*/;

    // TODO PGU Jan 31, 2013 remove static
    public static native JavaScriptObject publicProfileMap() /*-{
        return $wnd.pgu_geo.public_profile_map;
    }-*/;

    public native void updateMapVisu(String mapPreferences) /*-{

        var google = this.@pgu.client.pub.ui.PublicViewMap::google()
                          ();

        var
            public_map = $wnd.pgu_geo.public_profile_map
          , prefs = JSON.parse(mapPreferences)
          , center = new google.maps.LatLng(parseFloat(prefs.center_lat), parseFloat(prefs.center_lng))
        ;

        public_map.setZoom(prefs.zoom);
        public_map.setCenter(center);

    }-*/;
}
