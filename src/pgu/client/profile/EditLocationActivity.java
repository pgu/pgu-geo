package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.Notification;
import pgu.client.profile.ui.EditLocationViewHelper;
import pgu.client.service.LinkedinServiceAsync;

import com.github.gwtbootstrap.client.ui.event.HiddenEvent;
import com.github.gwtbootstrap.client.ui.event.HiddenHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EditLocationActivity {

    private final EditLocationView               view;
    private final EventBus                       eventBus;
    private final ClientUtils                    u              = new ClientUtils();
    private final ArrayList<HandlerRegistration> handlerRegs    = new ArrayList<HandlerRegistration>();
    private final LinkedinServiceAsync           linkedinService;
    private final ClientFactory                  clientFactory;
    private Timer                                timerCloseView = null;

    private final EditLocationViewHelper             viewHelper = new EditLocationViewHelper();

    public EditLocationActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        eventBus = clientFactory.getEventBus();
        view = clientFactory.getEditLocationView();
        linkedinService = clientFactory.getLinkedinService();

        handlerRegs.add(addCloseHandler());
    }

    private HandlerRegistration addAddHandler(final String itemConfigId) {
        return view.getAddHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                u.fire(eventBus, new LocationAddNewEvent(itemConfigId));
            }
        });
    }

    private HandlerRegistration addCloseHandler() {
        return view.getCloseHandler().addHiddenHandler(new HiddenHandler() {

            @Override
            public void onHidden(final HiddenEvent hiddenEvent) {
                view.getCloseWidget().setVisible(false);

                for (final Notification notif : view.getNotifications()) {
                    if (notif != null) {
                        notif.removeFromParent();
                    }
                }

                for (HandlerRegistration handlerReg : handlerRegs) {
                    handlerReg.removeHandler();
                    handlerReg = null;
                }
                handlerRegs.clear();

                if (timerCloseView != null) {
                    timerCloseView.cancel();
                }
            }

        });
    }

    private HandlerRegistration addSaveHandler(final String itemConfigId) {
        return view.getSaveWidget().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                final ArrayList<String> selectedLocations = view.getSelectedLocations();
                if (selectedLocations.isEmpty()) {
                    return;
                }

                view.getWaitingIndicator().setVisible(true);
                view.disableCreationForm();

                LocationsUtils.copyLocationCaches();
                for (final String locationName: selectedLocations) {
                    LocationsUtils.addLocation2ItemInCopyCache(itemConfigId, locationName);
                }

                linkedinService.saveLocations( //
                        //
                        clientFactory.getAppState().getUserId() //
                        , LocationsUtils.json_copyCacheItems() //
                        , LocationsUtils.json_copyCacheReferential() //
                        //
                        , new AsyncCallbackApp<Void>(eventBus) {

                            @Override
                            public void onSuccess(final Void result) {

                                LocationsUtils.replaceCachesByCopies();

                                view.getWaitingIndicator().setVisible(false);
                                view.removeCreationFormAndShowClose();

                                u.fire(eventBus, new LocationsSuccessSaveEvent(itemConfigId));

                                final StringBuilder msg = getSuccessMessage(selectedLocations);
                                u.showNotificationSuccess(msg, view);

                                hideViewWithDelay();
                            }

                            private StringBuilder getSuccessMessage(final ArrayList<String> selectedLocations) {
                                final StringBuilder msg = new StringBuilder();

                                if (selectedLocations.size() == 1) {
                                    msg.append("The location \"");
                                    msg.append(selectedLocations.get(0));
                                    msg.append("\" has been successfully added.");

                                } else {
                                    msg.append("The locations <ul>");
                                    for (final String loc : selectedLocations) {
                                        msg.append("<li>\"");
                                        msg.append(loc);
                                        msg.append("\"</li>");
                                    }

                                    msg.append("</ul>have been successfully added.");
                                }
                                return msg;
                            }

                            @Override
                            public void onFailure(final Throwable caught) {

                                LocationsUtils.deleteCopies();

                                view.getWaitingIndicator().setVisible(false);
                                view.resetCreationForm();

                                u.showNotificationError(caught, view);

                                super.onFailure(caught);

                            }

                        });
            }

        });
    }

    public void start(
            final String itemConfigId
            , final String locName
            ) {

        final boolean isNew = u.isVoid(locName);

        if (isNew) {

            handlerRegs.add(addSaveHandler(itemConfigId));
            handlerRegs.add(addAddHandler(itemConfigId));
            view.displayNewLocationWidget(itemConfigId);

        } else {

            final boolean isFromLinkedin = viewHelper.isLocationFromLinkedin(itemConfigId, locName);

            if (!isFromLinkedin) {
                handlerRegs.add(addDeleteHandler(itemConfigId, locName));
            }

            handlerRegs.add(addShowOnMapHandler(locName));
            view.displayEditLocationWidget(locName, isFromLinkedin);
        }
        view.show();
    }

    private HandlerRegistration addShowOnMapHandler(final String locName) {
        return view.getShowOnMapHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                u.fire(eventBus, new LocationShowOnMapEvent(locName));
                view.hide();
            }

        });
    }

    private HandlerRegistration addDeleteHandler(final String itemConfigId, final String locationName) {
        return view.getDeleteHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                view.getWaitingIndicator().setVisible(true);
                view.disableEditionForm();

                LocationsUtils.copyLocationCaches();
                viewHelper.removeLocationFromCopyCaches(itemConfigId, locationName);

                linkedinService.saveLocations( //
                        //
                        clientFactory.getAppState().getUserId() //
                        , LocationsUtils.json_copyCacheItems() //
                        , LocationsUtils.json_copyCacheReferential() //
                        //
                        , new AsyncCallbackApp<Void>(eventBus) {

                            @Override
                            public void onSuccess(final Void result) {

                                LocationsUtils.replaceCachesByCopies();

                                view.getWaitingIndicator().setVisible(false);
                                view.removeEditionFormAndShowClose();

                                u.fire(eventBus, new LocationSuccessDeleteEvent(itemConfigId));

                                final StringBuilder msg = getSuccessMessage(locationName);
                                u.showNotificationSuccess(msg, view);

                                hideViewWithDelay();
                            }

                            private StringBuilder getSuccessMessage(final String locationName) {
                                final StringBuilder msg = new StringBuilder();
                                msg.append("The location \"");
                                msg.append(locationName);
                                msg.append("\" has been successfully removed.");
                                return msg;
                            }

                            @Override
                            public void onFailure(final Throwable caught) {

                                LocationsUtils.deleteCopies();

                                view.getWaitingIndicator().setVisible(false);
                                view.enableEditionForm();

                                u.showNotificationError(caught, view);

                                super.onFailure(caught);

                            }

                        });
            }

        });
    }

    private void hideViewWithDelay() {
        timerCloseView = new Timer() {

            @Override
            public void run() {
                view.hide();

            }

        };
        timerCloseView.schedule(3000);
    }

}
