package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Notification;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.ItemLocation;

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

    public EditLocationActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        eventBus = clientFactory.getEventBus();
        view = clientFactory.getEditLocationView();
        linkedinService = clientFactory.getLinkedinService();

        handlerRegs.add(addCloseHandler());
    }

    private HandlerRegistration addAddHandler(final String itemId) {
        return view.getAddHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                u.fire(eventBus, new LocationAddNewEvent(itemId));
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

    private HandlerRegistration addSaveHandler(final String itemId) {
        return view.getSaveWidget().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                final ArrayList<ItemLocation> selectedLocations = view.getSelectedLocations();
                if (selectedLocations.isEmpty()) {
                    return;
                }

                view.getWaitingIndicator().setVisible(true);
                view.disableCreationForm();

                linkedinService.saveLocations( //
                        clientFactory.getAppState().getUserId() //
                        , u.getCopyCacheWithNewLocationsJson(itemId, selectedLocations) //
                        , new AsyncCallbackApp<Void>(eventBus) {

                            @Override
                            public void onSuccess(final Void result) {

                                view.getWaitingIndicator().setVisible(false);
                                view.removeCreationFormAndShowClose(itemId);

                                u.fire(eventBus, new LocationsSuccessSaveEvent(itemId, selectedLocations));

                                final StringBuilder msg = getSuccessMessage(selectedLocations);
                                u.showNotificationSuccess(msg, view);

                                hideViewWithDelay();
                            }

                            private StringBuilder getSuccessMessage(final ArrayList<ItemLocation> selectedLocations) {
                                final StringBuilder msg = new StringBuilder();

                                if (selectedLocations.size() == 1) {
                                    msg.append("The location \"");
                                    msg.append(selectedLocations.get(0).getName());
                                    msg.append("\" has been successfully added.");

                                } else {
                                    msg.append("The locations <ul>");
                                    for (final ItemLocation loc : selectedLocations) {
                                        msg.append("<li>\"");
                                        msg.append(loc.getName());
                                        msg.append("\"</li>");
                                    }

                                    msg.append("</ul>have been successfully added.");
                                }
                                return msg;
                            }

                            @Override
                            public void onFailure(final Throwable caught) {
                                view.getWaitingIndicator().setVisible(false);
                                view.resetCreationForm();

                                u.showNotificationError(caught, view);

                                super.onFailure(caught);

                            }

                        });
            }

        });
    }

    public void start(final ItemLocation itemLocation, final String itemId) {

        final boolean isNew = itemLocation == null;

        if (isNew) {

            handlerRegs.add(addSaveHandler(itemId));
            handlerRegs.add(addAddHandler(itemId));
            view.displayNewLocationWidget(itemId);

        } else {

            handlerRegs.add(addDeleteHandler(itemLocation, itemId));
            handlerRegs.add(addShowOnMapHandler(itemLocation));
            view.displayEditLocationWidget(itemLocation, itemId);
        }
        view.show();
    }

    private HandlerRegistration addShowOnMapHandler(final ItemLocation itemLocation) {
        return view.getShowOnMapHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                u.fire(eventBus, new LocationShowOnMapEvent(itemLocation));
                view.hide();
            }

        });
    }

    private HandlerRegistration addDeleteHandler(final ItemLocation itemLocation, final String itemId) {
        return view.getDeleteHandler().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                view.getWaitingIndicator().setVisible(true);
                view.disableEditionForm();

                linkedinService.saveLocations( //
                        clientFactory.getAppState().getUserId() //
                        , u.getCopyCacheWithoutLocationJson(itemId, itemLocation) //
                        , new AsyncCallbackApp<Void>(eventBus) {

                            @Override
                            public void onSuccess(final Void result) {

                                view.getWaitingIndicator().setVisible(false);
                                view.removeEditionFormAndShowClose();

                                u.fire(eventBus, new LocationSuccessDeleteEvent(itemId, itemLocation));

                                final StringBuilder msg = getSuccessMessage(itemLocation);
                                u.showNotificationSuccess(msg, view);

                                hideViewWithDelay();
                            }

                            private StringBuilder getSuccessMessage(final ItemLocation itemLocation) {
                                final StringBuilder msg = new StringBuilder();
                                msg.append("The location \"");
                                msg.append(itemLocation.getName());
                                msg.append("\" has been successfully removed.");
                                return msg;
                            }

                            @Override
                            public void onFailure(final Throwable caught) {
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
