package pgu.client.profile.ui;


public class PublicProfileUtils {

    @Deprecated
    public static native String getPublicProfile() /*-{

        // update $wnd.pgu_geo.public_prefs
//        @pgu.client.profile.ui.ProfileViewImpl::updateCachePublicPreferences()();

        var base_public_profile = $wnd.pgu_geo.base_public_profile;
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

}
