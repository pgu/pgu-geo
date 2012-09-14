package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationShowOnMapEvent extends GwtEvent<LocationShowOnMapEvent.Handler> {

    public interface HasLocationShowOnMapHandlers extends HasHandlers {
        HandlerRegistration addLocationShowOnMapHandler(LocationShowOnMapEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationShowOnMap(LocationShowOnMapEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String locName;

    public LocationShowOnMapEvent(final String locName) {
        this.locName = locName;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationShowOnMap(this);
    }

    public String getLocName() {
        return locName;
    }

}
