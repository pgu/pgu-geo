package pgu.client.profile.ui;


public class EditLocationUtils {

    public static native void showLatitudeAndLongitude(final EditLocationViewImpl view, final String location_name) /*-{

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
