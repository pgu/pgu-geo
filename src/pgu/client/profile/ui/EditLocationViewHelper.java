package pgu.client.profile.ui;


import pgu.client.app.utils.LocationsHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class EditLocationViewHelper {

    private final ProfileLocationsHelper locations = new ProfileLocationsHelper();
    private final LocationsHelper commonHelper = new LocationsHelper();

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

    public boolean isLocationInReferential(final String location_name) {
        return commonHelper.isLocationInReferential(location_name);
    }

    public JavaScriptObject getGeopoint(final String location_name) {
        return commonHelper.getGeopoint(location_name);
    }

    public native void showLatitudeAndLongitude(final EditLocationViewImpl view, final String location_name) /*-{

        var lat,lng;

        var geopoint_is_available = this.@pgu.client.profile.ui.EditLocationViewHelper::isLocationInReferential(Ljava/lang/String;)
                                         (location_name);
        if (geopoint_is_available) {

            var geopoint = this.@pgu.client.profile.ui.EditLocationViewHelper::getGeopoint(Ljava/lang/String;)
                                (location_name);
            lat = geopoint.lat;
            lng = geopoint.lng;

        } else {
            lat = lng = '';

        }
        view.@pgu.client.profile.ui.EditLocationViewImpl::showLatitudeAndLongitude(Ljava/lang/String;Ljava/lang/String;)
             (lat,lng);

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

    public native JavaScriptObject getOtherLocationNames(String item_config_id) /*-{

        var
            cache_referential = $wnd.pgu_geo.cache_referential
          , cache_items = $wnd.pgu_geo.cache_items
          , item_locations = cache_items[item_config_id]
          , other_location_names = []
        ;

        for (var key in cache_referential) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_referential.hasOwnProperty(key)) {

                if ($wnd.$.inArray(key, item_locations) == -1) {
                    other_location_names.push(key);
                }
            }
        }

        return other_location_names;
    }-*/;

}
