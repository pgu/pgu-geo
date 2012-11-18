package pgu.client;

import pgu.client.app.AppActivity;
import pgu.client.app.AppView;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.FetchLoginInfoEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.mvp.AppActivityMapper;
import pgu.client.app.mvp.AppPlaceHistoryMapper;
import pgu.client.app.mvp.BaseClientFactory;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.mvp.ClientFactoryImpl;
import pgu.client.app.mvp.PublicActivityMapper;
import pgu.client.app.mvp.PublicClientFactoryImpl;
import pgu.client.app.mvp.PublicPlaceHistoryMapper;
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
import pgu.client.resources.ResourcesApp;
import pgu.client.signin.SigninActivity;
import pgu.client.signin.SigninView;
import pgu.client.signin.ui.SigninViewImpl;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class Pgu_geo implements EntryPoint {

    private native void initPguGeoContext() /*-{
        $wnd.pgu_geo = {};
    }-*/;

    private final MVPContext mvp = new MVPContext();

    private void initJSContext(final boolean isPublic) {

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
            mvp.clientFactory = publicClientFactory;
            mvp.activityMapper = new PublicActivityMapper(publicClientFactory);

        } else {
            final ClientFactoryImpl clientFactory = new ClientFactoryImpl();
            mvp.clientFactory = clientFactory;
            mvp.activityMapper = new AppActivityMapper(clientFactory);

        }

        mvp.eventBus = mvp.clientFactory.getEventBus();
        mvp.placeController = mvp.clientFactory.getPlaceController();
    }

    private native void exportLinkedinHandlers() /*-{
        $wnd.pgu_geo.load_view = $entry(@pgu.client.Pgu_geo::loadView());
        $wnd.pgu_geo.go_to_signin = $entry(@pgu.client.Pgu_geo::goToSignin());
    }-*/;

    private static Pgu_geo static_self = null;

    // //////////////////////////
    // public
    // //////////////////////////
    private native void exportPublicCallbackOnLoadMapApi() /*-{
        $wnd.pgu_geo.maps_api_is_loaded = $entry(@pgu.client.Pgu_geo::mapsApiIsLoaded());
    }-*/;

    private native void exportPublicCallbackOnLoadChartsApi() /*-{
        $wnd.pgu_geo.charts_api_is_loaded = $entry(@pgu.client.Pgu_geo::chartsApiIsLoaded());
    }-*/;

    private native void exportPublicCallbackOnLoadShowdown() /*-{
        $wnd.pgu_geo.showdown_is_loaded = $entry(@pgu.client.Pgu_geo::showdownIsLoaded());
    }-*/;

    public static boolean isMapsApiLoaded   = false;
    public static boolean isChartsApiLoaded = false;
    public static boolean isShowdownLoaded  = false;

    public static void mapsApiIsLoaded() {
        initMapsApiVar();
        isMapsApiLoaded = true;
        static_self.mvp.eventBus.fireEvent(new MapsApiLoadedEvent());
    }

    public static void chartsApiIsLoaded() {
        isChartsApiLoaded = true;
        static_self.mvp.eventBus.fireEvent(new ChartsApiLoadedEvent());
    }

    public static void showdownIsLoaded() {
        initShowdownVar();
        isShowdownLoaded = true;
        static_self.mvp.eventBus.fireEvent(new ShowdownLoadedEvent());
    }

    public static native void initMapsApiVar() /*-{
        var google = $wnd.google;

        $wnd.pgu_geo.google = google;
        $wnd.pgu_geo.geocoder = new google.maps.Geocoder();
    }-*/;

    public static native void initShowdownVar() /*-{
        $wnd.pgu_geo.showdown_converter = new $wnd.Showdown.converter();
    }-*/;

    public void buildPublicMvp() {
        final PublicClientFactoryImpl publicClientFactory = new PublicClientFactoryImpl();
        mvp.clientFactory = publicClientFactory;
        mvp.activityMapper = new PublicActivityMapper(publicClientFactory);
        mvp.eventBus = mvp.clientFactory.getEventBus();
        mvp.placeController = mvp.clientFactory.getPlaceController();
    }

    // //////////////////////////
    // //////////////////////////
    // //////////////////////////

    private native void execAfterLoadingModule() /*-{
        $wnd.pgu_geo_after_loading_module();
    }-*/;

    private native void execAfterLoadingPublicModule() /*-{
        $wnd.pgu_geo_after_loading_public_module();
    }-*/;

    private native void console(String msg) /*-{
        $wnd.console.log(msg);
    }-*/;

    @Override
    public void onModuleLoad() {

        initPguGeoContext();
        static_self = this;
        ResourcesApp.INSTANCE.css().ensureInjected();

        final boolean isPublic = History.getToken().startsWith("!public");

        if (isPublic) {
            loadPublicApp();

        } else {
            // else load and wait for linkedin api
            exportLinkedinHandlers();
            execAfterLoadingModule();
        }

        //        initJSContext(isPublic); // done for public
        //        exportLinkedinHandlers(); // done for public
        //        initMVPContext(isPublic); // done for public
        //        ChartsUtils.setEventBus(eventBus); // obsolete
    }

    private void loadPublicApp() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                //
                // define callback actions on loaded apis
                //
                // map api
                exportPublicCallbackOnLoadMapApi();
                // charts api
                exportPublicCallbackOnLoadChartsApi();
                // showdown api
                exportPublicCallbackOnLoadShowdown();
                //
                // start public app
                //
                final PublicClientFactoryImpl pClientFactory = new PublicClientFactoryImpl();
                final EventBus pEventBus = pClientFactory.getEventBus();
                final PlaceController pPlaceController = pClientFactory.getPlaceController();
                final ActivityMapper pActivityMapper = new PublicActivityMapper(pClientFactory);

                final PublicMenuActivity pMenuActivity = new PublicMenuActivity(pClientFactory);
                pMenuActivity.start(pEventBus);
                final PublicMenuView pMenuView = pClientFactory.getPublicMenuView();

                final Place pPlace = pPlaceController.getWhere();
                final AppView appView = pClientFactory.getAppView();

                final AppActivity pAppActivity = new AppActivity(pMenuView, pClientFactory);
                pAppActivity.start(pEventBus);

                final ActivityManager pActivityManager = new ActivityManager(pActivityMapper, pEventBus);
                pActivityManager.setDisplay(appView);

                final PublicPlaceHistoryMapper pHistoryMapper = GWT.create(PublicPlaceHistoryMapper.class);
                final PlaceHistoryHandler pHistoryHandler = new PlaceHistoryHandler(pHistoryMapper);
                pHistoryHandler.register(pPlaceController, pEventBus, pPlace);

                RootPanel.get().add(appView);
                pHistoryHandler.handleCurrentHistory();

                mvp.eventBus = pEventBus;

                //
                // fire the load of other apis
                //
                execAfterLoadingPublicModule();
            }

            @Override
            public void onFailure(final Throwable reason) {
                logFailure(reason);
            }

        });
    }

    private void logFailure(final Throwable reason) {
        final String message = "!!! problem: " + reason.getMessage();
        GWT.log(message);
        console(message);
    }

    public static void goToSignin() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                //
                // start signin app
                //
                final SimplePanel panel = new SimplePanel();
                RootPanel.get().add(panel);

                final SigninView signinView = new SigninViewImpl();
                final SigninActivity activity = new SigninActivity(signinView);

                final EventBus eventBus = new SimpleEventBus();
                activity.start(panel, eventBus);
            }

            @Override
            public void onFailure(final Throwable reason) {
                static_self.logFailure(reason);
            }
        });
    }

    public static void loadView() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {

                final MVPContext mvpContext = static_self.mvp;
                final EventBus eventBus = mvpContext.eventBus;
                final PlaceController placeController = mvpContext.placeController;

                final ClientFactory clientFactory = (ClientFactory) mvpContext.clientFactory;

                final MenuActivity menuActivity = new MenuActivity(clientFactory, placeController);
                menuActivity.start(eventBus);
                final MenuView menuView = clientFactory.getMenuView();

                final Place defaultPlace = new ProfilePlace();

                static_self.startApplication(menuView, defaultPlace);

                clientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                        new AsyncCallbackApp<LoginInfo>(eventBus) {

                    @Override
                    public void onSuccess(final LoginInfo loginInfo) {
                        eventBus.fireEvent(new FetchLoginInfoEvent(loginInfo));
                    }

                });
            }

            @Override
            public void onFailure(final Throwable reason) {
                GWT.log("!!! problem: " + reason.getMessage());
            }
        });

    }

    private void startApplication(final IsWidget menuView, final Place defaultPlace) {

        final BaseClientFactory clientFactory = mvp.clientFactory;
        final EventBus eventBus = mvp.eventBus;
        final PlaceController placeController = mvp.placeController;
        final ActivityMapper activityMapper = mvp.activityMapper;

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
        private ActivityMapper    activityMapper;
        private BaseClientFactory clientFactory;
        private EventBus          eventBus;
        private PlaceController   placeController;
    }

}
