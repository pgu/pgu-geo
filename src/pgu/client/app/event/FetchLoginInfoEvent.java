package pgu.client.app.event;

import pgu.shared.dto.LoginInfo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FetchLoginInfoEvent extends GwtEvent<FetchLoginInfoEvent.Handler> {

    public interface HasFetchLoginInfoHandlers extends HasHandlers {
        HandlerRegistration addFetchLoginInfoHandler(FetchLoginInfoEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onFetchLoginInfo(FetchLoginInfoEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final LoginInfo loginInfo;

    public FetchLoginInfoEvent(final LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onFetchLoginInfo(this);
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

}
