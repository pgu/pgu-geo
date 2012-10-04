package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleUtils {

    public static native void initGoogle() /*-{
        $wnd.pgu_geo.google = $wnd.google;
    }-*/;

    public static native JavaScriptObject google() /*-{
        return $wnd.pgu_geo.google;
    }-*/;

}
