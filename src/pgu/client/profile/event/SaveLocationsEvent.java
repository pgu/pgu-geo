package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveLocationsEvent extends GwtEvent<SaveLocationsEvent.Handler> {

    public interface HasSaveLocationsHandlers extends HasHandlers {
        HandlerRegistration addSaveLocationsHandler(SaveLocationsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveLocations(SaveLocationsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveLocations(this);
    }

}
