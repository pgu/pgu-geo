package pgu.client.app.utils;

import pgu.client.app.event.ChartsApiLoadedEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;


public class ChartsUtils {

    private static EventBus s_eventBus;
    private static boolean isApiLoaded = false;

    public native void exportMethods() /*-{
        $wnd.pgu_geo.init_charts = $entry(@pgu.client.app.utils.ChartsUtils::initCharts());
    }-*/;

    public static void initCharts() {
        if (s_eventBus == null) {

            new Timer() {

                @Override
                public void run() {
                    initCharts();
                }

            }.schedule(300);

        } else {
            GWT.log("fire charts api is ON");
            isApiLoaded = true;
            s_eventBus.fireEvent(new ChartsApiLoadedEvent());
        }

    }

    public static void setEventBus(final EventBus eventBus) {
        s_eventBus = eventBus;
    }

    public static boolean isApiLoaded() {
        return isApiLoaded;
    }

}
