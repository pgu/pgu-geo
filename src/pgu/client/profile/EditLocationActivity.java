package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Notification;
import pgu.shared.dto.ItemLocation;

import com.github.gwtbootstrap.client.ui.event.HiddenEvent;
import com.github.gwtbootstrap.client.ui.event.HiddenHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EditLocationActivity {

    private final EditLocationView               view;
    private final EventBus                       eventBus;
    private final ClientUtils                    u           = new ClientUtils();
    private final ArrayList<HandlerRegistration> handlerRegs = new ArrayList<HandlerRegistration>();

    public EditLocationActivity(final ClientFactory clientFactory) {
        eventBus = clientFactory.getEventBus();
        view = clientFactory.getEditLocationView();
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
                final ArrayList<ItemLocation> locations = view.getSelectedItemLocations();

                GWT.log("------------");
                for (final ItemLocation itemLocation : locations) {
                    GWT.log("> " + itemLocation);
                }
            }

        });
    }

    public void start(final ItemLocation itemLocation, final String itemId) {

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
