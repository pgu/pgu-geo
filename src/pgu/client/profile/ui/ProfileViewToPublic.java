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

//        $wnd.pgu_geo.base_public_profile = {};

//        var
//            p = $wnd.pgu_geo.profile
//          , first_name = p.firstName || ''
//          , last_name = p.lastName || ''
//          , headline = p.headline || ''
//          , current_location = p.location || {}
//          , current_location_name = current_location.name || ''
//          , specialties = p.specialties || ''
//          , summary = p.summary || ''
//          , languages = p.languages || {} //
//          , language_values = languages.values || [] //
//          , positions = p.positions || {}
//          , educations = p.educations || {}
//        ;

        var p = $wnd.pgu_geo.profile;

        var public_p = {};
        public_p.id = p.id;
        public_p.publicProfileUrl = p.publicProfileUrl;
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
               (public_profile);

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

}
