package pgu.client.profile.ui;

import pgu.client.app.utils.JsonHelper;
import pgu.client.app.utils.ProfileItemsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewToPublic {

    private final ProfileItemsHelper profileItems = new ProfileItemsHelper();
    private final JsonHelper json = new JsonHelper();

    public native String getJsonPublicProfile(
            String specialtiesHtml
            , String locationName
            , String fmtSummary
            , String languagesHtml
            ) /*-{

//        $wnd.pgu_geo.base_public_profile = public_p;

        var p = $wnd.pgu_geo.profile;

        var public_p = {};
//        public_p.id = p.id; do not expose the profile id
        public_p.rawPublicProfileUrl = p.publicProfileUrl;
        public_p.shortPublicProfileUrl = p.publicProfileUrl.substring('http://www.linkedin.com/'.length);
        public_p.firstName = p.firstName;
        public_p.lastName = p.lastName;
        public_p.headline = p.headline;
        public_p.specialties = specialtiesHtml;
        public_p.location = locationName;
        public_p.summary = fmtSummary;
        public_p.languages = languagesHtml;
        public_p.educations = [];
        public_p.positions = [];

        var item_configs = $wnd.pgu_geo.item_configs;
        for (var i = 0, len = item_configs.length; i < len; i++) {

            var
                item_config = item_configs[i]
              , type = item_config.type
            ;

            if (this.@pgu.client.profile.ui.ProfileViewToPublic::isEdu(Ljava/lang/String;)
                     (type)) {

                public_p.educations.push(item_config);

            } else if (this.@pgu.client.profile.ui.ProfileViewToPublic::isXp(Ljava/lang/String;)
                            (type)) {

                public_p.positions.push(item_config);

            } else {
                throw {
                    name: 'Unknown type'
                  , msg: type
                }
            }
        }

        this.@pgu.client.profile.ui.ProfileViewToPublic::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)
             (public_p.educations);

        this.@pgu.client.profile.ui.ProfileViewToPublic::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)
             (public_p.positions);

        // remember: item_configs do not contain locations

        return this.@pgu.client.profile.ui.ProfileViewToPublic::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                    (public_p);

    }-*/;

    private void sortProfileItemsByDateFromOldToNew(final JavaScriptObject profile_items) {
        profileItems.sortProfileItemsByDateFromOldToNew(profile_items);
    }

    private boolean isEdu(final String type) {
        return profileItems.isEdu(type);
    }

    private boolean isXp(final String type) {
        return profileItems.isXp(type);
    }

    private String stringify(final JavaScriptObject jso) {
        return json.stringify(jso);
    }

    public native void showPublicPreferences(final ProfileViewImpl view, final String preferences) /*-{

        // {"wishes":true,"positions":true,"educations":false,"contacts":true}, see PublicProfileItemType
        $wnd.pgu_geo.public_prefs = JSON.parse(preferences);

        var public_prefs = $wnd.pgu_geo.public_prefs;

        for (var key in public_prefs) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (public_prefs.hasOwnProperty(key)) {
                var is_public = public_prefs[key];

                view.@pgu.client.profile.ui.ProfileViewImpl::setPublicHeader(Ljava/lang/String;Z)
                     (key,is_public);
            }
        }

    }-*/;

    public native String getJsonPublicPreferences() /*-{
        return this.@pgu.client.profile.ui.ProfileViewToPublic::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                    ($wnd.pgu_geo.public_prefs);
    }-*/;

    public native void updatePublicPreference(String public_preference, boolean is_public) /*-{
        $wnd.pgu_geo.public_prefs[public_preference] = is_public;
    }-*/;

    public native String getPublicProfileUrl() /*-{
        return $wnd.pgu_geo.profile.publicProfileUrl.substring('http://www.linkedin.com/'.length);
    }-*/;

}
