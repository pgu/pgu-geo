package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Notification;
import pgu.client.app.utils.NotificationImpl;
import pgu.client.profile.EditLocationView;
import pgu.shared.dto.ItemLocation;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavPills;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.Widget;

public class EditLocationViewImpl extends Composite implements EditLocationView {

    private static EditLocationViewImplUiBinder uiBinder = GWT.create(EditLocationViewImplUiBinder.class);

    interface EditLocationViewImplUiBinder extends UiBinder<Widget, EditLocationViewImpl> {
    }

    @UiField
    Modal                                 container;
    @UiField
    Button                                saveBtn, addBtn, displayOnMapBtn, deleteBtn, closeBtn;
    @UiField
    ProgressBar                           progressBar;
    @UiField
    HTMLPanel                             notification, editPanel, addPanel;
    @UiField
    NavPills                              otherLocationsContainer;
    @UiField
    HTML                                  locationLatUI, locationLngUI;

    private final ArrayList<Notification> notifications      = new ArrayList<Notification>();
    private final ArrayList<String>       otherItemLocations = new ArrayList<String>();
    private final ArrayList<String>       selecteds          = new ArrayList<String>();
    private final ClientUtils             u                  = new ClientUtils();

    public EditLocationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        closeBtn.setVisible(false);
        progressBar.setVisible(false);
    }

    @UiHandler("closeBtn")
    public void clickClose(final ClickEvent e) {
        container.hide();
    }

    @UiHandler("addBtn")
    public void clickAdd(final ClickEvent e) {
        container.hide();
    }

    @Override
    public HasVisibleHandlers getCloseHandler() {
        return container;
    }

    @Override
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public HasClickHandlers getAddHandler() {
        return addBtn;
    }

    @Override
    public void show() {
        container.show();
    }

    private static final Comparator<String> LEXICO = new Comparator<String>() {

        @Override
        public int compare(final String loc1, final String loc2) {
            return loc1.toLowerCase().compareTo(loc2.toLowerCase());
        }

    };

    private void showOtherExistingItemLocations(final String itemConfigId) {

        retrieveOtherExistingItemLocations(itemConfigId);

        otherLocationsContainer.clear();
        selecteds.clear();

        Collections.sort(otherItemLocations, LEXICO);

        for (final String otherLocationName : otherItemLocations) {

            final NavLink otherLocationWidget = new NavLink();
            otherLocationWidget.setText(otherLocationName);
            otherLocationWidget.getElement().addClassName("locationLi");
            otherLocationsContainer.add(otherLocationWidget);

            otherLocationWidget.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(final ClickEvent event) {

                    if (!otherLocationWidget.isActive()) {

                        selecteds.add(otherLocationName);
                        otherLocationWidget.setActive(true);

                    } else {
                        selecteds.remove(otherLocationName);
                        otherLocationWidget.setActive(false);

                    }
                }
            });

        }
    }

    private void retrieveOtherExistingItemLocations(final String itemConfigId) {
        otherItemLocations.clear();
        getOtherItemLocationsFromCache(itemConfigId, this);
    }

    public native void getOtherItemLocationsFromCache(String item_config_Id, EditLocationViewImpl view) /*-{

		var other_location_names = @pgu.client.app.utils.LocationsUtils::getOtherLocationNames(Ljava/lang/String;)(item_config_Id);

		for ( var key in other_location_names) {
			var other_location_name = other_location_names[key];

			view.@pgu.client.profile.ui.EditLocationViewImpl::addOtherExistingItemLocation(Ljava/lang/String;)(other_location_name);
		}
    }-*/;

    public void addOtherExistingItemLocation(final String locationName) {
        otherItemLocations.add(locationName);
    }

    @Override
    public HasClickHandlers getSaveWidget() {
        return saveBtn;
    }

    @Override
    public void displayNewLocationWidget(final String itemConfigId) {

        container.setTitle("Add locations");

        showOtherExistingItemLocations(itemConfigId);

        // ///////////////
        addPanel.setVisible(true);
        editPanel.setVisible(false);

        addBtn.setVisible(true);
        saveBtn.setVisible(true);
        displayOnMapBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }

    // click on a location: popup with: information: name, lat, lng; actions: show on the map, delete it
    @Override
    public void displayEditLocationWidget(final ItemLocation itemLocation, final String itemId) {
        container.setTitle(itemLocation.getName());

        locationLatUI.setText(itemLocation.getLat());
        locationLngUI.setText(itemLocation.getLng());

        // ///////////////
        addPanel.setVisible(false);
        editPanel.setVisible(true);

        addBtn.setVisible(false);
        saveBtn.setVisible(false);
        displayOnMapBtn.setVisible(true);
        deleteBtn.setVisible(true);
    }

    @Override
    public void hide() {
        container.hide();
    }

    @Override
    public HasVisibility getWaitingIndicator() {
        return progressBar;
    }

    @Override
    public Notification newNotification() {
        final NotificationImpl notif = new NotificationImpl(notification, 3000);
        notifications.add(notif);
        return notif;
    }

    @Override
    public void disableCreationForm() {
        setEnableOnCreationForm(false);
    }

    @Override
    public void resetCreationForm() {
        setEnableOnCreationForm(true);
        for (int i = 0; i < otherLocationsContainer.getWidgetCount(); i++) {
            ((NavLink) otherLocationsContainer.getWidget(i)).setActive(false);
        }

        selecteds.clear();
    }

    private void setEnableOnCreationForm(final boolean isEnabled) {
        saveBtn.setEnabled(isEnabled);
        addBtn.setEnabled(isEnabled);
        for (int i = 0; i < otherLocationsContainer.getWidgetCount(); i++) {
            ((NavLink) otherLocationsContainer.getWidget(i)).setDisabled(!isEnabled);
        }
    }

    @Override
    public ArrayList<String> getSelectedLocations() {
        return selecteds;
    }

    @Override
    public void removeCreationFormAndShowClose() {

        addPanel.setVisible(false);
        saveBtn.setVisible(false);
        addBtn.setVisible(false);
        closeBtn.setVisible(true);

        saveBtn.setEnabled(true);
        addBtn.setEnabled(true);

        otherLocationsContainer.clear();
    }

    @Override
    public HasVisibility getCloseWidget() {
        return closeBtn;
    }

    @Override
    public HasClickHandlers getShowOnMapHandler() {
        return displayOnMapBtn;
    }

    @Override
    public HasClickHandlers getDeleteHandler() {
        return deleteBtn;
    }

    @Override
    public void disableEditionForm() {
        displayOnMapBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    @Override
    public void enableEditionForm() {
        displayOnMapBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

    @Override
    public void removeEditionFormAndShowClose() {
        editPanel.setVisible(false);
        displayOnMapBtn.setVisible(false);
        deleteBtn.setVisible(false);
        closeBtn.setVisible(true);

        displayOnMapBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

}
