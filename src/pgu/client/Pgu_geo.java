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
import pgu.client.app.mvp.ClientFactoryImpl;
import pgu.client.app.ui.AppViewImpl;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.menu.MenuActivity;
import pgu.client.menu.MenuView;
import pgu.client.profile.ProfilePlace;
import pgu.client.pub.PublicActivity;
import pgu.client.pub.PublicMenuActivity;
import pgu.client.pub.PublicMenuView;
import pgu.client.pub.PublicView;
import pgu.client.pub.ui.PublicMenuViewImpl;
import pgu.client.pub.ui.PublicViewImpl;
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

    private native void exportLinkedinHandlers() /*-{
        $wnd.pgu_geo.load_view = $entry(@pgu.client.Pgu_geo::loadView());
        $wnd.pgu_geo.go_to_signin = $entry(@pgu.client.Pgu_geo::goToSignin());
    }-*/;

    private static Pgu_geo static_self = null;

    private native void exportCallbackOnLoadMapsApi() /*-{
        $wnd.pgu_geo.maps_api_is_loaded = $entry(@pgu.client.Pgu_geo::mapsApiIsLoaded());
    }-*/;

    private native void exportCallbackOnLoadChartsApi() /*-{
        $wnd.pgu_geo.charts_api_is_loaded = $entry(@pgu.client.Pgu_geo::chartsApiIsLoaded());
    }-*/;

    private native void exportCallbackOnLoadShowdown() /*-{
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
    }

    private void loadPublicApp() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                //
                // define callback actions on loaded apis
                //
                // map api
                exportCallbackOnLoadMapsApi();
                // charts api
                exportCallbackOnLoadChartsApi();
                // showdown api
                exportCallbackOnLoadShowdown();
                //
                // start public app
                //
                final EventBus eventBus = new SimpleEventBus();

                final SimplePanel panel = new SimplePanel();
                RootPanel.get().add(panel);

                final AppView appView = new AppViewImpl();
                final AppActivity pAppActivity = new AppActivity(appView);
                pAppActivity.start(panel, eventBus);

                final PublicMenuView pMenuView = new PublicMenuViewImpl();
                final PublicMenuActivity pMenuActivity = new PublicMenuActivity(pMenuView);
                pMenuActivity.start(appView.getHeader(), eventBus);

                final PublicView pView = new PublicViewImpl(eventBus);
                final PublicActivity pActivity = new PublicActivity(pView);
                pActivity.start(appView.getBody(), eventBus);

                mvp.eventBus = eventBus;

                // TODO PGU Nov 18, 2012 see how to deal with navigation between several public profiles

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

                //
                // define callback actions on loaded apis
                //
                // map api
                static_self.exportCallbackOnLoadMapsApi();
                // charts api
                static_self.exportCallbackOnLoadChartsApi();
                // showdown api
                static_self.exportCallbackOnLoadShowdown();
                //
                // start public app
                //

                final ClientFactoryImpl clientFactory = new ClientFactoryImpl();
                final EventBus eventBus = clientFactory.getEventBus();
                final PlaceController placeController = clientFactory.getPlaceController();
                final AppActivityMapper activityMapper = new AppActivityMapper(clientFactory);

                // TODO PGU Nov 18, 2012
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

                static_self.mvp.eventBus = eventBus;
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

        appView.getHeader().setWidget(menuView);

        final SimplePanel panel = new SimplePanel();

        final AppActivity appActivity = new AppActivity(appView);
        appActivity.start(panel, eventBus);

        final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appView);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(panel);
        historyHandler.handleCurrentHistory();

    }

    private static class MVPContext {
        private ActivityMapper    activityMapper;
        private BaseClientFactory clientFactory;
        private EventBus          eventBus;
        private PlaceController   placeController;
    }

}