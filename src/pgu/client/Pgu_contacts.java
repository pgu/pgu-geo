package pgu.client;

import pgu.client.app.AppActivity;
import pgu.client.app.AppView;
import pgu.client.app.mvp.AppActivityMapper;
import pgu.client.app.mvp.AppPlaceHistoryMapper;
import pgu.client.app.mvp.BaseClientFactory;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.mvp.ClientFactoryImpl;
import pgu.client.app.mvp.PublicAppActivityMapper;
import pgu.client.app.mvp.PublicClientFactory;
import pgu.client.app.mvp.PublicClientFactoryImpl;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ChartsUtils;
import pgu.client.app.utils.GeoUtils;
import pgu.client.contacts.ui.ContactsViewImpl;
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
import com.google.gwt.core.client.RunAsyncCallback;
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

        final ChartsUtils chartsUtils = new ChartsUtils();
        chartsUtils.exportMethods();

        if (!isPublic) {
            ProfileViewImpl.exportMethods();
            ContactsViewImpl.exportMethods();
        }

    }

    private void initMVPContext(final boolean isPublic) {

        if (isPublic) {
            final PublicClientFactoryImpl publicClientFactory = new PublicClientFactoryImpl();
            mvpContext.clientFactory = publicClientFactory;
            mvpContext.activityMapper = new PublicAppActivityMapper(publicClientFactory);

        } else {
            final ClientFactoryImpl clientFactory = new ClientFactoryImpl();
            mvpContext.clientFactory = clientFactory;
            mvpContext.activityMapper = new AppActivityMapper(clientFactory);

        }

        mvpContext.eventBus = mvpContext.clientFactory.getEventBus();
        mvpContext.placeController = mvpContext.clientFactory.getPlaceController();
    }

    @Override
    public void onModuleLoad() {

        final boolean isPublic = History.getToken().startsWith("PublicPlace");

        initJSContext(isPublic);
        initMVPContext(isPublic);

        final EventBus eventBus = mvpContext.eventBus;
        final PlaceController placeController = mvpContext.placeController;

        ChartsUtils.setEventBus(eventBus);

        if (isPublic) {

            GWT.runAsync(new RunAsyncCallback() {

                @Override
                public void onSuccess() {

                    final PublicClientFactory clientFactory = (PublicClientFactory) mvpContext.clientFactory;

                    final PublicMenuActivity menuActivity = new PublicMenuActivity(clientFactory);
                    menuActivity.start(eventBus);
                    final PublicMenuView menuView = clientFactory.getPublicMenuView();

                    final Place defaultPlace = placeController.getWhere();

                    startApplication(menuView, defaultPlace);
                }

                @Override
                public void onFailure(final Throwable reason) {
                    GWT.log("!!! problem: " + reason.getMessage());
                }
            });

        } else {

            GWT.runAsync(new RunAsyncCallback() {

                @Override
                public void onSuccess() {

                    final ClientFactory clientFactory = (ClientFactory) mvpContext.clientFactory;

                    clientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                            new AsyncCallbackApp<LoginInfo>(eventBus) {

                        @Override
                        public void onSuccess(final LoginInfo loginInfo) {
                            clientFactory.setLoginInfo(loginInfo);

                            final MenuActivity menuActivity = new MenuActivity(clientFactory, placeController);
                            menuActivity.start(eventBus);
                            final MenuView menuView = clientFactory.getMenuView();

                            final Place defaultPlace = new ProfilePlace();

                            startApplication(menuView, defaultPlace);
                        }

                    });
                }

                @Override
                public void onFailure(final Throwable reason) {
                    GWT.log("!!! problem: " + reason.getMessage());
                }
            });

        }
    }

    private void startApplication(final IsWidget menuView, final Place defaultPlace) {

        final BaseClientFactory clientFactory = mvpContext.clientFactory;
        final EventBus eventBus = mvpContext.eventBus;
        final PlaceController placeController = mvpContext.placeController;
        final ActivityMapper activityMapper = mvpContext.activityMapper;

        final AppView appView = clientFactory.getAppView();

        final AppActivity appActivity = new AppActivity(menuView, clientFactory);
        appActivity.start(eventBus);

        final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appView);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(appView);
        historyHandler.handleCurrentHistory();
    }

    private static class MVPContext {
        private ActivityMapper activityMapper;
        private BaseClientFactory clientFactory;
        private EventBus eventBus;
        private PlaceController placeController;
    }

}
