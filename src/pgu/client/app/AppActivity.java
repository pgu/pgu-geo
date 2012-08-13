package pgu.client.app;

import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.TechnicalErrorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.Notification;
import pgu.client.menu.MenuActivity;
import pgu.client.menu.MenuView;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public class AppActivity implements //
        NotificationEvent.Handler //
        , TechnicalErrorEvent.Handler //
{

    private final ClientFactory   clientFactory;
    private final AppView         view;
    private final MenuView        menuView;
    private final PlaceController placeController;
    private EventBus              eventBus;
    private final ClientUtils     u = new ClientUtils();

    public AppActivity(final PlaceController placeController, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.placeController = placeController;

        view = clientFactory.getAppView();
        menuView = clientFactory.getMenuView();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.addHandler(TechnicalErrorEvent.TYPE, this);
        eventBus.addHandler(NotificationEvent.TYPE, this);

        final MenuActivity menuActivity = new MenuActivity(clientFactory);
        menuActivity.start(eventBus);

        view.getHeader().setWidget(menuView);
    }

    @Override
    public void onTechnicalError(final TechnicalErrorEvent event) {
        final Throwable th = event.getThrowable();
        final StringBuilder sb = new StringBuilder();
        sb.append(th.getMessage());
        sb.append("<br>");

        for (final StackTraceElement ste : th.getStackTrace()) {
            sb.append(ste);
            sb.append("<br>");
        }

        final Notification notif = view.newNotification();
        notif.setHeading("Technical Error");
        notif.setText(sb.toString());
        notif.setLevel(Level.ERROR);
        notif.show();

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

}
