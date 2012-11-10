package pgu.client.contacts.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FetchContactsNamesEvent extends GwtEvent<FetchContactsNamesEvent.Handler> {

    public interface HasFetchContactsNamesHandlers extends HasHandlers {
        HandlerRegistration addFetchContactsNamesHandler(FetchContactsNamesEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onFetchContactsNames(FetchContactsNamesEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onFetchContactsNames(this);
    }

}
