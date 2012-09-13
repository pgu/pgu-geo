package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationAddNewEvent extends GwtEvent<LocationAddNewEvent.Handler> {

    public interface HasLocationAddNewHandlers extends HasHandlers {
        HandlerRegistration addLocationAddNewHandler(LocationAddNewEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationAddNew(LocationAddNewEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String              itemConfigId;

    public LocationAddNewEvent(final String itemConfigId) {
        this.itemConfigId = itemConfigId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationAddNew(this);
    }

    public String getItemConfigId() {
        return itemConfigId;
    }

}
