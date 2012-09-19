package pgu.client.app.utils;

import pgu.client.profile.ui.ProfileUtils;

public class GeoUtils {

    static {
        exportMethod();
    }

    private static native void exportMethod() /*-{
        $wnd.pgu_geo.init_geo = $entry(@pgu.client.app.utils.GeoUtils::initGeo());
    }-*/;

    public static void initGeo() {
        GeocoderUtils.initGeocoder();
        ProfileUtils.initProfileMap();
    }

}
