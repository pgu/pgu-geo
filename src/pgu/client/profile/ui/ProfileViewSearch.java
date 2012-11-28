package pgu.client.profile.ui;

import pgu.client.app.utils.GeocoderHelper;
import pgu.client.app.utils.GoogleHelper;
import pgu.client.profile.ProfileActivity;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewSearch {

    private final GeocoderHelper geocoder = new GeocoderHelper();
    private final GoogleHelper google = new GoogleHelper();
    private final ProfileViewMap viewMap = new ProfileViewMap();
    private final ProfileViewMarkers markers = new ProfileViewMarkers();

    private JavaScriptObject geocoder() {
        return geocoder.geocoder();
    }

    private JavaScriptObject google() {
        return google.google();
    }

    private JavaScriptObject profileMap() {
        return viewMap.profileMap();
    }

    private void createMarkerOnProfileMap(final String location_name, final String lat, final String lng) {
        markers.createMarkerOnProfileMap(location_name, lat, lng);
    }

    public native void searchLocationAndAddMarker(ProfileViewImpl view, String location_name) /*-{

        $wnd.console.log('searchLocationAndAddMarker');

        var geocoder = this.@pgu.client.profile.ui.ProfileViewSearch::geocoder()
                            ();

        var google = this.@pgu.client.profile.ui.ProfileViewSearch::google()
                          ();

        var map = this.@pgu.client.profile.ui.ProfileViewSearch::profileMap()
                       ();

        geocoder
                .geocode(
                        {
                            'address' : location_name
                        },
                        function(results, status) {

                            if (status != google.maps.GeocoderStatus.OK) {
                                var msg = "Geocode was not successful for the following reason: " + status;
                                view.@pgu.client.profile.ui.ProfileViewImpl::showNotificationWarning(Ljava/lang/String;)
                                     (msg);
                                return;
                            }

                            var
                                loc = results[0].geometry.location
                              , lat = '' + loc.lat()
                              , lng = '' + loc.lng()
                            ;

                            this.@pgu.client.profile.ui.ProfileViewSearch::createMarkerOnProfileMap(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                                 (location_name,lat,lng);

                            view.@pgu.client.profile.ui.ProfileViewImpl::cacheLastSearchedLocation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                                 (location_name,lat,lng);
                        });
    }-*/;

    /**
     * @deprecated
     */
    @Deprecated
    public static native void addNewLocation(ProfileActivity activity, String item_config_id, String location_name) /*-{

        $wnd.console.log('addNewLocation');

        var
          geocoder = @pgu.client.app.utils.GeocoderUtils::geocoder()()
        , google = @pgu.client.app.utils.GoogleUtils::google()()
        , map = @pgu.client.profile.ui.ProfileUtils::profileMap()()
        ;

        geocoder.geocode(
                {
                    'address' : location_name
                },
                function(results, status) {

                    if (status != google.maps.GeocoderStatus.OK) {
                        var msg = "Geocode was not successful for the following reason: " + status;
                        activity.@pgu.client.profile.ProfileActivity::showNotificationWarning(Ljava/lang/String;)( //
                        msg);
                        return;
                    }

                    var loc = results[0].geometry.location
                            , lat = '' + loc.lat()
                            , lng = '' + loc.lng()
                            ;

                    var is_doublon = @pgu.client.app.utils.LocationsUtils::isDoublon(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
                                     item_config_id,location_name,lat,lng);

                    if (is_doublon) {
                        activity.@pgu.client.profile.ProfileActivity::saveLocationService(ZLjava/lang/String;)( //
                        true,location_name);

                    } else {
                        @pgu.client.app.utils.LocationsUtils::addGeopointToCopyCache(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
                        location_name,lat,lng);

                        @pgu.client.app.utils.LocationsUtils::addLocation2ItemInCopyCache(Ljava/lang/String;Ljava/lang/String;)( //
                        item_config_id, location_name);

                        activity.@pgu.client.profile.ProfileActivity::saveLocationService(ZLjava/lang/String;)( //
                        false,location_name);
                    }
                });
    }-*/;

}

