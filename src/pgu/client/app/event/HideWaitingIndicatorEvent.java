package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class HideWaitingIndicatorEvent extends GwtEvent<HideWaitingIndicatorEvent.Handler> {

    public interface HasHideWaitingIndicatorHandlers extends HasHandlers {
        HandlerRegistration addHideWaitingIndicatorHandler(HideWaitingIndicatorEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onHideWaitingIndicator(HideWaitingIndicatorEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onHideWaitingIndicator(this);
    }

}
