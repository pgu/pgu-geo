package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LocationSearchEvent extends GwtEvent<LocationSearchEvent.Handler> {

    public interface HasLocationSearchHandlers extends HasHandlers {
        HandlerRegistration addLocationSearchHandler(LocationSearchEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onLocationSearch(LocationSearchEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String              itemId;
    private final String              text;

    public LocationSearchEvent(final String itemId, final String text) {
        this.itemId = itemId;
        this.text = text;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onLocationSearch(this);
    }

    public String getItemId() {
        return itemId;
    }

    public String getText() {
        return text;
    }

}
