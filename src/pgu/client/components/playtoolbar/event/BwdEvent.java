package pgu.client.components.playtoolbar.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class BwdEvent extends GwtEvent<BwdEvent.Handler> {

    public interface HasBwdHandlers extends HasHandlers {
        HandlerRegistration addBwdHandler(BwdEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onBwd(BwdEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final int token;

    public BwdEvent(final int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onBwd(this);
    }

}
