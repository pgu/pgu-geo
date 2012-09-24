package pgu.client.app.mvp;

import pgu.client.app.AppView;
import pgu.client.app.ui.AppViewImpl;
import pgu.client.pub.PublicMenuView;
import pgu.client.pub.PublicView;
import pgu.client.pub.ui.PublicMenuViewImpl;
import pgu.client.pub.ui.PublicViewImpl;
import pgu.client.service.PublicProfileService;
import pgu.client.service.PublicProfileServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class PublicClientFactoryImpl implements PublicClientFactory {

    private static EventBus                  eventBus             = new SimpleEventBus();
    private static PlaceController           placeController      = new PlaceController(eventBus);
    private static AppView                   appView              = new AppViewImpl();

    private static PublicView                publicView           = new PublicViewImpl();
    private static PublicMenuView            publicMenuView       = new PublicMenuViewImpl();

    private static PublicProfileServiceAsync publicProfileService = GWT.create(PublicProfileService.class);

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public AppView getAppView() {
        return appView;
    }

    @Override
    public PublicView getPublicView() {
        return publicView;
    }

    @Override
    public PublicProfileServiceAsync getPublicProfileService() {
        return publicProfileService;
    }

    @Override
    public PublicMenuView getPublicMenuView() {
        return publicMenuView;
    }


}
