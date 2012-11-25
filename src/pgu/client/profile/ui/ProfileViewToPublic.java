package pgu.client.profile.ui;

public class ProfileViewToPublic {

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

        return @pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
               (public_profile);

    }-*/;


}
