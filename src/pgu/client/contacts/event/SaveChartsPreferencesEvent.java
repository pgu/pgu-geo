package pgu.client.contacts.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveChartsPreferencesEvent extends GwtEvent<SaveChartsPreferencesEvent.Handler> {

    public interface HasSaveChartsPreferencesHandlers extends HasHandlers {
        HandlerRegistration addSaveChartsPreferencesHandler(SaveChartsPreferencesEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveChartsPreferences(SaveChartsPreferencesEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String jsonChartTypes;

    public SaveChartsPreferencesEvent(final String jsonChartTypes) {
        this.jsonChartTypes = jsonChartTypes;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveChartsPreferences(this);
    }

    public String getChartTypes() {
        return jsonChartTypes;
    }

}
