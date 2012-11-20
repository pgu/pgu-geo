package pgu.client.app.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ContactsLoadedEvent extends GwtEvent<ContactsLoadedEvent.Handler> {

    public interface HasContactsLoadedHandlers extends HasHandlers {
        HandlerRegistration addContactsLoadedHandler(ContactsLoadedEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onContactsLoaded(ContactsLoadedEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onContactsLoaded(this);
    }

}
