package pgu.client.app.utils;

import pgu.client.profile.ui.ProfileUtils;
import pgu.client.pub.ui.PublicUtils;

public class GeoUtils {

    public void exportMethods(final boolean isPublic) {

        if (isPublic) {
            exportPublicMethods();

        } else {
            exportMethods();
        }
    }

    private native void exportMethods() /*-{
        $wnd.pgu_geo.init_geo = $entry(@pgu.client.app.utils.GeoUtils::initGeo());
    }-*/;

    public static void initGeo() {
        initGeoInternal();
        ProfileUtils.initProfileMap();
    }

    private static void initGeoInternal() {
        GoogleUtils.initGoogle();
        GeocoderUtils.initGeocoder();
    }

    private native void exportPublicMethods() /*-{
        $wnd.pgu_geo.init_geo = $entry(@pgu.client.app.utils.GeoUtils::initPublicGeo());
    }-*/;

    public static void initPublicGeo() {
        initGeoInternal();
        PublicUtils.initProfileMap();
    }


}
