package pgu.client.pub.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicUtils {

    public static native void initPublicProfileMap() /*-{

        var div = $wnd.document.getElementById("pgu_geo_public_profile_map");

        if (div === null) {
            $wnd.setTimeout(
                function() {
                    @pgu.client.pub.ui.PublicUtils::initPublicProfileMap()();
                }
                , 1000
            );
            return;
        }

        $wnd.console.log('initPublicMap');

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

    public static native JavaScriptObject publicProfileMap() /*-{
        return $wnd.pgu_geo.public_profile_map;
    }-*/;

    public static native void updateMapVisu(String mapPreferences) /*-{

        var
            public_map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()()
          , google = @pgu.client.app.utils.GoogleUtils::google()()
          , prefs = JSON.parse(mapPreferences)
          , center = new google.maps.LatLng(parseFloat(prefs.center_lat), parseFloat(prefs.center_lng))
        ;

        public_map.setZoom(prefs.zoom);
        public_map.setCenter(center);

    }-*/;
}
