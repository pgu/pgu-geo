package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationSuccessDeleteEvent extends GwtEvent<LocationSuccessDeleteEvent.Handler> {

    public interface HasLocationSuccessDeleteHandlers extends HasHandlers {
        HandlerRegistration addLocationSuccessDeleteHandler(LocationSuccessDeleteEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationSuccessDelete(LocationSuccessDeleteEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String itemConfigId;

    public LocationSuccessDeleteEvent(final String itemConfigId) {
        this.itemConfigId = itemConfigId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationSuccessDelete(this);
    }

    public String getItemConfigId() {
        return itemConfigId;
    }

}
