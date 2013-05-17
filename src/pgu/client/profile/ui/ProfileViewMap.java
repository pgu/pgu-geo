package pgu.client.profile.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.JsonHelper;
import pgu.client.app.utils.MapHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewMap {

    private final JsonHelper json = new JsonHelper();
    private final GoogleHelper google = new GoogleHelper();
    private final MapHelper map = new MapHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    private JavaScriptObject buildMap(final String containerId) {
        return map.buildMap(containerId);
    }

    public native void initProfileMap() /*-{
        $wnd.console.log('initProfileMap');
        $wnd.pgu_geo.profile_map = this.@pgu.client.profile.ui.ProfileViewMap::buildMap(Ljava/lang/String;)
                                        ('pgu_geo_profile_map');
    }-*/;

    public native JavaScriptObject profileMap() /*-{
        return $wnd.pgu_geo.profile_map;
    }-*/;

    private String stringify(final JavaScriptObject jso) {
        return json.stringify(jso);
    }

    public native String getCurrentMapPreferences() /*-{
        var profile_map = $wnd.pgu_geo.profile_map;

        var map_preferences = {};
        map_preferences.zoom = profile_map.zoom;
        map_preferences.center_lat = '' + profile_map.center.lat();
        map_preferences.center_lng = '' + profile_map.center.lng();

        return this.@pgu.client.profile.ui.ProfileViewMap::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                    (map_preferences);
    }-*/;

    public native void setPreferences(final String map_preferences) /*-{

        if (!map_preferences) {
            $wnd.console.log('no map_preferences');
            return;
        }

        var google = this.@pgu.client.profile.ui.ProfileViewMap::google()
                          ();

        var
            profile_map = $wnd.pgu_geo.profile_map
          , prefs = JSON.parse(map_preferences)
          , center = new google.maps.LatLng(parseFloat(prefs.center_lat), parseFloat(prefs.center_lng))
        ;

        profile_map.setZoom(prefs.zoom);
        profile_map.setCenter(center);

    }-*/;


}
