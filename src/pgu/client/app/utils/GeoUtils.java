package pgu.client.app.utils;

import pgu.client.profile.ui.ProfileUtils;
import pgu.client.pub.ui.PublicUtils;

public class GeoUtils {

    public static native void exportMethod() /*-{
        $wnd.pgu_geo.init_geo = $entry(@pgu.client.app.utils.GeoUtils::initGeo());
    }-*/;

    public static void initGeo() {
        GeocoderUtils.initGeocoder();
        ProfileUtils.initProfileMap();
        PublicUtils.initProfileMap(); // TODO PGU separer ça..
    }

}
