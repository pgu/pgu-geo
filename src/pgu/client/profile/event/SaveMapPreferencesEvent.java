package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveMapPreferencesEvent extends GwtEvent<SaveMapPreferencesEvent.Handler> {

    public interface HasSaveMapPreferencesHandlers extends HasHandlers {
        HandlerRegistration addSaveMapPreferencesHandler(SaveMapPreferencesEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveMapPreferences(SaveMapPreferencesEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String mapPreferences;

    public SaveMapPreferencesEvent(final String mapPreferences) {
        this.mapPreferences = mapPreferences;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveMapPreferences(this);
    }

    public String getMapPreferences() {
        return mapPreferences;
    }

}
