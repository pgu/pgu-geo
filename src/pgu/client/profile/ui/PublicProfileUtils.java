package pgu.client.profile.ui;

public class PublicProfileUtils {

    public static native void showPublicPreferences(ProfileViewImpl view, final String preferences) /*-{

        if ("" === preferences) {

    		$wnd.pgu_geo.public_prefs = {};

            view.@pgu.client.profile.ui.ProfileViewImpl::updatePublicHeader(ZLjava/lang/String;)(true,"experiences");
            view.@pgu.client.profile.ui.ProfileViewImpl::updatePublicHeader(ZLjava/lang/String;)(true,"educations");

    		return;
        }

		// {"wishes":true,"positions":true,"educations":false,"contacts":true}, see PublicProfileItem
		$wnd.pgu_geo.public_prefs = JSON.parse(preferences);

		var public_prefs = $wnd.pgu_geo.public_prefs;

		for ( var key in public_prefs) {
			if ('__gwt_ObjectId' === key) {
				continue;
			}
			if (public_prefs.hasOwnProperty(key)) {
				var is_public = public_prefs[key];

                view.@pgu.client.profile.ui.ProfileViewImpl::updatePublicHeader(ZLjava/lang/String;)(is_public,key);
			}
		}

    }-*/;

    public static native void updatePublicProfileItem(String public_profile_item, boolean is_public) /*-{
        $wnd.pgu_geo.public_prefs[public_profile_item] = is_public;
    }-*/;

    public static native String getPublicProfile() /*-{

        // update $wnd.pgu_geo.public_prefs
        @pgu.client.profile.ui.ProfileViewImpl::updateCachePublicPreferences()();


        var copy_profile = @pgu.client.profile.ui.ProfileUtils::copyProfile()();

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
