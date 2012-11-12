package pgu.client.pub.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FetchPublicContactsEvent extends GwtEvent<FetchPublicContactsEvent.Handler> {

    public interface HasFetchPublicContactsHandlers extends HasHandlers {
        HandlerRegistration addFetchPublicContactsHandler(FetchPublicContactsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onFetchPublicContacts(FetchPublicContactsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String userId;

    public FetchPublicContactsEvent(final String userId) {
        this.userId = userId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onFetchPublicContacts(this);
    }

    public String getUserId() {
        return userId;
    }

}
