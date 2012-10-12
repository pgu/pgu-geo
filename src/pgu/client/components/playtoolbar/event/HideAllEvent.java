package pgu.client.components.playtoolbar.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class HideAllEvent extends GwtEvent<HideAllEvent.Handler> implements HasSelectedItemType {

    public interface HasHideAllHandlers extends HasHandlers {
        HandlerRegistration addHideAllHandler(HideAllEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onHideAll(HideAllEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String selectedItemType;

    public HideAllEvent(final String selectedItemType) {
        this.selectedItemType = selectedItemType;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onHideAll(this);
    }

    @Override
    public String getSelectedItemType() {
        return selectedItemType;
    }

}
