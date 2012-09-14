package pgu.client.profile.ui;

import java.util.ArrayList;

import pgu.client.app.utils.LocationsUtils;

public class EditLocationUtils {

    public static void addExistingLocations(final String itemConfigId, final ArrayList<String> selectedLocations) {
        for (final String locationName: selectedLocations) {
            LocationsUtils.addLocation2Item(itemConfigId, locationName);
        }
    }

    public static native void showLatitudeAndLongitude(final EditLocationViewImpl view, final String locationName) /*-{

        var lat,lng;

        var geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
        if (geopoint_is_available) {

            var geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name);
            lat = geopoint.lat;
            lng = geopoint.lng;

        } else {
            lat = lng = '';

        }
        view.@pgu.client.profile.ui.EditLocationViewImpl::showLatitudeAndLongitude(Ljava/lang/String;Ljava/lang/String;)(lat,lng);

    }-*/;

}
