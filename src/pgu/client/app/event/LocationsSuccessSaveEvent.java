package pgu.client.app.event;

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

    public LocationsSuccessSaveEvent(final String itemConfigId) {
        this.itemConfigId = itemConfigId;
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

}
