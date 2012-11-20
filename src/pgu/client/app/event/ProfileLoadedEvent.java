package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ProfileLoadedEvent extends GwtEvent<ProfileLoadedEvent.Handler> {

    public interface HasProfileLoadedHandlers extends HasHandlers {
        HandlerRegistration addProfileLoadedHandler(ProfileLoadedEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onProfileLoaded(ProfileLoadedEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onProfileLoaded(this);
    }

}
