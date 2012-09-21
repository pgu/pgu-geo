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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

public class Pgu_contacts implements EntryPoint {

    private static native void initAppContext() /*-{
        $wnd.pgu_geo = {};
    }-*/;

    private void initContext() {
        initAppContext();
        GeoUtils.exportMethod();
        ProfileViewImpl.exportMethod();
    }

    @Override
    public void onModuleLoad() {

        initContext();

        final ClientFactory clientFactory = GWT.create(ClientFactory.class);
        final EventBus eventBus = clientFactory.getEventBus();
        final PlaceController placeController = clientFactory.getPlaceController();

        if (History.getToken().startsWith("PublicPlace")) {

            final AppView appView = clientFactory.getAppView();

            final PublicMenuView publicMenuView = clientFactory.getPublicMenuView();

            final AppActivity appActivity = new AppActivity(publicMenuView, placeController, clientFactory);
            appActivity.start(eventBus);

            //            final Place defaultPlace = new PublicPlace();

            final ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
            final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
            activityManager.setDisplay(appView);

            final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
            final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
            historyHandler.register(placeController, eventBus, placeController.getWhere());
            //            historyHandler.register(placeController, eventBus, defaultPlace);

            RootPanel.get().add(appView);
            historyHandler.handleCurrentHistory();

        } else {
            clientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                    new AsyncCallbackApp<LoginInfo>(eventBus) {

                @Override
                public void onSuccess(final LoginInfo loginInfo) {
                    clientFactory.setLoginInfo(loginInfo);

                    final AppView appView = clientFactory.getAppView();

                    final MenuActivity menuActivity = new MenuActivity(clientFactory);
                    menuActivity.start(eventBus);
                    final MenuView menuView = clientFactory.getMenuView();

                    final AppActivity appActivity = new AppActivity(menuView, placeController, clientFactory);
                    appActivity.start(eventBus);

                    final Place defaultPlace = new ProfilePlace();

                    final ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
                    final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
                    activityManager.setDisplay(appView);

                    final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
                    final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
                    historyHandler.register(placeController, eventBus, defaultPlace);

                    RootPanel.get().add(appView);
                    historyHandler.handleCurrentHistory();
                }

            });
        }
    }

}
