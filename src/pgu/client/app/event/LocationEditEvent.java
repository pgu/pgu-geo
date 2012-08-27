package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationEditEvent extends GwtEvent<LocationEditEvent.Handler> {

    public interface HasLocationEditHandlers extends HasHandlers {
        HandlerRegistration addLocationEditHandler(LocationEditEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationEdit(LocationEditEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String              locationId;
    private final String              itemId;

    public LocationEditEvent(final String locationId, final String itemId) {
        this.locationId = locationId;
        this.itemId = itemId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationEdit(this);
    }

    public String getLocationId() {
        return locationId;
    }

    public String getItemId() {
        return itemId;
    }

}
