package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileUtils {

    public static native void cacheProfile(JavaScriptObject profile) /*-{
        $wnd.pgu_geo.profile = profile;
    }-*/;

    public static native boolean isLocationFromLinkedin(final String item_config_id, final String location_name) /*-{

        if (item_config_id.indexOf('experience') === -1) {
            return false;
        }

        var
            profile = $wnd.pgu_geo.profile
          , poss = profile.positions || {}
          , positions = poss.values || []
          , position_id = item_config_id.split("_")[1]
        ;

        for (var i in positions) {
            var position = positions[i];
            if (position.id+'' === position_id) {

                var
                    location = position.location || {}
                  , loc_name = location.name || ''
                ;

                if (loc_name === location_name) {
                    return true;
                }
            }
        }

        return false;
    }-*/;

}
