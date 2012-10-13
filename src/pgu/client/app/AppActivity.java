package pgu.client.app;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.TechnicalErrorEvent;
import pgu.client.app.mvp.BaseClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.Notification;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

public class AppActivity implements //
NotificationEvent.Handler //
, TechnicalErrorEvent.Handler //
, ShowWaitingIndicatorEvent.Handler //
, HideWaitingIndicatorEvent.Handler //
{

    private final AppView         view;
    private final IsWidget        menuView;
    private final ClientUtils     u = new ClientUtils();

    public AppActivity( //
            final IsWidget menuView //
            , final BaseClientFactory clientFactory //
            ) {
        view = clientFactory.getAppView();

        this.menuView = menuView;
    }

    public void start(final EventBus eventBus) {

        eventBus.addHandler(TechnicalErrorEvent.TYPE, this);
        eventBus.addHandler(NotificationEvent.TYPE, this);

        eventBus.addHandler(ShowWaitingIndicatorEvent.TYPE, this);
        eventBus.addHandler(HideWaitingIndicatorEvent.TYPE, this);

        view.getHeader().setWidget(menuView);
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
