package pgu.client.app.event;

import java.util.ArrayList;

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

    private final String                  itemConfigId;
    private final ArrayList<String> newItemLocations = new ArrayList<String>();

    public LocationsSuccessSaveEvent(final String itemConfigId, final String newItemLocation) {
        this.itemConfigId = itemConfigId;
        newItemLocations.add(newItemLocation);
    }

    public LocationsSuccessSaveEvent(final String itemConfigId, final ArrayList<String> newItemLocations) {
        this.itemConfigId = itemConfigId;
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

    public String getItemConfigId() {
        return itemConfigId;
    }

    public ArrayList<String> getNewItemLocations() {
        return newItemLocations;
    }

}
