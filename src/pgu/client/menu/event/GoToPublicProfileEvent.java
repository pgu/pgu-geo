package pgu.client.menu.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class GoToPublicProfileEvent extends GwtEvent<GoToPublicProfileEvent.Handler> {

    public interface HasGoToPublicProfileHandlers extends HasHandlers {
        HandlerRegistration addGoToPublicProfileHandler(GoToPublicProfileEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onGoToPublicProfile(GoToPublicProfileEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onGoToPublicProfile(this);
    }

}
