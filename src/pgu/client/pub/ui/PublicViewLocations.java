package pgu.client.pub.ui;


public class PublicViewLocations {

    public native void initCaches( //
            final String items2locations //
            , final String referential //
            ) /*-{

        if (!items2locations) {
            $wnd.pgu_geo.public_cache_items = {};
        } else {
            $wnd.pgu_geo.public_cache_items = JSON.parse(items2locations);
        }

        if (!referential) {
            $wnd.pgu_geo.public_cache_referential = {};
        } else {
            $wnd.pgu_geo.public_cache_referential = JSON.parse(referential);
        }

    }-*/;

}