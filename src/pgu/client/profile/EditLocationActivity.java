package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.RefreshLocationsEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
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
    private final ClientUtils                    u           = new ClientUtils();
    private final ArrayList<HandlerRegistration> handlerRegs = new ArrayList<HandlerRegistration>();
    private final LinkedinServiceAsync           linkedinService;
    private final ClientFactory                  clientFactory;

    public EditLocationActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        eventBus = clientFactory.getEventBus();
        view = clientFactory.getEditLocationView();
        linkedinService = clientFactory.getLinkedinService();

        handlerRegs.add(addSaveHandler());
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
            }

        });
    }

    private HandlerRegistration addSaveHandler() {
        return view.getSaveWidget().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                final ArrayList<ItemLocation> selectedLocations = view.getSelectedLocations();
                if (selectedLocations.isEmpty()) {
                    return;
                }

                view.getWaitingIndicator().setVisible(true);
                view.disableCreationForm();

                final String allItemsWithAllLocations = view.getAllItemsWithAllLocationsJson(itemId);

                linkedinService.saveLocations( //
                        clientFactory.getAppState().getUserId() //
                        , allItemsWithAllLocations //
                        , new AsyncCallbackApp<Void>(eventBus) {

                            @Override
                            public void onSuccess(final Void result) {
                                view.getWaitingIndicator().setVisible(false);
                                view.removeCreationFormAndCommitNewLocations(itemId);

                                u.fire(eventBus, new RefreshLocationsEvent(itemId));

                                String msg = "";
                                if (selectedLocations.size() == 1) {
                                    msg = "The location \"" //
                                            + selectedLocations.get(0).getName()//
                                            + "\" has been added successfully";

                                } else {
                                    msg = "The locations have been added successfully";

                                }

                                final Notification notification = view.newNotification();
                                notification.setHeading("Success");
                                notification.setHTML(msg);
                                notification.setLevel(Level.SUCCESS);
                                notification.show();

                                new Timer() {

                                    @Override
                                    public void run() {
                                        view.hide();

                                    }

                                }.schedule(3000);
                            }

                            @Override
                            public void onFailure(final Throwable caught) {
                                view.getWaitingIndicator().setVisible(false);
                                view.resetCreationForm();

                                final Notification notification = view.newNotification();
                                notification.setHTML( //
                                        "Oops! Something wrong happened: <br>" //
                                                + caught.getMessage());
                                notification.setHeading("Error");
                                notification.setLevel(Level.ERROR);

                                notification.show();

                                super.onFailure(caught);

                            }

                        });
            }

        });
    }

    private String itemId = null;

    public void start(final ItemLocation itemLocation, final String itemId) {
        this.itemId = itemId;

        final boolean isNew = itemLocation == null;

        if (isNew) {

            handlerRegs.add(addAddHandler(itemId));
            view.showOtherExistingItemLocations(itemId);

            view.getFormTitle().setText("Add locations");
            view.displayNewLocationWidget();

        } else {

            view.getFormTitle().setText(itemLocation.getName());
            view.displayEditLocationWidget();
            // TODO PGU Aug 28, 2012 edit location
            // click on a location: popup with: information: name, lat, lng; actions: show on the map, delete it
        }
        view.show();
    }

}
