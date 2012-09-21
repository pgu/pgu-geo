package pgu.client.pub.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class UserNameEvent extends GwtEvent<UserNameEvent.Handler> {

    public interface HasUserNameHandlers extends HasHandlers {
        HandlerRegistration addUserNameHandler(UserNameEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onUserName(UserNameEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String userName;

    public UserNameEvent(final String userName) {
        this.userName = userName;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onUserName(this);
    }

    public String getUserName() {
        return userName;
    }

}
