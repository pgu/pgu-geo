package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleUtils {

    public static native void initGoogle() /*-{
        $wnd.console.log('init google');
        $wnd.console.log($wnd.google);

        $wnd.pgu_geo.google = $wnd.google;

        $wnd.console.log($wnd.pgu_geo.google);
    }-*/;

    public static native JavaScriptObject google() /*-{

        $wnd.console.log('google()');
        $wnd.console.log($wnd.pgu_geo.google);

        return $wnd.pgu_geo.google;
    }-*/;

}
