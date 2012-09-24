package pgu.client.app.mvp;

import pgu.client.app.AppView;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface BaseClientFactory {

    EventBus getEventBus();

    PlaceController getPlaceController();

    AppView getAppView();

}
