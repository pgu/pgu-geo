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
import pgu.client.resources.ResourcesApp;
import pgu.client.signin.SigninPlace;
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

public class Pgu_geo implements EntryPoint {

    private native void initPguGeoContext() /*-{
		$wnd.pgu_geo = {};
    }-*/;

    private final MVPContext mvpContext = new MVPContext();

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

    private native void exportMethods() /*-{
		$wnd.pgu_geo.is_logged_in = $entry(@pgu.client.Pgu_geo::isLoggedIn());
		$wnd.pgu_geo.is_logged_out = $entry(@pgu.client.Pgu_geo::isLoggedOut());
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

    public static boolean isMapsApiLoaded = false;
    public static boolean isChartsApiLoaded = false;
    public static boolean isShowdownLoaded = false;

    public static void mapsApiIsLoaded() {
        initMapsApiVar();
        isMapsApiLoaded = true;
        static_self.mvpContext.eventBus.fireEvent(new MapsApiLoadedEvent());
    }

    public static void chartsApiIsLoaded() {
        isChartsApiLoaded = true;
        static_self.mvpContext.eventBus.fireEvent(new ChartsApiLoadedEvent());
    }

    public static void showdownIsLoaded() {
        initShowdownVar();
        isShowdownLoaded = true;
        static_self.mvpContext.eventBus.fireEvent(new ShowdownLoadedEvent());
    }

    public static native void initMapsApiVar() /*-{
        var google = $wnd.google;

        $wnd.pgu_geo.google = google;
        $wnd.pgu_geo.geocoder = new google.maps.Geocoder();
    }-*/;

    public static native void initShowdownVar() /*-{
        $wnd.pgu_geo.showdown_converter = new $wnd.Showdown.converter();
    }-*/;

    // //////////////////////////
    // //////////////////////////
    // //////////////////////////

    private native void execAfterLoadingModule() /*-{
        $wnd.pgu_geo_after_loading_module();
    }-*/;

    @Override
    public void onModuleLoad() {

        initPguGeoContext();
        static_self = this;

        final boolean isPublic = History.getToken().startsWith("!public");

        if (isPublic) {
            //
            // define callback actions on loaded apis
            //
            // map api
            exportPublicCallbackOnLoadMapApi();
            // charts api
            exportPublicCallbackOnLoadChartsApi();
            // showdown api
            exportPublicCallbackOnLoadShowdown();

            // fire loading apis
            execAfterLoadingModule();

        } else {

            // else wait for linkedin api
        }


        initJSContext(isPublic);
        exportMethods();

        initMVPContext(isPublic);

        final EventBus eventBus = mvpContext.eventBus;
        final PlaceController placeController = mvpContext.placeController;

        ChartsUtils.setEventBus(eventBus);

        ResourcesApp.INSTANCE.css().ensureInjected();

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
            // waits about the logged user information
        }
    }

    public static void isLoggedIn() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {

                final MVPContext mvpContext = static_self.mvpContext;
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

    public static void isLoggedOut() {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {

                final MVPContext mvpContext = static_self.mvpContext;
                final EventBus eventBus = mvpContext.eventBus;
                final PlaceController placeController = mvpContext.placeController;
                final ActivityMapper activityMapper = mvpContext.activityMapper;

                final Place defaultPlace = new SigninPlace();
                final SimplePanel appWidget = new SimplePanel();

                final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
                activityManager.setDisplay(appWidget);

                final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
                final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
                historyHandler.register(placeController, eventBus, defaultPlace);

                RootPanel.get().add(appWidget);
                historyHandler.handleCurrentHistory();

            }

            @Override
            public void onFailure(final Throwable reason) {
                GWT.log("!!! problem: " + reason.getMessage());
            }
        });
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

        //        loadExternalScripts();
    }

    private native void loadExternalScripts() /*-{
		$wnd.pgu_geo_load_external_scripts();
    }-*/;

    private static class MVPContext {
        private ActivityMapper    activityMapper;
        private BaseClientFactory clientFactory;
        private EventBus          eventBus;
        private PlaceController   placeController;
    }

}
