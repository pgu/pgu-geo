package pgu.client.app.event;

import java.util.ArrayList;

import pgu.shared.dto.ItemLocation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationsSuccessSaveEvent extends GwtEvent<LocationsSuccessSaveEvent.Handler> {

    public interface HasLocationsSuccessSaveHandlers extends HasHandlers {
        HandlerRegistration addLocationsSuccessSaveHandler(LocationsSuccessSaveEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationsSuccessSave(LocationsSuccessSaveEvent event);
    }

    public static final Type<Handler>     TYPE             = new Type<Handler>();

    private final String                  itemId;
    private final ArrayList<ItemLocation> newItemLocations = new ArrayList<ItemLocation>();

    public LocationsSuccessSaveEvent(final String itemId, final ItemLocation newItemLocation) {
        this.itemId = itemId;
        newItemLocations.add(newItemLocation);
    }

    public LocationsSuccessSaveEvent(final String itemId, final ArrayList<ItemLocation> newItemLocations) {
        this.itemId = itemId;
        this.newItemLocations.addAll(newItemLocations);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationsSuccessSave(this);
    }

    public String getItemId() {
        return itemId;
    }

    public ArrayList<ItemLocation> getNewItemLocations() {
        return newItemLocations;
    }

}
