package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicProfileUtils {

    public static native void initBasePublicProfile() /*-{

        var profile = @pgu.client.profile.ui.ProfileUtils::profile()();

        $wnd.pgu_geo.base_public_profile = {};
        $wnd.pgu_geo.base_public_profile['id'] = profile.id;
    }-*/;

    public static native JavaScriptObject basePublicProfile() /*-{
        return $wnd.pgu_geo.base_public_profile;
    }-*/;

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

    public static native void setSpecialties(String html_specialties) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.specialties = html_specialties;

    }-*/;

    public static native void setLanguages(String html_languages) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.languages = html_languages;

    }-*/;

    public static native void addEducation(JavaScriptObject profile_item) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        if (!base_public_profile.educations) {
            base_public_profile.educations = [];
        }

        base_public_profile.educations.push(profile_item);
    }-*/;

    public static native void addExperience(JavaScriptObject profile_item) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        if (!base_public_profile.positions) {
            base_public_profile.positions = [];
        }

        base_public_profile.positions.push(profile_item);
    }-*/;

    public static native void sortProfileItems(String type) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();

        if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)(type)) {
            if (base_public_profile.educations) {
                base_public_profile.educations.sort(function(a,b) { return -(a.startD.getTime() - b.startD.getTime()) } );
            }

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {
            if (base_public_profile.positions) {
                base_public_profile.positions.sort(function(a,b) { return -(a.startD.getTime() - b.startD.getTime()) } );
            }

        }

    }-*/;

}
