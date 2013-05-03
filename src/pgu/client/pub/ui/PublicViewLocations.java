package pgu.client.pub.ui;


public class PublicViewLocations {

    public native void initCaches( //
            final String items2locations //
            , final String referential //
            ) /*-{

        $wnd.console.log(' ---- init public caches ----');

        // TODO review if we should use public_cache_items instead of cache_items
        if (!items2locations) {
            $wnd.pgu_geo.cache_items = {}; // overrides regular cache_items, will there be conflicts?
        } else {
            $wnd.pgu_geo.cache_items = JSON.parse(items2locations);
        }


        if (!referential) {
            $wnd.pgu_geo.cache_referential = {}; // overrides regular cache_referential
        } else {
            $wnd.pgu_geo.cache_referential = JSON.parse(referential);
        }

        $wnd.console.log(' ---- ref ----');
        $wnd.console.log($wnd.pgu_geo.cache_referential);
        $wnd.console.log(' ---- items ----');
        $wnd.console.log($wnd.pgu_geo.cache_items);

    }-*/;

}
