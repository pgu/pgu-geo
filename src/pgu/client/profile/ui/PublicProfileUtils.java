package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicProfileUtils {

    private static native JavaScriptObject basePublicProfile() /*-{
        return $wnd.pgu_geo.base_public_profile;
    }-*/;

    public static native void updatePublicProfileItem(String public_profile_item, boolean is_public) /*-{
        $wnd.pgu_geo.public_prefs[public_profile_item] = is_public;
    }-*/;

    @Deprecated
    public static native String getPublicProfile() /*-{

        // update $wnd.pgu_geo.public_prefs
        @pgu.client.profile.ui.ProfileViewImpl::updateCachePublicPreferences()();

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        var copy_profile = JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        base_public_profile));

        // modify copy according to public preferences
        var public_prefs = $wnd.pgu_geo.public_prefs;

        for ( var key in public_prefs) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (public_prefs.hasOwnProperty(key)) {
                var is_public = public_prefs[key];

                if (!is_public) {

                    if (key === 'experiences') {
                        delete copy_profile['positions'];

                    } else if (key === 'educations') {
                        delete copy_profile['educations'];

                    }
// TODO summary...
                }
            }
        }


        return @pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
  		            copy_profile);
    }-*/;

    public static native String json_publicPreferences() /*-{

        // update $wnd.pgu_geo.public_prefs
        @pgu.client.profile.ui.ProfileViewImpl::updateCachePublicPreferences()();

        return @pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.public_prefs);
    }-*/;

}
