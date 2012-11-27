package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FetchProfileLocationsEvent extends GwtEvent<FetchProfileLocationsEvent.Handler> {

    public interface HasFetchProfileLocationsHandlers extends HasHandlers {
        HandlerRegistration addFetchProfileLocationsHandler(FetchProfileLocationsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onFetchProfileLocations(FetchProfileLocationsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onFetchProfileLocations(this);
    }

}
