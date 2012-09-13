package pgu.client.menu.ui;

public class MenuViewUtils {

    static {
        initProfileMovieIndex();
    }

    private static native void initProfileMovieIndex() /*-{
        $wnd.pgu_geo.profile_current_idx = -1
        $wnd.pgu_geo.profile_info_window = new $wnd.google.maps.InfoWindow();
    }-*/;

    public static native void initIndex(boolean isPastToPresent) /*-{
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        $wnd.pgu_geo.profile_current_idx = isPastToPresent ? -1 : nb_items;
    }-*/;

    public static native void decrementIndex(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            if (idx < 0) {
                return;
            }
            idx--;
        } else {
            if (idx >= nb_items) {
                return;
            }
            idx++;
        }

        $wnd.pgu_geo.profile_current_idx = idx;
    }-*/;

    public static native void incrementIndex(boolean isPastToPresent) /*-{

        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            if (idx >= nb_items) {
                return;
            }
            idx++;
        } else {
            if (idx < 0) {
                return;
            }
            idx--;
        }
        $wnd.pgu_geo.profile_current_idx = idx;

    }-*/;

    public static native boolean showFwdBtn(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            return idx < nb_items -1;
        } else {
            return idx > 0;
        }

    }-*/;


    public static native boolean showBwdBtn(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            return idx > 0;
        } else {
            return idx < nb_items -1;
        }

    }-*/;

    public static native boolean showProfileItemOnMap(boolean isPastToPresent) /*-{

        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (idx < 0 || idx >= nb_items) { // out of bounds
            return true; // is done
        }

        var item_config = @pgu.client.profile.ui.ProfileViewUtils::getItemConfig(I)(idx);
        var location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(itemConfig.id);

        var google = $wnd.google;
        var map = $wnd.map;

        for (var i in location_names) {
            var location_name = location_names[i];

            var marker = @pgu.client.app.utils.MarkersUtils::createMarker(Ljava/lang/String;)(location_name);

            if (i ===  "0") {
                var latLng = new google.maps.LatLng(parseFloat(geopoint.lat), parseFloat(geopoint.lng));
                map.setCenter(latLng);

                var info = $wnd.pgu_geo.profile_info_window;
                info.setContent(item_config.short_content + "<br/><br/>" + item_config.long_content);
                info.open(map, marker);
            }

        }

        return false; // is not done
    }-*/;

    public static native void searchLocationAndAddMarker(MenuViewImpl menu, String location_name) /*-{
        var
          geocoder = $wnd.geocoder
        , google = $wnd.google
        , map = $wnd.map
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

                            @pgu.client.app.utils.LocationsUtils::updateLocationReferential(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);

                            @pgu.client.app.utils.MarkersUtils::createMarker(Ljava/lang/String;)(location_name);

                            menu.@pgu.client.menu.ui.MenuViewImpl::cacheLastSearchedLocation(Ljava/lang/String;)(location_name);

                        });

    }-*/;


}
