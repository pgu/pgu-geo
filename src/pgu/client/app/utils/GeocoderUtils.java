package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class GeocoderUtils {

    public static native JavaScriptObject geocoder() /*-{
        return $wnd.pgu_geo.geocoder;
    }-*/;

    public static void searchGeopoint(final String locationName, final JavaScriptObject callback) {

        if (LocationsUtils.isLocationInReferential(locationName)) {
            executeCallback(callback);
            return;
        }

        if (!isGeocoderAvailable()) {
            searchGeopointWithDelay(locationName, callback, 1000);
            return;
        }

        searchAndAddToCache(locationName, callback);
    }

    private static native void executeCallback(JavaScriptObject callback) /*-{
        if (callback) {

            $wnd.console.log('executeCallback');

            var google = @pgu.client.app.utils.GoogleUtils::google()();
            callback(google.maps.GeocoderStatus.OK);
        }
    }-*/;

    private static native boolean isGeocoderAvailable() /*-{
        return typeof $wnd.pgu_geo.geocoder !== 'undefined';
    }-*/;

    public static void searchGeopointWithDelay( //

            final String locationName //
            , final JavaScriptObject callback //
            , final int delayMillis //
            ) {

        new Timer() {

            @Override
            public void run() {
                Scheduler.get().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        searchGeopoint(locationName, callback);
                    }
                });
            }

        }.schedule(delayMillis);
    }

    private static native void searchAndAddToCache(String location_name, JavaScriptObject callback) /*-{

        $wnd.console.log('serachAndAddToCache');

        var
            geocoder = @pgu.client.app.utils.GeocoderUtils::geocoder()()
          , google = @pgu.client.app.utils.GoogleUtils::google()()
        ;

        geocoder
            .geocode(
                {
                    'address' : location_name
                },
                function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {

                        var
                          location = results[0].geometry.location
                          , lat = '' + location.lat()
                          , lng = '' + location.lng()
                        ;
                        @pgu.client.app.utils.LocationsUtils::addGeopointToCache(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);


                    } else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
                        throw {
                            name: 'Unknown location, status'
                          , msg: location_name + ", " + status
                        }

                    } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                        @pgu.client.app.utils.GeocoderUtils::searchGeopointWithDelay(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;I)(location_name,callback,1000);
                        throw {
                            name: 'Over query limit'
                          , msg: location_name
                        }


                    } else {
                        throw {
                            name: 'Technical error, status'
                          , msg: location_name + ", " + status
                        }
                    }

                    if (callback) {
                        callback(status);
                    }
                }
        );

    }-*/;



}
