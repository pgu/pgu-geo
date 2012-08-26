package pgu.client.app.event;

import pgu.shared.dto.LatLng;

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

    private final LatLng              latLng;
    private final String              itemId;
    private final String              locationLabel;

    public LocationSaveEvent(final LatLng latLng, final String itemId, final String locationLabel) {
        this.latLng = latLng;
        this.itemId = itemId;
        this.locationLabel = locationLabel;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationSave(this);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getItemId() {
        return itemId;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

}
