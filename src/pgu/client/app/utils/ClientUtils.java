package pgu.client.app.utils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pgu.shared.dto.ItemLocation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Command;

public class ClientUtils {

    public void info(final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            final Logger logger = Logger.getLogger("pgu");
            logger.log(Level.INFO, message);
        }
    }

    public boolean isVoid(final String str) {
        return null == str || str.trim().isEmpty();
    }

    public void fire(final com.google.gwt.event.shared.EventBus eventBus, final GwtEvent event) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                eventBus.fireEvent(event);
            }
        });
    }

    public void fire(final com.google.web.bindery.event.shared.EventBus eventBus, final GwtEvent event) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                eventBus.fireEvent(event);
            }
        });
    }

    public void addNewLocationsToItem(final String itemConfigId, final ArrayList<String> locations) {
        for (final String loc : locations) {

            addNewLocationToItem(itemConfigId, loc.getName(), loc.getLat(), loc.getLng());
        }

        refreshHtmlLocationsForItem(itemConfigId);
    }

    private native void addNewLocationToItem(String item_config_Id, String location_name, String lat, String lng) /*-{

        @pgu.client.app.utils.LocationsUtils::updateLocationReferential(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(location_name,lat,lng);
        @pgu.client.app.utils.LocationsUtils::addLocation2Item(Ljava/lang/String;Ljava/lang/String;)(item_config_id,location_name);

    }-*/;

    private native void refreshHtmlLocationsForItem(final String itemId) /*-{

		var html_locations = $wnd.createListLocations(itemId);
		$doc.getElementById("locations_" + itemId).innerHTML = html_locations;

    }-*/;

    public void removeLocationFromItem(final String itemId, final ItemLocation deletedItemLocation) {
        removeLocationFromItem(itemId, deletedItemLocation.getName());
        refreshHtmlLocationsForItem(itemId);
    }

    private native void removeLocationFromItem(String itemId, String locationName) /*-{

		var locations = $wnd.cache_itemId2locations[itemId];
		var updateds = [];

		for ( var i = 0, len = locations.length; i < len; i++) {
			var location = locations[i];

			if (location.name === locationName) {
				continue;
			}

			updateds.push(location);
		}

		$wnd.cache_itemId2locations[itemId] = updateds;
    }-*/;

    public void showNotificationError(final Throwable t, final HasNotifications view) {

        final StringBuilder sb = new StringBuilder();
        sb.append(t.getMessage());
        sb.append("<br>");

        for (final StackTraceElement ste : t.getStackTrace()) {
            sb.append(ste);
            sb.append("<br>");
        }

        final Notification notification = view.newNotification();
        notification.setHeading("Technical Error");
        notification.setText(sb.toString());
        notification.setLevel(pgu.client.app.utils.Level.ERROR);
        notification.show();
    }

    public void showNotificationSuccess(final StringBuilder msg, final HasNotifications view) {

        final Notification notification = view.newNotification();
        notification.setHeading("Success");
        notification.setHTML(msg.toString());
        notification.setLevel(pgu.client.app.utils.Level.SUCCESS);
        notification.show();
    }

    public static void log(final String msg) {
        GWT.log(msg);
    }

}
