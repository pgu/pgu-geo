package pgu.client.pub.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class UserHeadlineEvent extends GwtEvent<UserHeadlineEvent.Handler> {

    public interface HasUserHeadlineHandlers extends HasHandlers {
        HandlerRegistration addUserHeadlineHandler(UserHeadlineEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onUserHeadline(UserHeadlineEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String userHeadline;

    public UserHeadlineEvent(final String userHeadline) {
        this.userHeadline = userHeadline;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onUserHeadline(this);
    }

    public String getUserHeadline() {
        return userHeadline;
    }

}
