package pgu.client.components.playtoolbar;

import pgu.client.components.playtoolbar.event.PlayEvent;
import pgu.client.components.playtoolbar.event.PlayEvent.HasPlayHandlers;
import pgu.client.components.playtoolbar.event.StopEvent;
import pgu.client.components.playtoolbar.event.StopEvent.HasStopHandlers;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class PlayToolbar extends Composite implements HasPlayHandlers, HasStopHandlers {

    private static PlayToolbarUiBinder uiBinder = GWT.create(PlayToolbarUiBinder.class);

    interface PlayToolbarUiBinder extends UiBinder<Widget, PlayToolbar> {
    }

    @UiField
    Button                   //
    past2prstBtn, prst2pastBtn //
    , bwdBtn, fwdBtn //
    , stopBtn, playBtn, pauseBtn //
    ;

    public PlayToolbar() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("playBtn")
    public void clickOnPlay(final ClickEvent e) {
        fireEvent(new PlayEvent());
    }

    @UiHandler("stopBtn")
    public void clickOnStop(final ClickEvent e) {
        fireEvent(new StopEvent());
    }

    @Override
    public HandlerRegistration addStopHandler(final StopEvent.Handler handler) {
        return addHandler(handler, StopEvent.TYPE);
    }

    @Override
    public HandlerRegistration addPlayHandler(final PlayEvent.Handler handler) {
        return addHandler(handler, PlayEvent.TYPE);
    }


}
