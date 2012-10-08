package pgu.client.components.playtoolbar;

import java.util.ArrayList;
import java.util.Arrays;

import pgu.client.app.utils.ProfileItemsUtils;
import pgu.client.components.playtoolbar.event.BwdEvent;
import pgu.client.components.playtoolbar.event.FwdEvent;
import pgu.client.components.playtoolbar.event.HideAllEvent;
import pgu.client.components.playtoolbar.event.PauseEvent;
import pgu.client.components.playtoolbar.event.PlayEvent;
import pgu.client.components.playtoolbar.event.ShowAllEvent;
import pgu.client.components.playtoolbar.event.StopEvent;
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
{

    private static final String FROM_PAST_TO_PRESENT = "From past to present";
    private static final String FROM_PRESENT_TO_PAST = "From present to past";

    private static PlayToolbarUiBinder uiBinder = GWT.create(PlayToolbarUiBinder.class);

    interface PlayToolbarUiBinder extends UiBinder<Widget, PlayToolbar> {
    }

    @UiField
    ListBox                         items;
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

    private final ArrayList<Button> allControls = new ArrayList<Button>();

    public PlayToolbar() {
        initWidget(uiBinder.createAndBindUi(this));

        playDirection.setText(FROM_PAST_TO_PRESENT);

        items.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(final ChangeEvent event) {
                final String selectedItemType = items.getValue(items.getSelectedIndex());
                setSelectedProfileItems(selectedItemType);

                clickOnStop(null);
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

    @UiHandler("showAllBtn")
    public void clickOnShowAll(final ClickEvent e) {
        stop();

        if (showAllBtn.isToggled()) {
            fireEvent(new ShowAllEvent());

        } else {
            fireEvent(new HideAllEvent());

        }
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

        isPlaying = false;

        stopPlayTimer();

        visibles(playBtn, fwdBtn);
        showAllBtn.setEnabled(true);
        showAllBtn.removeStyleName("active");

        // currents.add(summary);
        // currents.add(userGeo);

        initToken();
        fireEvent(new StopEvent());
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
        pauseBtn.setVisible(false);
        playBtn.setVisible(true);
    }

    private Timer playTimer = null;

    @UiHandler("playBtn")
    public void clickOnPlay(final ClickEvent e) {

        isPlaying = true;
        showAllBtn.setEnabled(false);
        showAllBtn.removeStyleName("active");

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
                playTimer.schedule(2000);

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

        play(true, new ScheduledCommand() {

            @Override
            public void execute() {
                fireEvent(new FwdEvent(token));
            }
        });
    }

    @UiHandler("bwdBtn")
    public void clickOnBwd(final ClickEvent e) {

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
