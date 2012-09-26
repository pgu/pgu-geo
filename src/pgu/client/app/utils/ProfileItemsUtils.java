package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsUtils {

    public static native void setProfileItems(final JavaScriptObject profile) /*-{

        $wnd.pgu_geo.profile_items = {};

        var
            profile_items = $wnd.pgu_geo.profile_items
        ;

        if (profile.positions) {
            profile_items['experience'] = [];
        }

        if (profile.educations) {
            profile_items['education'] = [];
        }

        profile_items['all'] = [];

        for (var key in profile_items) {

            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if ('all' === key) {
                continue;
            }

            if (profile_items.hasOwnProperty(key)) {
                profile_items['all'] = profile_items['all'].concat(profile_items[key]);
            }
        }

        // education, experience
        // profile.education?
        // for each education in profile.education
        // transform(education) -> profile_item
        // compute item_config.dates
        // [].push(item_config)
        //
        // profile_items[educations] = []
        // profile_items[all] = object.educations.concat(object.exp);
        // sort all the arrays by dates

        // at the end: movie[educations, experiences, all]
        // default option: 'all'

        //
        // play: init what type to play: for each item of selected movie.ownProperty
        // play them.

    }-*/;

}
