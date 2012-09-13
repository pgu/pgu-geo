package pgu.client.profile.ui;

import java.util.ArrayList;

public class EditLocationUtils {

    public static void addExistingLocations(final String itemConfigId, final ArrayList<String> selectedLocations) {
        for (final String locationName: selectedLocations) {
            addExistingLocation(itemConfigId, locationName);
        }
    }

    public static native void addExistingLocation(String item_config_id, String location_name) /*-{
        @pgu.client.app.utils.LocationsUtils::addLocation2Item(Ljava/lang/String;Ljava/lang/String;)(item_config_id, location_name);
    }-*/;

    public static native void deleteLocationFromItem(final String item_config_id, final String location_name) /*-{
        @pgu.client.app.utils.LocationsUtils::removeLocationFromItem(Ljava/lang/String;Ljava/lang/String;)(item_config_id, location_name);
    }-*/;



}
