package pgu.client.app.utils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pgu.shared.dto.ItemLocation;

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

    public void addNewLocationsToItem(final String itemId, final ArrayList<ItemLocation> locations) {
        for (final ItemLocation loc : locations) {

            addNewLocationToItem(itemId, loc.getName(), loc.getLat(), loc.getLng());
        }

        refreshHtmlLocationsForItem(itemId);
    }

    private native void addNewLocationToItem(String itemId, String name, String lat, String lng) /*-{

		var loc = {};
		loc.name = name;
		loc.lat = lat;
		loc.lng = lng;

		$wnd.cache_itemId2locations[itemId].push(loc);

    }-*/;

    private native void refreshHtmlLocationsForItem(final String itemId) /*-{

		var html_locations = $wnd.createListLocations(itemId);
		$doc.getElementById("locations_" + itemId).innerHTML = html_locations;

    }-*/;

    public String getCopyCacheWithNewLocationsJson(final String itemId, final ItemLocation itemLocation) {
        final ArrayList<ItemLocation> arr = new ArrayList<ItemLocation>();
        arr.add(itemLocation);
        return getCopyCacheWithNewLocationsJson(itemId, arr);
    }

    public String getCopyCacheWithNewLocationsJson(final String itemId, final ArrayList<ItemLocation> newLocations) {

        initTemporaryCache();

        for (final ItemLocation newLoc : newLocations) {

            addNewLocationToTemporaryCache(itemId, newLoc.getName(), newLoc.getLat(), newLoc.getLng());
        }

        return fetchAllFromTemporaryCacheJson();
    }

    private native void initTemporaryCache() /*-{

		$wnd.__tmp_cache_itemId2locations = {};

		var orig = $wnd.cache_itemId2locations;
		var copy = $wnd.__tmp_cache_itemId2locations;

		for ( var key in orig) {
			if (orig.hasOwnProperty(key)) {

				var copyLocs = [];
				var locs = orig[key];

				for ( var i = 0, len = locs.length; i < len; i++) {
					var loc = locs[i];

					var copyLoc = {};
					copyLoc.name = loc.name;
					copyLoc.lat = loc.lat;
					copyLoc.lng = loc.lng;

					copyLocs.push(copyLoc);
				}

				copy[key] = copyLocs;
			}
		}

    }-*/;

    private native void addNewLocationToTemporaryCache(String itemId, String name, String lat, String lng) /*-{

		var loc = {};
		loc.name = name;
		loc.lat = lat;
		loc.lng = lng;

		$wnd.__tmp_cache_itemId2locations[itemId].push(loc);

    }-*/;

    private native String fetchAllFromTemporaryCacheJson() /*-{

		var tmp_cache_json = JSON.stringify($wnd.__tmp_cache_itemId2locations);

		$wnd.__tmp_cache_itemId2locations = null;

		return tmp_cache_json;
    }-*/;

    public String getCopyCacheWithoutLocationJson(final String itemId, final ItemLocation itemLocation) {
        // TODO Auto-generated method stub
        return null;
    }

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

}
