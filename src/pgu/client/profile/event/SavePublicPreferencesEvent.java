package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SavePublicPreferencesEvent extends GwtEvent<SavePublicPreferencesEvent.Handler> {

    public interface HasSavePublicPreferencesHandlers extends HasHandlers {
        HandlerRegistration addSavePublicPreferencesHandler(SavePublicPreferencesEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSavePublicPreferences(SavePublicPreferencesEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String type;

    public SavePublicPreferencesEvent(final String type) {
        this.type = type;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSavePublicPreferences(this);
    }

    public String getType() {
        return type;
    }

}
