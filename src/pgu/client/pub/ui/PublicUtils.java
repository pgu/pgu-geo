package pgu.client.pub.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicUtils {

    public static native void initProfileMap() /*-{

        var div = $wnd.document.getElementById("pgu_geo_public_profile_map");

        if (div === null) {
            $wnd.setTimeout(
                function() {
                    @pgu.client.pub.ui.PublicUtils::initProfileMap()();
                }
                , 1000
            );
            return;
        }

        var
            google = $wnd.google
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
}
