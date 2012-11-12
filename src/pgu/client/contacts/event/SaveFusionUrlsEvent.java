package pgu.client.contacts.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveFusionUrlsEvent extends GwtEvent<SaveFusionUrlsEvent.Handler> {

    public interface HasSaveFusionUrlsHandlers extends HasHandlers {
        HandlerRegistration addSaveFusionUrlsHandler(SaveFusionUrlsEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveFusionUrls(SaveFusionUrlsEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String jsonFusionUrls;

    public SaveFusionUrlsEvent(final String jsonFusionUrls) {
        this.jsonFusionUrls = jsonFusionUrls;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveFusionUrls(this);
    }

    public String getFusionUrls() {
        return jsonFusionUrls;
    }

}
