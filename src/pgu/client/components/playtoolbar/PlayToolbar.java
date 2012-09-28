package pgu.client.components.playtoolbar;

import java.util.ArrayList;
import java.util.Arrays;

import pgu.client.app.utils.ProfileItemsUtils;
import pgu.client.components.playtoolbar.event.BwdEvent;
import pgu.client.components.playtoolbar.event.FwdEvent;
import pgu.client.components.playtoolbar.event.PauseEvent;
import pgu.client.components.playtoolbar.event.PlayEvent;
import pgu.client.components.playtoolbar.event.PlayEvent.HasPlayHandlers;
import pgu.client.components.playtoolbar.event.StopEvent;
import pgu.client.components.playtoolbar.event.StopEvent.HasStopHandlers;
import pgu.shared.utils.ItemType;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
    ListBox                         items;

    @UiField
    Button                         //
    past2prstBtn,
    prst2pastBtn //
    , bwdBtn, fwdBtn //
    , stopBtn, playBtn, pauseBtn //
    ;

    private boolean                 isPast2Prst = true;
    private int                     token       = 0;
    private boolean                 isPlaying   = false;

    private final ArrayList<Widget> allControls = new ArrayList<Widget>();

    public PlayToolbar() {
        initWidget(uiBinder.createAndBindUi(this));

        items.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(final ChangeEvent event) {
                final String selectedItemType = items.getValue(items.getSelectedIndex());
                setSelectedProfileItems(selectedItemType);

                stop();
            }
        });

        allControls.add(bwdBtn);
        allControls.add(stopBtn);
        allControls.add(playBtn);
        allControls.add(pauseBtn);
        allControls.add(fwdBtn);

        stop();

        setVisible(false);
    }

    private int nbItems() {
        return ProfileItemsUtils.nbSelectedItems();
    }

    private void initToken() {
        token = isPast2Prst ? -1 : nbItems();
    }

    private boolean isTokenOutOfBounds() {
        return token >= nbItems() || token < 0;
    }

    private void reverseToken() {

        isPast2Prst = !isPast2Prst;

        if (isTokenOutOfBounds()) {
            initToken();
        }
    }

    @UiHandler("prst2pastBtn")
    public void clickOnPrst2Past(final ClickEvent e) {
        if (isPast2Prst) {
            reverseToken();
        }
    }

    @UiHandler("past2prstBtn")
    public void clickOnPast2Prst(final ClickEvent e) {
        if (!isPast2Prst) {
            reverseToken();
        }
    }

    private void play() {
        isPlaying = true;
        play(true);
    }

    private void incToken() {
        token += isPast2Prst ? 1 : -1;
    }

    private void decToken() {
        token -= isPast2Prst ? 1 : -1;
    }

    private void stop() {
        isPlaying = false;

        visibles(playBtn, fwdBtn);

        // currents.add(summary);
        // currents.add(userGeo);

        initToken();
    }

    private void visibles(final Widget... widgets) {
        visibles(new ArrayList<Widget>(Arrays.asList(widgets)));
    }

    private void visibles(final ArrayList<Widget> visibles) {
        final ArrayList<Widget> copyAllControls = new ArrayList<Widget>(allControls);

        copyAllControls.removeAll(visibles);

        for (final Widget widget : visibles) {
            widget.setVisible(true);
        }

        for (final Widget widget : copyAllControls) {
            widget.setVisible(false);
        }

    }

    private int firstToken() {
        return isPast2Prst ? 0 : nbItems() - 1;
    }

    private int lastToken() {
        return isPast2Prst ? nbItems() - 1 : 0;
    }

    private boolean isAfterFirstToken() {
        return isPast2Prst ? token > firstToken() : token < firstToken();
    }

    private boolean isBeforeLastToken() {
        return isPast2Prst ? token < lastToken() : token > lastToken();
    }

    private void play(final boolean isFwd) {

        if (isFwd) {
            incToken();

        } else {
            decToken();

        }

        if (isTokenOutOfBounds()) {
            stop();
            return;
        }

        final ArrayList<Widget> visibles = new ArrayList<Widget>();

        if (isAfterFirstToken()) {
            visibles.add(bwdBtn);
        }

        visibles.add(stopBtn);

        if (isPlaying) {
            visibles.add(pauseBtn);
        } else {
            visibles.add(playBtn);
        }

        if (isBeforeLastToken()) {
            visibles.add(fwdBtn);
        }

        // final String location = locations[token];
        // currents.add(loc2desc.get(location));
        // currents.add(loc2geo.get(location));

        visibles(visibles);
    }

    private void pause() {
        pauseBtn.setVisible(false);
        playBtn.setVisible(true);

        // TODO PGU Sep 28, 2012 cancel timer
    }

    private void bwd() {
        play(false);
    }

    private void fwd() {
        play(true);
    }

    @UiHandler("playBtn")
    public void clickOnPlay(final ClickEvent e) {
        play();

        fireEvent(new PlayEvent(token));
    }

    @UiHandler("stopBtn")
    public void clickOnStop(final ClickEvent e) {
        stop();

        fireEvent(new StopEvent());
    }

    @UiHandler("pauseBtn")
    public void clickOnPause(final ClickEvent e) {
        pause();

        fireEvent(new PauseEvent());
    }

    @UiHandler("fwdBtn")
    public void clickOnFwd(final ClickEvent e) {
        fwd();

        fireEvent(new FwdEvent(token));
    }

    @UiHandler("bwdBtn")
    public void clickOnBwd(final ClickEvent e) {
        bwd();

        fireEvent(new BwdEvent(token));
    }

    @Override
    public HandlerRegistration addStopHandler(final StopEvent.Handler handler) {
        return addHandler(handler, StopEvent.TYPE);
    }

    @Override
    public HandlerRegistration addPlayHandler(final PlayEvent.Handler handler) {
        return addHandler(handler, PlayEvent.TYPE);
    }

    public void addProfileItems() {

        if (ProfileItemsUtils.hasAllOption()) {
            items.addItem("All", "all");
        }
        if (ProfileItemsUtils.hasExperienceOption()) {
            items.addItem("Experience", ItemType.experience);
        }
        if (ProfileItemsUtils.hasEducationOption()) {
            items.addItem("Education", ItemType.education);
        }

        if (items.getItemCount() > 0) {
            items.setSelectedIndex(0);
            setVisible(true);

            final String selectedItemType = items.getValue(items.getSelectedIndex());
            setSelectedProfileItems(selectedItemType);
        }
    }

    private void setSelectedProfileItems(final String selectedItemType) {
        ProfileItemsUtils.setSelectedProfileItems(selectedItemType);
    }

}
