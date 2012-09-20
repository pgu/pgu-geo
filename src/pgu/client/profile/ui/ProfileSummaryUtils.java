package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileSummaryUtils {

    public static native void setProfileId(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var profile_id = profile.id || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileId(Ljava/lang/String;)(profile_id);
    }-*/;

    public static native void setProfilePublicUrl(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var public_url = profile.publicProfileUrl || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfilePublicUrl(Ljava/lang/String;)(public_url);
    }-*/;

    public static native void setProfileName(ProfileViewImpl view, JavaScriptObject profile) /*-{
        var
            first_name = profile.firstName || '' //
          , last_name = profile.lastName || '' //
        ;

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileName(Ljava/lang/String;Ljava/lang/String;)(first_name, last_name);
    }-*/;

    public static native void setProfileHeadline(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var headline = profile.headline || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileHeadline(Ljava/lang/String;)(headline);
    }-*/;

    public static native void setProfileSpecialties(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var specialties = profile.specialties || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileSpecialties(Ljava/lang/String;)(specialties);
    }-*/;

    public static native void setProfileLocation(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var
          profile_location = profile.location || {}
        , location_name = profile_location.name;

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileLocation(Ljava/lang/String;)(location_name);
    }-*/;

    public static native void setProfileSummary(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var summary = profile.summary || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileSummary(Ljava/lang/String;)(summary);
    }-*/;

    public static native void setProfileLanguages(ProfileViewImpl view, JavaScriptObject profile) /*-{

        view.@pgu.client.profile.ui.ProfileViewImpl::clearProfileLanguages()();

        var languages = profile.languages || {};
        var language_values = languages.values || [];

        for ( var i in language_values) {

            var //
            language_value = language_values[i] //
            //
            , language = language_value.language || {} //
            , language_name = language.name || '' //
            //
            , language_proficiency = language_value.proficiency || {} //
            , language_level = language_proficiency.level || '' //
            ;

            view.@pgu.client.profile.ui.ProfileViewImpl::addProfileLanguage(Ljava/lang/String;Ljava/lang/String;)(language_name, language_level);
        }

        view.@pgu.client.profile.ui.ProfileViewImpl::showProfileLanguages()();
    }-*/;

}
