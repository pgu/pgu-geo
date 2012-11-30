package pgu.client.profile.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.LocationsUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class ProfileViewGeocoder {

    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
    }

    public native JavaScriptObject geocoder() /*-{
        return $wnd.pgu_geo.geocoder;
    }-*/;

    public void searchGeopoint(final String location_name, final JavaScriptObject callback) {

        if (LocationsUtils.isLocationInReferential(location_name)) {
            executeCallback(callback);
            return;
        }

        searchAndAddToCache(location_name, callback);
    }

    private native void executeCallback(JavaScriptObject callback) /*-{
        if (callback) {
            $wnd.console.log('executeCallback');

            var google = this.@pgu.client.profile.ui.ProfileViewGeocoder::google()
                              ();

            callback(google.maps.GeocoderStatus.OK);
        }
    }-*/;

    private native void searchAndAddToCache(String location_name, JavaScriptObject callback) /*-{
        $wnd.console.log('searchAndAddToCache');

        var google = this.@pgu.client.profile.ui.ProfileViewGeocoder::google()
                          ();

        var geocoder = this.@pgu.client.profile.ui.ProfileViewGeocoder::geocoder()
                            ();

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

                        // TODO
                        @pgu.client.app.utils.LocationsUtils::addGeopointToCache(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                        (location_name, lat, lng);


                    } else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
                        var warn = {
                            name: 'Unknown location, status'
                          , msg: location_name + ", " + status
                        }
                        $wnd.console.log(warn);

                    } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {

                        this.@pgu.client.profile.ui.ProfileViewGeocoder::searchGeopointWithDelay(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;I)
                        (location_name, callback, 1000);

                        var warn = {
                            name: 'Over query limit'
                          , msg: location_name
                        }
                        $wnd.console.log(warn);

                    } else {

                        var err = {
                            name: 'Technical error, status'
                          , msg: location_name + ", " + status
                        }
                        $wnd.console.log(err);
                    }

                    if (callback) {
                        callback(status);
                    }
                }
        );

    }-*/;

    private void searchGeopointWithDelay( //
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


}