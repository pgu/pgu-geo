package pgu.client.menu.ui;

import pgu.client.menu.MenuActivity;

public class MenuViewUtils {

    public static native void searchLocationAndAddMarker(MenuViewImpl menu, String location_name) /*-{
        var
          geocoder = @pgu.client.app.utils.GeocoderUtils::geocoder()()
        , google = $wnd.google
        , map = @pgu.client.profile.ui.ProfileUtils::profileMap()()
        ;

        geocoder
                .geocode(
                        {
                            'address' : location_name
                        },
                        function(results, status) {

                            if (status != google.maps.GeocoderStatus.OK) {
                                var msg = "Geocode was not successful for the following reason: " + status;
                                menu.@pgu.client.menu.ui.MenuViewImpl::showNotificationWarning(Ljava/lang/String;)(msg);
                                return;
                            }

                            var loc = results[0].geometry.location
                            , lat = '' + loc.lat()
                            , lng = '' + loc.lng()
                            ;

                            @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);

                            menu.@pgu.client.menu.ui.MenuViewImpl::cacheLastSearchedLocation(Ljava/lang/String;)(location_name);
                        });
    }-*/;

    public static native void addNewLocation(MenuActivity activity, String item_config_id, String location_name) /*-{
        var
          geocoder = @pgu.client.app.utils.GeocoderUtils::geocoder()()
        , google = $wnd.google
        , map = @pgu.client.profile.ui.ProfileUtils::profileMap()()
        ;

        geocoder.geocode(
                {
                    'address' : location_name
                },
                function(results, status) {

                    if (status != google.maps.GeocoderStatus.OK) {
                        var msg = "Geocode was not successful for the following reason: " + status;
                        menu.@pgu.client.menu.ui.MenuViewImpl::showNotificationWarning(Ljava/lang/String;)(msg);
                        return;
                    }

                    var loc = results[0].geometry.location
                            , lat = '' + loc.lat()
                            , lng = '' + loc.lng()
                            ;

                    var is_doublon = @pgu.client.app.utils.LocationsUtils::isDoublon(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(item_config_id,location_name,lat,lng);
                    if (is_doublon) {
                        activity.@pgu.client.menu.MenuActivity::saveLocationService(ZLjava/lang/String;)(true,location_name);

                    } else {
                        @pgu.client.app.utils.LocationsUtils::addGeopointToCopyCache(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);
                        @pgu.client.app.utils.LocationsUtils::addLocation2ItemInCopyCache(Ljava/lang/String;Ljava/lang/String;)(item_config_id, location_name);
                        activity.@pgu.client.menu.MenuActivity::saveLocationService(ZLjava/lang/String;)(false,location_name);
                    }
                });
    }-*/;

}
