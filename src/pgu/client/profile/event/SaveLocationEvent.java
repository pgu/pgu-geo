package pgu.client.profile.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveLocationEvent extends GwtEvent<SaveLocationEvent.Handler> {

    public interface HasSaveLocationHandlers extends HasHandlers {
        HandlerRegistration addSaveLocationHandler(SaveLocationEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveLocation(SaveLocationEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String locationName;
    private final String lat;
    private final String lng;

    public SaveLocationEvent(final String locationName, final String lat, final String lng) {
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveLocation(this);
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

}
