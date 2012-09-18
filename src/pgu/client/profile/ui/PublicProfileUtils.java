package pgu.client.profile.ui;

public class PublicProfileUtils {

    public static native void showPublicPreferencesAndUpdatePublicProfile(ProfileViewImpl view, final String preferences) /*-{
		var public_prefs = JSON.parse(preferences);
		// {"wishes":true,"positions":true,"educations":false,"contacts":true}, see PublicProfileItem

		for ( var key in public_prefs) {
			if ('__gwt_ObjectId' === key) {
				continue;
			}
			if (public_prefs.hasOwnProperty(key)) {
				var is_public = public_prefs[key];

                view.@pgu.client.profile.ui.ProfileViewImpl::updatePublicHeader(Ljava/lang/Boolean;Ljava/lang/String;)(is_public,key);
			}
		}

    }-*/;

    public static native String getPublicProfile() /*-{
		// TODO Auto-generated method stub
		return '';
    }-*/;

}
