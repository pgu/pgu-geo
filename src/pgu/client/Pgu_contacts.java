package pgu.client;

import pgu.client.app.AppActivity;
import pgu.client.app.AppView;
import pgu.client.app.mvp.AppActivityMapper;
import pgu.client.app.mvp.AppPlaceHistoryMapper;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.GeoUtils;
import pgu.client.menu.MenuActivity;
import pgu.client.menu.MenuView;
import pgu.client.profile.ProfilePlace;
import pgu.client.profile.ui.ProfileViewImpl;
import pgu.client.pub.PublicMenuActivity;
import pgu.client.pub.PublicMenuView;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

public class Pgu_contacts implements EntryPoint {

    private native void initAppJSContext() /*-{
        $wnd.pgu_geo = {};
    }-*/;

    private final MVPContext mvpContext = new MVPContext();

    private void initJSContext(final boolean isPublic) {
        initAppJSContext();

        final GeoUtils geoUtils = new GeoUtils();
        geoUtils.exportMethods(isPublic);

        if (!isPublic) {
            ProfileViewImpl.exportMethods();
        }

    }

    private void initMVPContext() {

        final ClientFactory clientFactory = GWT.create(ClientFactory.class);

        mvpContext.clientFactory = clientFactory;
        mvpContext.eventBus = clientFactory.getEventBus();
        mvpContext.placeController = clientFactory.getPlaceController();
    }

    @Override
    public void onModuleLoad() {

        final boolean isPublic = History.getToken().startsWith("PublicPlace");

        initJSContext(isPublic);
        initMVPContext();

        if (isPublic) {

            final PublicMenuActivity menuActivity = new PublicMenuActivity(mvpContext.clientFactory);
            menuActivity.start(mvpContext.eventBus);
            final PublicMenuView menuView = mvpContext.clientFactory.getPublicMenuView();

            final Place defaultPlace = mvpContext.placeController.getWhere();

            startApplication(menuView, defaultPlace);

        } else {
            mvpContext.clientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                    new AsyncCallbackApp<LoginInfo>(mvpContext.eventBus) {

                @Override
                public void onSuccess(final LoginInfo loginInfo) {
                    mvpContext.clientFactory.setLoginInfo(loginInfo);

                    final MenuActivity menuActivity = new MenuActivity(mvpContext.clientFactory);
                    menuActivity.start(mvpContext.eventBus);
                    final MenuView menuView = mvpContext.clientFactory.getMenuView();

                    final Place defaultPlace = new ProfilePlace();

                    startApplication(menuView, defaultPlace);
                }

            });
        }
    }

    private void startApplication(final IsWidget menuView, final Place defaultPlace) {

        final ClientFactory clientFactory = mvpContext.clientFactory;
        final EventBus eventBus = mvpContext.eventBus;
        final PlaceController placeController = mvpContext.placeController;

        final AppView appView = clientFactory.getAppView();

        final AppActivity appActivity = new AppActivity(menuView, placeController, clientFactory);
        appActivity.start(eventBus);

        final ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appView);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(appView);
        historyHandler.handleCurrentHistory();
    }

    private static class MVPContext {
        private ClientFactory clientFactory;
        private EventBus eventBus;
        private PlaceController placeController;
    }

}
