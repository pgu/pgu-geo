package pgu.client;

import pgu.client.app.AppActivity;
import pgu.client.app.AppContext;
import pgu.client.app.AppView;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.FetchLoginInfoEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.mvp.AppActivityMapper;
import pgu.client.app.mvp.AppPlaceHistoryMapper;
import pgu.client.app.mvp.ClientFactoryImpl;
import pgu.client.app.ui.AppViewImpl;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
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
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class Pgu_geo implements EntryPoint {

    private native void initPguGeoContext() /*-{
        $wnd.pgu_geo = {};
    }-*/;

    private final ClientUtils u = new ClientUtils();
    private final AppContext ctx = new AppContext();
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

    public static void mapsApiIsLoaded() {
        initMapsApiVar();
        static_self.ctx.isMapsApiLoaded = true;
        static_self.mvp.eventBus.fireEvent(new MapsApiLoadedEvent());
    }

    public static void chartsApiIsLoaded() {
        static_self.ctx.isChartsApiLoaded = true;
        static_self.mvp.eventBus.fireEvent(new ChartsApiLoadedEvent());
    }

    public static void showdownIsLoaded() {
        initShowdownVar();
        static_self.ctx.isShowdownLoaded = true;
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

    private native void execAfterLoadingApp() /*-{
        $wnd.pgu_geo_after_loading_app();
    }-*/;

    private native void execAfterLoadingPublicModule() /*-{
        $wnd.pgu_geo_after_loading_public_module();
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
                final PublicActivity pActivity = new PublicActivity(pView, ctx);
                pActivity.start(appView.getBody(), eventBus);

                mvp.eventBus = eventBus;

                // TODO PGU Nov 18, 2012 see how to deal with navigation between several public profiles
                // TODO PGU Nov 20, 2012 extract the code in separate classes

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
        u.log(message);
        u.console(message);
        u.throwX(message);
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
                // profile json
                static_self.exportCallbackOnLoadProfile();
                // contacts json
                static_self.exportCallbackOnContactsProfile();
                //
                // start public app
                //
                final ClientFactoryImpl aClientFactory = new ClientFactoryImpl();
                final EventBus aEventBus = aClientFactory.getEventBus();
                final PlaceController aPlaceController = aClientFactory.getPlaceController();
                final AppActivityMapper aActivityMapper = new AppActivityMapper(aClientFactory, static_self.ctx);

                final SimplePanel panel = new SimplePanel();
                RootPanel.get().add(panel);

                final AppView appView = aClientFactory.getAppView();
                final AppActivity appActivity = new AppActivity(appView);
                appActivity.start(panel, aEventBus);

                final MenuView aMenuView = aClientFactory.getMenuView();
                final MenuActivity aMenuActivity = new MenuActivity(aMenuView, aClientFactory);
                aMenuActivity.start(appView.getHeader(), aEventBus);

                final Place profilePlace = new ProfilePlace();

                final ActivityManager activityManager = new ActivityManager(aActivityMapper, aEventBus);
                activityManager.setDisplay(panel);

                final AppPlaceHistoryMapper aHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
                final PlaceHistoryHandler aHistoryHandler = new PlaceHistoryHandler(aHistoryMapper);
                aHistoryHandler.register(aPlaceController, aEventBus, profilePlace);

                aHistoryHandler.handleCurrentHistory();

                aClientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                        new AsyncCallbackApp<LoginInfo>(aEventBus) {

                    @Override
                    public void onSuccess(final LoginInfo loginInfo) {
                        aEventBus.fireEvent(new FetchLoginInfoEvent(loginInfo));
                    }

                });

                static_self.mvp.eventBus = aEventBus;
                //
                // fire the load of other apis
                //
                static_self.execAfterLoadingApp();
            }

            @Override
            public void onFailure(final Throwable reason) {
                static_self.logFailure(reason);
            }
        });

    }

    protected void exportCallbackOnContactsProfile() {
        // TODO Auto-generated method stub

    }

    protected void exportCallbackOnLoadProfile() {
        // TODO Auto-generated method stub

    }

    private static class MVPContext {
        private EventBus          eventBus;
    }

}
