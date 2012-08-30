package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

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
    Modal                                             container;
    @UiField
    Button                                            saveBtn, addBtn, displayOnMapBtn, deleteBtn, closeBtn;
    @UiField
    ProgressBar                                       progressBar;
    @UiField
    HTMLPanel                                         notification, editPanel, addPanel;
    @UiField
    NavPills                                          otherLocationsContainer;
    @UiField
    HTML                                              locationLatUI, locationLngUI;

    private final ArrayList<Notification>             notifications      = new ArrayList<Notification>();
    private final ArrayList<ItemLocation>             otherItemLocations = new ArrayList<ItemLocation>();
    private final LinkedHashMap<String, ItemLocation> selecteds          = new LinkedHashMap<String, ItemLocation>();
    private final ClientUtils                         u                  = new ClientUtils();

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

    private static final Comparator<ItemLocation> BY_NAME = new Comparator<ItemLocation>() {

                                                              @Override
                                                              public int compare(final ItemLocation loc1,
                                                                      final ItemLocation loc2) {
                                                                  return loc1.getName().toLowerCase()
                                                                          .compareTo(loc2.getName().toLowerCase());
                                                              }

                                                          };

    private void showOtherExistingItemLocations(final String itemId) {

        retrieveOtherExistingItemLocations(itemId);

        otherLocationsContainer.clear();
        selecteds.clear();

        Collections.sort(otherItemLocations, BY_NAME);

        for (final ItemLocation loc : otherItemLocations) {

            final NavLink otherLocationWidget = new NavLink();
            otherLocationWidget.setText(loc.getName());
            otherLocationWidget.getElement().addClassName("locationLi");
            otherLocationsContainer.add(otherLocationWidget);

            otherLocationWidget.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(final ClickEvent event) {

                    if (!otherLocationWidget.isActive()) {

                        selecteds.put(loc.getName(), loc);
                        otherLocationWidget.setActive(true);

                    } else {
                        selecteds.remove(loc.getName());
                        otherLocationWidget.setActive(false);

                    }
                }
            });

        }
    }

    private void retrieveOtherExistingItemLocations(final String itemId) {
        otherItemLocations.clear();
        getOtherItemLocationsFromCache(itemId, this);
    }

    public native void getOtherItemLocationsFromCache(String itemId, EditLocationViewImpl view) /*-{
        
        var locations = $wnd.cache_itemId2locations[itemId];
        var locationNames = [];
        
        for (var i = 0, len = locations.length; i < len; i++) {
            locationNames.push(locations[i].name);
        }
        
		var cache_all = $wnd.cache_name2itemLocation;

		for (var key in cache_all) {
			if (cache_all.hasOwnProperty(key)) {
				
				if ($wnd.$.inArray(key, locationNames) == -1) {
				
    				var loc = cache_all[key];
				    view
						.@pgu.client.profile.ui.EditLocationViewImpl::addOtherExistingItemLocation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(
								loc.name, '' + loc.lat, '' + loc.lng);
				}
			}
		}
    }-*/;

    public void addOtherExistingItemLocation(final String name, final String lat, final String lng) {
        final ItemLocation itemLocation = new ItemLocation();
        itemLocation.setName(name);
        itemLocation.setLat(lat);
        itemLocation.setLng(lng);
        otherItemLocations.add(itemLocation);
    }

    @Override
    public HasClickHandlers getSaveWidget() {
        return saveBtn;
    }

    @Override
    public void displayNewLocationWidget(final String itemId) {

        container.setTitle("Add locations");

        showOtherExistingItemLocations(itemId);

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

        // rollback selected locations
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
    public ArrayList<ItemLocation> getSelectedLocations() {
        return new ArrayList<ItemLocation>(selecteds.values());
    }

    @Override
    public void removeCreationForm(final String itemId) {
        // remove creation form
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

}
