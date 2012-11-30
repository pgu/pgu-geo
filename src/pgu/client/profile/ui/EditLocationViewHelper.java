package pgu.client.profile.ui;

import pgu.client.app.utils.LocationsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class EditLocationViewHelper {

    private final LocationsHelper locations = new LocationsHelper();

    public native boolean isLocationFromLinkedin(final String item_config_id, final String location_name) /*-{

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

    public native void showLatitudeAndLongitude(final EditLocationViewImpl view, final String location_name) /*-{

        var lat,lng;

        var geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
        if (geopoint_is_available) {

            var geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name);
            lat = geopoint.lat;
            lng = geopoint.lng;

        } else {
            lat = lng = '';

        }
        view.@pgu.client.profile.ui.EditLocationViewImpl::showLatitudeAndLongitude(Ljava/lang/String;Ljava/lang/String;)(lat,lng);

    }-*/;

    public native void removeLocationFromCopyCaches(String item_config_id, String location_name) /*-{
        var
            cache_items = $wnd.pgu_geo.copy_cache_items
          , cache_referential = $wnd.pgu_geo.copy_cache_referential
        ;

        this.@pgu.client.profile.ui.EditLocationViewHelper::removeLocationFromItem(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;)
             (cache_items, cache_referential, item_config_id, location_name);

    }-*/;

    private void removeLocationFromItem(final JavaScriptObject cache_items, final JavaScriptObject cache_referential, final String item_config_id, final String location_name) {
        locations.removeLocationFromItem(cache_items, cache_referential, item_config_id, location_name);
    }


}
