package pgu.client.pub.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class PublicViewUtils {

    public static native void setProfileName(PublicViewImpl view, JavaScriptObject profile) /*-{

        var
            first_name = profile.firstName || '' //
          , last_name = profile.lastName || '' //
        ;

        view.@pgu.client.pub.ui.PublicViewImpl::setProfileName(Ljava/lang/String;Ljava/lang/String;)(first_name,last_name);
    }-*/;

    public static native void setProfileHeadline(PublicViewImpl view, JavaScriptObject profile) /*-{

        var headline = profile.headline || '';

        view.@pgu.client.pub.ui.PublicViewImpl::setProfileHeadline(Ljava/lang/String;)(headline);
    }-*/;

    public static native void setProfileLocation(PublicViewImpl view, JavaScriptObject profile) /*-{

        var
          profile_location = profile.location || {}
        , location_name = profile_location.name;

        view.@pgu.client.pub.ui.PublicViewImpl::setProfileLocation(Ljava/lang/String;)(location_name);
    }-*/;

    public static native void setProfileSpecialties(PublicViewImpl view, JavaScriptObject profile) /*-{

        var specialties = profile.specialties || '';

        view.@pgu.client.pub.ui.PublicViewImpl::setProfileSpecialties(Ljava/lang/String;)(specialties);
    }-*/;

}
