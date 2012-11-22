package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FetchCustomLocationsEvent extends GwtEvent<FetchCustomLocationsEvent.Handler> {

    public interface HasFetchCustomLocationsHandlers extends HasHandlers {
        HandlerRegistration addFetchCustomLocationsHandler(FetchCustomLocationsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onFetchCustomLocations(FetchCustomLocationsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onFetchCustomLocations(this);
    }

}
