package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SavePublicLocationsEvent extends GwtEvent<SavePublicLocationsEvent.Handler> {

    public interface HasSavePublicLocationsHandlers extends HasHandlers {
        HandlerRegistration addSavePublicLocationsHandler(SavePublicLocationsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSavePublicLocations(SavePublicLocationsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSavePublicLocations(this);
    }

}
