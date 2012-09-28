package pgu.client.pub.ui;

import pgu.client.app.utils.ProfileItemsUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

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

    public static native void setProfileSummary(PublicViewImpl view, JavaScriptObject profile) /*-{

        var summary = profile.summary || '';

        view.@pgu.client.pub.ui.PublicViewImpl::setProfileSummary(Ljava/lang/String;)(summary);
    }-*/;

    public static native void setProfilePublicUrl(PublicViewImpl view, JavaScriptObject profile) /*-{

        var public_url = profile.publicProfileUrl || '';

        view.@pgu.client.pub.ui.PublicViewImpl::setProfilePublicUrl(Ljava/lang/String;)(public_url);
    }-*/;

    public static void setProfileItems(final PublicViewImpl view, final JavaScriptObject profile) {

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                ProfileItemsUtils.setProfileItems(profile);
                view.addProfileItemsToPlayToolbar();
            }
        });

    }

}
