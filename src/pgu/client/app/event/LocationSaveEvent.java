package pgu.client.app.event;

import pgu.shared.dto.ItemLocation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationSaveEvent extends GwtEvent<LocationSaveEvent.Handler> {

    public interface HasLocationSaveHandlers extends HasHandlers {
        HandlerRegistration addLocationSaveHandler(LocationSaveEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationSave(LocationSaveEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String              itemId;
    private final ItemLocation        itemLocation;

    public LocationSaveEvent(final String itemId, final ItemLocation itemLocation) {
        this.itemId = itemId;
        this.itemLocation = itemLocation;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationSave(this);
    }

    public String getItemId() {
        return itemId;
    }

    public ItemLocation getItemLocation() {
        return itemLocation;
    }

}
