package pgu.client.profile.ui;

import java.util.ArrayList;

import pgu.client.app.utils.LocationsUtils;

public class EditLocationUtils {

    public static void addExistingLocations(final String itemConfigId, final ArrayList<String> selectedLocations) {
        for (final String locationName: selectedLocations) {
            LocationsUtils.addLocation2Item(itemConfigId, locationName);
        }
    }

    public static native void showLatitudeAndLongitude(final EditLocationViewImpl editLocationViewImpl, final String locationName) /*-{

    }-*/;

}
