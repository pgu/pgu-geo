package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileSummaryUtils {

    public static native void setProfileId(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var profile_id = profile.id || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileId(Ljava/lang/String;)(profile_id);

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.id = profile_id;

    }-*/;

    public static native void setProfilePublicUrl(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var public_url = profile.publicProfileUrl || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfilePublicUrl(Ljava/lang/String;)(public_url);

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.publicProfileUrl = public_url;
    }-*/;

    public static native void setProfileName(ProfileViewImpl view, JavaScriptObject profile) /*-{
        var
            first_name = profile.firstName || '' //
          , last_name = profile.lastName || '' //
        ;

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileName(Ljava/lang/String;Ljava/lang/String;)(first_name, last_name);

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.firstName = first_name;
        base_public_profile.lastName = last_name;

    }-*/;

    public static native void setProfileHeadline(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var headline = profile.headline || '';

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileHeadline(Ljava/lang/String;)(headline);

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.headline = headline;

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

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.location = location_name;

    }-*/;

    public static native void setProfileSummary(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var summary = profile.summary || '';

        var html_summary = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(summary);

        view.@pgu.client.profile.ui.ProfileViewImpl::setProfileSummary(Ljava/lang/String;)(html_summary);

        var base_public_profile = @pgu.client.profile.ui.PublicProfileUtils::basePublicProfile()();
        base_public_profile.summary = html_summary;

    }-*/;

}
