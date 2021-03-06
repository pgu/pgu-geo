package pgu.client.components.playtoolbar;

import java.util.ArrayList;
import java.util.Arrays;

import pgu.client.components.playtoolbar.event.BwdEvent;
import pgu.client.components.playtoolbar.event.FwdEvent;
import pgu.client.components.playtoolbar.event.HideAllEvent;
import pgu.client.components.playtoolbar.event.PauseEvent;
import pgu.client.components.playtoolbar.event.PlayEvent;
import pgu.client.components.playtoolbar.event.ShowAllEvent;
import pgu.client.components.playtoolbar.event.StartPlayingEvent;
import pgu.client.components.playtoolbar.event.StopEvent;
import pgu.client.pub.ui.PublicViewProfileItems;
import pgu.shared.utils.ItemType;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class PlayToolbar extends Composite implements
BwdEvent.HasBwdHandlers //
, PauseEvent.HasPauseHandlers //
, StopEvent.HasStopHandlers //
, PlayEvent.HasPlayHandlers //
, FwdEvent.HasFwdHandlers //
, ShowAllEvent.HasShowAllHandlers //
, HideAllEvent.HasHideAllHandlers //
, StartPlayingEvent.HasStartPlayingHandlers //
{

    private static final String FROM_PAST_TO_PRESENT = "From past to present";
    private static final String FROM_PRESENT_TO_PAST = "From present to past";

    private static PlayToolbarUiBinder uiBinder = GWT.create(PlayToolbarUiBinder.class);

    interface PlayToolbarUiBinder extends UiBinder<Widget, PlayToolbar> {
    }

    @UiField
    ListBox                         itemsBox, nbSecondsBox;
    @UiField
    Label playDirection;

    @UiField
    Button                         //
    past2prstBtn,
    prst2pastBtn //
    , bwdBtn, fwdBtn //
    , stopBtn, playBtn, pauseBtn //
    , showAllBtn //
    ;

    private boolean                 isPast2Prst = true;
    private int                     token       = 0;
    private boolean                 isPlaying   = false;
    private boolean isToggled = false;

    private final ArrayList<Button> allControls = new ArrayList<Button>();

    private final PublicViewProfileItems viewProfileItems = new PublicViewProfileItems();

    public PlayToolbar() {
        initWidget(uiBinder.createAndBindUi(this));

        playDirection.setText(FROM_PAST_TO_PRESENT);

        // TODO PGU May 13, 2013 review this filter with the items blocks
        itemsBox.setVisible(false);

        itemsBox.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(final ChangeEvent event) {
                final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());
                setSelectedProfileItems(selectedItemType);

                stop();
            }
        });

        playBtn.getElement().setId("pgu_geo_public_play_btn");

        allControls.add(bwdBtn);
        allControls.add(stopBtn);
        allControls.add(playBtn);
        allControls.add(pauseBtn);
        allControls.add(fwdBtn);

        visibles(playBtn, fwdBtn);
        initToken();
        isPlaying = false;

        itemsBox.setEnabled(true);
        showAllBtn.setEnabled(true);
        isToggled = false;

        nbSecondsBox.addItem("2");
        nbSecondsBox.addItem("5");
        nbSecondsBox.addItem("10");
        nbSecondsBox.addItem("30");
        nbSecondsBox.setSelectedIndex(0);

        new Timer() {

            @Override
            public void run() {
                showPlayBtnToolTip();
            }
        }.schedule(3000);
    }

    private native void showPlayBtnToolTip() /*-{
        $wnd.$('#pgu_geo_public_play_btn').tooltip('show');
    }-*/;

    @UiHandler("showAllBtn")
    public void clickOnShowAll(final ClickEvent e) {
        isToggled = !isToggled;

        itemsBox.setEnabled(!isToggled);
        playBtn.setEnabled(!isToggled);
        fwdBtn.setEnabled(!isToggled);

        fireShowAllAction();
    }

    private void fireShowAllAction() {
        final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());

        // we can not rely on showAllBtn.isToggled() as we get here before it sets the css 'active'
        if (isToggled) {
            fireEvent(new ShowAllEvent(selectedItemType));

        } else {
            fireEvent(new HideAllEvent(selectedItemType));

        }
    }

    private int nbItems() {
        return viewProfileItems.nbSelectedItems();
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
            playDirection.setText(FROM_PRESENT_TO_PAST);
            reverseToken();
        }
    }

    @UiHandler("past2prstBtn")
    public void clickOnPast2Prst(final ClickEvent e) {
        if (!isPast2Prst) {
            playDirection.setText(FROM_PAST_TO_PRESENT);
            reverseToken();
        }
    }

    private void incToken() {
        token += isPast2Prst ? 1 : -1;
    }

    private void decToken() {
        token -= isPast2Prst ? 1 : -1;
    }

    private void stop() {

        stopPlayTimer();

        visibles(playBtn, fwdBtn);

        // currents.add(summary);
        // currents.add(userGeo);

        initToken();
        fireEvent(new StopEvent());

        isPlaying = false;

        showAllBtn.setEnabled(true);
        itemsBox.setEnabled(!isToggled);

        fireShowAllAction(); // restore the state of the show all
    }

    private void visibles(final Button... widgets) {
        enables(new ArrayList<Button>(Arrays.asList(widgets)));
    }

    private void enables(final ArrayList<Button> enables) {
        final ArrayList<Button> disableds = new ArrayList<Button>(allControls);

        disableds.removeAll(enables);

        for (final Button btn : enables) {
            btn.setEnabled(true);
        }

        for (final Button btn : disableds) {
            btn.setEnabled(false);
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

    private void play(final boolean isFwd, final ScheduledCommand eventToFire) {

        if (isFwd) {
            incToken();

        } else {
            decToken();

        }

        if (isTokenOutOfBounds()) {
            stop();
            return;
        }

        final ArrayList<Button> enableds = new ArrayList<Button>();

        if (isAfterFirstToken()) {
            enableds.add(bwdBtn);
        }

        enableds.add(stopBtn);

        if (isPlaying) {
            enableds.add(pauseBtn);
        } else {
            enableds.add(playBtn);
        }

        if (isBeforeLastToken()) {
            enableds.add(fwdBtn);
        }

        // final String location = locations[token];
        // currents.add(loc2desc.get(location));
        // currents.add(loc2geo.get(location));

        enables(enableds);

        if (isPlaying) {
            resetPlayTimer();
        }

        eventToFire.execute();
    }

    private void pause() {
        pauseBtn.setEnabled(false);
        playBtn.setEnabled(true);
    }

    private Timer playTimer = null;

    @UiHandler("playBtn")
    public void clickOnPlay(final ClickEvent e) {

        if (!isPlaying) {

            showAllBtn.setEnabled(false);
            itemsBox.setEnabled(false);

            final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());

            fireEvent(new StartPlayingEvent(selectedItemType));

            isPlaying = true;
        }

        play(true, new ScheduledCommand() {

            @Override
            public void execute() {
                fireEvent(new PlayEvent(token));
            }

        });
    }

    private void resetPlayTimer() {

        stopPlayTimer();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                playTimer = new Timer() {

                    @Override
                    public void run() {
                        clickOnPlay(null);
                    }
                };

                final Integer nbSeconds = Integer.valueOf(nbSecondsBox.getValue(nbSecondsBox.getSelectedIndex()));
                playTimer.schedule(nbSeconds * 1000);

            }
        });
    }

    @UiHandler("stopBtn")
    public void clickOnStop(final ClickEvent e) {
        stop();
    }

    private void stopPlayTimer() {

        if (playTimer != null) {
            playTimer.cancel();
            playTimer = null;
        }
    }

    @UiHandler("pauseBtn")
    public void clickOnPause(final ClickEvent e) {

        stopPlayTimer();

        pause();

        fireEvent(new PauseEvent());
    }

    @UiHandler("fwdBtn")
    public void clickOnFwd(final ClickEvent e) {

        if (!isPlaying) {
            if (showAllBtn.isEnabled()) {

                showAllBtn.setEnabled(false);
                itemsBox.setEnabled(false);

                final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());
                fireEvent(new HideAllEvent(selectedItemType));
            }
        }

        play(true, new ScheduledCommand() {

            @Override
            public void execute() {
                fireEvent(new FwdEvent(token));
            }
        });
    }

    @UiHandler("bwdBtn")
    public void clickOnBwd(final ClickEvent e) {

        if (!isPlaying) {
            if (showAllBtn.isEnabled()) {

                showAllBtn.setEnabled(false);
                itemsBox.setEnabled(false);

                final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());
                fireEvent(new HideAllEvent(selectedItemType));
            }
        }

        play(false, new ScheduledCommand() {

            @Override
            public void execute() {
                fireEvent(new BwdEvent(token));
            }
        });
    }

    @Override
    public HandlerRegistration addStopHandler(final StopEvent.Handler handler) {
        return addHandler(handler, StopEvent.TYPE);
    }

    @Override
    public HandlerRegistration addPlayHandler(final PlayEvent.Handler handler) {
        return addHandler(handler, PlayEvent.TYPE);
    }

    @Override
    public HandlerRegistration addFwdHandler(final FwdEvent.Handler handler) {
        return addHandler(handler, FwdEvent.TYPE);
    }

    @Override
    public HandlerRegistration addPauseHandler(final PauseEvent.Handler handler) {
        return addHandler(handler, PauseEvent.TYPE);
    }

    @Override
    public HandlerRegistration addBwdHandler(final BwdEvent.Handler handler) {
        return addHandler(handler, BwdEvent.TYPE);
    }

    @Override
    public HandlerRegistration addHideAllHandler(final HideAllEvent.Handler handler) {
        return addHandler(handler, HideAllEvent.TYPE);
    }

    @Override
    public HandlerRegistration addShowAllHandler(final ShowAllEvent.Handler handler) {
        return addHandler(handler, ShowAllEvent.TYPE);
    }

    @Override
    public HandlerRegistration addStartPlayingHandler(final StartPlayingEvent.Handler handler) {
        return addHandler(handler, StartPlayingEvent.TYPE);
    }

    public void addProfileItems() {

        if (viewProfileItems.hasAllOption()) {
            itemsBox.addItem("All", "all");
        }
        if (viewProfileItems.hasExperienceOption()) {
            itemsBox.addItem("Experience", ItemType.experience);
        }
        if (viewProfileItems.hasEducationOption()) {
            itemsBox.addItem("Education", ItemType.education);
        }

        if (itemsBox.getItemCount() > 0) {
            itemsBox.setSelectedIndex(0);

            final String selectedItemType = itemsBox.getValue(itemsBox.getSelectedIndex());
            setSelectedProfileItems(selectedItemType);
        }
    }

    private void setSelectedProfileItems(final String selectedItemType) {
        viewProfileItems.setSelectedProfileItems(selectedItemType);
    }

    public boolean hasItemsToDisplay() {
        return itemsBox.getItemCount() > 0;
    }

}
