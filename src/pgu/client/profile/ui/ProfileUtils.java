package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileUtils {

    @Deprecated
    public static native JavaScriptObject profileMap() /*-{
        return $wnd.pgu_geo.profile_map;
    }-*/;

    @Deprecated
    public static native JavaScriptObject copyProfile() /*-{
        return JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.profile));
    }-*/;

}
