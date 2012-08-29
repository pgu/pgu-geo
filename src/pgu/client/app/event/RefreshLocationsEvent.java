package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RefreshLocationsEvent extends GwtEvent<RefreshLocationsEvent.Handler> {

    public interface HasRefreshLocationsHandlers extends HasHandlers {
        HandlerRegistration addRefreshLocationsHandler(RefreshLocationsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onRefreshLocations(RefreshLocationsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String              itemId;

    public RefreshLocationsEvent(final String itemId) {
        this.itemId = itemId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onRefreshLocations(this);
    }

    public String getItemId() {
        return itemId;
    }

}
