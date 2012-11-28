package pgu.client.profile.ui;

import pgu.client.app.utils.JsonHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewMap {

    private final JsonHelper json = new JsonHelper();

    public native void initProfileMap() /*-{

        var div = $wnd.document.getElementById('pgu_geo_profile_map');

        $wnd.console.log('initProfileMap');

        var
            google = @pgu.client.app.utils.GoogleUtils::google()()
          , mapOptions = {
              zoom: 2,
              center: new google.maps.LatLng(0, 0),
              mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        ;

        $wnd.pgu_geo.profile_map = new google.maps.Map( //
            div //
            , mapOptions);

    }-*/;

    public native JavaScriptObject profileMap() /*-{
        return $wnd.pgu_geo.profile_map;
    }-*/;

    private String stringify(final JavaScriptObject jso) {
        return json.stringify(jso);
    }

    public native String getCurrentMapPreferences() /*-{
        var profile_map = this.@pgu.client.profile.ui.ProfileViewMap::profileMap()
                               ();

        var map_preferences = {};
        map_preferences.zoom = profile_map.zoom;
        map_preferences.center_lat = '' + profile_map.center.lat();
        map_preferences.center_lng = '' + profile_map.center.lng();

        return this.@pgu.client.profile.ui.ProfileViewMap::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                    (map_preferences);
    }-*/;


}
