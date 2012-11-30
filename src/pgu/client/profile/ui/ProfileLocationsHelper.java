package pgu.client.profile.ui;

import pgu.client.app.utils.JsonHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileLocationsHelper {

    private final JsonHelper json = new JsonHelper();

    public native void copyLocationCaches() /*-{
        $wnd.pgu_geo.copy_cache_items = JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.cache_items));


        $wnd.pgu_geo.copy_cache_referential = JSON.parse(@pgu.client.app.utils.JsonUtils::json_stringify(Lcom/google/gwt/core/client/JavaScriptObject;)( //
        $wnd.pgu_geo.cache_referential));

    }-*/;

    public String json_copyCacheItems() {
        return json.stringify(copyCacheItems());
    }

    private native JavaScriptObject copyCacheItems() /*-{
        return $wnd.pgu_geo.copy_cache_items;
    }-*/;

    public String json_copyCacheReferential() {
        return json.stringify(copyCacheReferential());
    }

    private native JavaScriptObject copyCacheReferential() /*-{
        return $wnd.pgu_geo.copy_cache_referential;
    }-*/;

    public native void replaceCachesByCopies() /*-{
        $wnd.pgu_geo.cache_items = $wnd.pgu_geo.copy_cache_items;
        $wnd.pgu_geo.cache_referential = $wnd.pgu_geo.copy_cache_referential;
    }-*/;

    public native void deleteCopies() /*-{
        delete $wnd.pgu_geo.copy_cache_items;
        delete $wnd.pgu_geo.copy_cache_referential;
    }-*/;

    public native void removeLocationFromItem( //
            JavaScriptObject cache_items //
            , JavaScriptObject cache_referential //
            , String item_config_id //
            , String location_name) /*-{


        var location_names = cache_items[item_config_id];
        var updated_locations = [];

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var name = location_names[i];

            if (location_name !== name) {
                updated_locations.push(name);
            }
        }

        cache_items[item_config_id] = updated_locations;

        var has_at_least_once = false;

        mainloop: for (var key in cache_items) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_items.hasOwnProperty(key)) {

                var locations = cache_items[key];
                for ( var j = 0, len = locations.length; j < len; j++) {

                    var location = locations[j];
                    if (location === location_name) {

                        has_at_least_once = true;
                        break mainloop;
                    }
                }
            }
        }

        if (!has_at_least_once) {
            delete cache_referential[location_name];
        }

    }-*/;

}
