package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfilePositionsUtils {

    public static native void updateProfilePositions(ProfileViewImpl view, JavaScriptObject profile) /*-{

        var
            pos = profile.positions || {}
          , positions = pos.values || []
        ;

        for (var i in positions) {
            var
                position = positions[i]
              , experience_id = position.id
              , location = position.location || {}
              , location_names = location.name
            ;

            if (location_names) {
                var locations = location_names.split(";");
                for (var j in locations) {
                    var
                        raw_location = locations[j]
                      , location_name = raw_location.trim()
                    ;

                    @pgu.client.app.utils.LocationsUtils::addExperienceLocationToCache(DLjava/lang/String;)(experience_id, location_name);
                }
            }
        }

    }-*/;

}
