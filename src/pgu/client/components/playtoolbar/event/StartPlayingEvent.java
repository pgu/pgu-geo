package pgu.client.components.playtoolbar.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class StartPlayingEvent extends GwtEvent<StartPlayingEvent.Handler> {

    public interface HasStartPlayingHandlers extends HasHandlers {
        HandlerRegistration addStartPlayingHandler(StartPlayingEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onStartPlaying(StartPlayingEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String selectedItemType;
    private final boolean isShowAllOn;

    public StartPlayingEvent(final String selectedItemType, final boolean isShowAllOn) {
        this.selectedItemType = selectedItemType;
        this.isShowAllOn = isShowAllOn;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onStartPlaying(this);
    }

    public String getSelectedItemType() {
        return selectedItemType;
    }

    public boolean isShowAllOn() {
        return isShowAllOn;
    }

}