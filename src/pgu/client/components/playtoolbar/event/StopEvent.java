package pgu.client.components.playtoolbar.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class StopEvent extends GwtEvent<StopEvent.Handler> {

    public interface HasStopHandlers extends HasHandlers {
        HandlerRegistration addStopHandler(StopEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onStop(StopEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onStop(this);
    }

}
