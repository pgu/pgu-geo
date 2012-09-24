package pgu.client.app;

import pgu.client.app.event.GoToContactsEvent;
import pgu.client.app.event.GoToProfileEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.TechnicalErrorEvent;
import pgu.client.app.mvp.BaseClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.Notification;
import pgu.client.contacts.ContactsPlace;
import pgu.client.profile.ProfilePlace;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

public class AppActivity implements //
NotificationEvent.Handler //
, TechnicalErrorEvent.Handler //
, GoToProfileEvent.Handler //
, GoToContactsEvent.Handler //
{

    private final BaseClientFactory   clientFactory;
    private final AppView         view;
    private final IsWidget        menuView;
    private final PlaceController placeController;
    private EventBus              eventBus;
    private final ClientUtils     u = new ClientUtils();

    public AppActivity( //
            final IsWidget menuView //
            , final PlaceController placeController //
            , final BaseClientFactory clientFactory //
            ) {
        this.clientFactory = clientFactory;
        this.placeController = placeController;

        view = clientFactory.getAppView();
        this.menuView = menuView;
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.addHandler(TechnicalErrorEvent.TYPE, this);
        eventBus.addHandler(NotificationEvent.TYPE, this);
        eventBus.addHandler(GoToProfileEvent.TYPE, this);
        eventBus.addHandler(GoToContactsEvent.TYPE, this);

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
    public void onGoToContacts(final GoToContactsEvent event) {
        placeController.goTo(new ContactsPlace());
    }

    @Override
    public void onGoToProfile(final GoToProfileEvent event) {
        placeController.goTo(new ProfilePlace());
    }

}
