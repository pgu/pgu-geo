package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicProfileUtils {

    public static native void initBasePublicProfile() /*-{
        $wnd.pgu_geo.base_public_profile = {};
    }-*/;

    private static native JavaScriptObject basePublicProfile() /*-{
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

    //    public static native void setProfileId(String profile_id) /*-{
    //
    //        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
    //        base_public_profile.id = profile_id;
    //
    //    }-*/;

    //    public static native void setProfilePublicUrl(String public_url) /*-{
    //
    //        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
    //        base_public_profile.publicProfileUrl = public_url;
    //
    //    }-*/;

    //    public static native void setProfileName(String first_name, String last_name) /*-{
    //
    //        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
    //        base_public_profile.firstName = first_name;
    //        base_public_profile.lastName = last_name;
    //
    //    }-*/;

    //    public static native void setProfileHeadline(String headline) /*-{
    //
    //        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
    //        base_public_profile.headline = headline;
    //
    //    }-*/;

    public static native void setSpecialties(String html_specialties) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.specialties = html_specialties;

    }-*/;

    public static native void setLanguages(String html_languages) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.languages = html_languages;

    }-*/;

    //    public static native void setProfileLocation(String location_name) /*-{
    //
    //        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
    //        base_public_profile.location = location_name;
    //
    //    }-*/;

    public static native void setProfileSummary(String html_summary) /*-{

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.summary = html_summary;

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
                @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)(base_public_profile.educations);
            }

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {
            if (base_public_profile.positions) {
                @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)(base_public_profile.positions);
            }

        }

    }-*/;

}
