package pgu.client.app;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.TechnicalErrorEvent;
import pgu.client.app.utils.ClientHelper;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.Notification;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class AppActivity implements //
NotificationEvent.Handler //
, TechnicalErrorEvent.Handler //
, ShowWaitingIndicatorEvent.Handler //
, HideWaitingIndicatorEvent.Handler //
{

    private final AppView         view;
    private final ClientHelper     u = new ClientHelper();

    public AppActivity(final AppView view) {
        this.view = view;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        eventBus.addHandler(TechnicalErrorEvent.TYPE, this);
        eventBus.addHandler(NotificationEvent.TYPE, this);

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);

        panel.setWidget(view.asWidget());
    }

    @Override
    public void onTechnicalError(final TechnicalErrorEvent event) {
        u.showNotificationError(event.getThrowable(), view);
    }

    @Override
    public void onNotification(final NotificationEvent event) {

        final Level level = event.getLevel();
        final String message = event.getMessage();

        final Notification notification = view.newNotification();
        notification.setHTML(message);

        if (Level.INFO == level) {
            notification.setHeading("Info");
            notification.setLevel(Level.INFO);

        } else if (Level.SUCCESS == level) {
            notification.setHeading("Success");
            notification.setLevel(Level.SUCCESS);

        } else if (Level.WARNING == level) {
            notification.setHeading("Warning");
            notification.setLevel(Level.WARNING);

        } else if (Level.ERROR == level) {
            notification.setHeading("Ups!");
            notification.setLevel(Level.ERROR);
        }

        notification.show();
    }

    @Override
    public void onHideWaitingIndicator(final HideWaitingIndicatorEvent event) {
        view.hideWaitingIndicator();
    }

    @Override
    public void onShowWaitingIndicator(final ShowWaitingIndicatorEvent event) {
        view.showWaitingIndicator();
    }

}
