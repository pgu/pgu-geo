package pgu.client.menu.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class GoToContactsEvent extends GwtEvent<GoToContactsEvent.Handler> {

    public interface HasGoToContactsHandlers extends HasHandlers {
        HandlerRegistration addGoToContactsHandler(GoToContactsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onGoToContacts(GoToContactsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onGoToContacts(this);
    }

}
