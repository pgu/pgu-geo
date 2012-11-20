package pgu.client.app.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import pgu.client.app.AppContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Command;

public class ClientUtils {

    public native void console(String msg) /*-{
        $wnd.console.log(msg);
    }-*/;

    public native void throwX(String msg) /*-{
        throw {
            name: 'Technical error'
          , msg: msg
        }
    }-*/;

    public void info(final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            final Logger logger = Logger.getLogger("pgu");
            logger.log(Level.INFO, message);
        }
    }

    public boolean isVoid(final String str) {
        return null == str || str.trim().isEmpty();
    }

    public void fire(final com.google.gwt.event.shared.EventBus eventBus, final GwtEvent event) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                eventBus.fireEvent(event);
            }
        });
    }

    public void fire(final com.google.web.bindery.event.shared.EventBus eventBus, final GwtEvent event) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                eventBus.fireEvent(event);
            }
        });
    }

    public void showNotificationError(final Throwable t, final HasNotifications view) {

        final StringBuilder sb = new StringBuilder();
        sb.append(t.getMessage());
        sb.append("<br>");

        for (final StackTraceElement ste : t.getStackTrace()) {
            sb.append(ste);
            sb.append("<br>");
        }

        final Notification notification = view.newNotification();
        notification.setHeading("Technical Error");
        notification.setText(sb.toString());
        notification.setLevel(pgu.client.app.utils.Level.ERROR);
        notification.show();
    }

    public void showNotificationSuccess(final StringBuilder msg, final HasNotifications view) {

        final Notification notification = view.newNotification();
        notification.setHeading("Success");
        notification.setHTML(msg.toString());
        notification.setLevel(pgu.client.app.utils.Level.SUCCESS);
        notification.show();
    }

    public void log(final String msg) {
        GWT.log(msg);
    }

    public boolean areExternalApisLoaded(final AppContext ctx) {
        return ctx.isShowdownLoaded() //
                && ctx.isChartsApiLoaded() //
                && ctx.isMapsApiLoaded() //
                ;
    }

}
