package pgu.client;

import pgu.client.app.AppActivity;
import pgu.client.app.AppView;
import pgu.client.app.event.GoToProfileEvent;
import pgu.client.app.mvp.AppActivityMapper;
import pgu.client.app.mvp.AppPlaceHistoryMapper;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.profile.ProfilePlace;
import pgu.shared.dto.LoginInfo;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

public class Pgu_contacts implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final ClientFactory clientFactory = GWT.create(ClientFactory.class);
        final EventBus eventBus = clientFactory.getEventBus();
        final PlaceController placeController = clientFactory.getPlaceController();

        clientFactory.getLoginService().getLoginInfo(GWT.getHostPageBaseURL(),
                new AsyncCallbackApp<LoginInfo>(eventBus) {

                    @Override
                    public void onSuccess(final LoginInfo loginInfo) {
                        clientFactory.setLoginInfo(loginInfo);

                        final AppView appView = clientFactory.getAppView();
                        final AppActivity appActivity = new AppActivity(placeController, clientFactory);
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

                        eventBus.fireEvent(new GoToProfileEvent());
                    }

                });
    }

}
