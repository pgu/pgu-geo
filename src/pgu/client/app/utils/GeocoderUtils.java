package pgu.client.app.utils;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class GeocoderUtils {

    static {
        exportMethod();
    }

    private static native void exportMethod() /*-{
        $wnd.pgu_geo.init_geocoder = $entry(@pgu.client.app.utils.GeocoderUtils::initGeocoder());
    }-*/;

    public static native void initGeocoder() /*-{
        $wnd.pgu_geo.geocoder = new $wnd.google.maps.Geocoder();
    }-*/;

    public static void searchGeopoint(final String locationName) {

        if (LocationsUtils.isLocationInReferential(locationName)) {
            return;
        }

        if (!isGeocoderAvailable()) {
            searchGeopointWithDelay(locationName, 1000);
            return;
        }

        searchAndAdd(locationName);
    }

    private static native boolean isGeocoderAvailable() /*-{
        return $wnd.pgu_geo.geocoder !== undefined;
    }-*/;

    public static void searchGeopointWithDelay(final String locationName, final int delayMillis) {
        new Timer() {

            @Override
            public void run() {
                Scheduler.get().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        searchGeopoint(locationName);
                    }
                });
            }

        }.schedule(delayMillis);
    }

    public static native void searchAndAdd(String location_name) /*-{
        var
            geocoder = $wnd.pgu_geo.geocoder
          , google = $wnd.google
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
                        @pgu.client.app.utils.LocationsUtils::addGeopoint(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);

                    } else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
                        @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown location: "
                                  + location_name + ", " + status);

                    } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                        @pgu.client.app.utils.GeocoderUtils::searchGeopointWithDelay(Ljava/lang/String;I)(location_name,1000);
                        @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("over_query_limit... " + location_name);

                    } else {
                        @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Oups: " + status);
                    }
                }
        );

    }-*/;



}
