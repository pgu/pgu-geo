package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import pgu.client.app.utils.Notification;
import pgu.client.app.utils.NotificationImpl;
import pgu.client.profile.EditLocationView;
import pgu.shared.dto.ItemLocation;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.Widget;

public class EditLocationViewImpl extends Composite implements EditLocationView {

    private static EditLocationViewImplUiBinder uiBinder = GWT.create(EditLocationViewImplUiBinder.class);

    interface EditLocationViewImplUiBinder extends UiBinder<Widget, EditLocationViewImpl> {
    }

    @UiField
    Modal                                             container;
    @UiField
    Button                                            saveBtn, addBtn, displayOnMapBtn, deleteBtn;
    @UiField
    ProgressBar                                       progressBar;
    @UiField
    HTMLPanel                                         notification, btnsContainer;
    @UiField
    WellForm                                          addPanel, editPanel;

    private final ArrayList<Notification>             notifications      = new ArrayList<Notification>();
    private final ArrayList<ItemLocation>             otherItemLocations = new ArrayList<ItemLocation>();
    private final LinkedHashMap<String, ItemLocation> selecteds          = new LinkedHashMap<String, ItemLocation>();

    public EditLocationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        progressBar.setVisible(false);
    }

    public native static void exportMethod() /*-{
        $wnd.searchMapFor = $entry(@pgu.client.profile.ui.ProfileViewImpl::searchMapFor(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;));
    }-*/;

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

    @Override
    public void showOtherExistingItemLocations(final String itemId) {

        retrieveOtherExistingItemLocations(itemId);

        btnsContainer.clear();
        selecteds.clear();

        Collections.sort(otherItemLocations, BY_NAME);

        for (final ItemLocation loc : otherItemLocations) {
            final Button btn = new Button();
            btn.setType(ButtonType.DEFAULT);
            btn.setText(loc.getName());

            btnsContainer.add(btn);

            btn.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(final ClickEvent event) {
                    final ButtonType type = btn.getType();

                    if (ButtonType.DEFAULT == type) {
                        btn.setType(ButtonType.SUCCESS);
                        selecteds.put(loc.getName(), loc);

                    } else {
                        btn.setType(ButtonType.DEFAULT);
                        selecteds.remove(loc.getName());

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
    public HasText getFormTitle() {
        return new HasText() {

            @Override
            public String getText() {
                return container.getTitle();
            }

            @Override
            public void setText(final String text) {
                container.setTitle(text);
            }

        };
    }

    @Override
    public void displayNewLocationWidget() {
        selecteds.clear();
        btnsContainer.clear();

        addPanel.setVisible(true);
        editPanel.setVisible(false);

        saveBtn.setVisible(true);
        displayOnMapBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }

    @Override
    public void displayEditLocationWidget() {
        addPanel.setVisible(false);
        editPanel.setVisible(true);

        saveBtn.setVisible(false);
        displayOnMapBtn.setVisible(true);
        deleteBtn.setVisible(true);
    }

    @Override
    public String getAllItemsWithAllLocationsJson(final String itemId) {

        initTemporaryCache();

        final ArrayList<ItemLocation> newLocations = new ArrayList<ItemLocation>(selecteds.values());
        for (final ItemLocation newLoc : newLocations) {

            addNewLocationToTemporaryCache(itemId, newLoc.getName(), newLoc.getLat(), newLoc.getLng());
        }

        return fetchAllFromTemporaryCacheJson();
    }

    private native void initTemporaryCache() /*-{

		$wnd.__tmp_cache_itemId2locations = {};

		var orig = $wnd.cache_itemId2locations;
		var copy = $wnd.__tmp_cache_itemId2locations;

		for ( var key in orig) {
			if (orig.hasOwnProperty(key)) {

				copy[key] = orig[key];
			}
		}

    }-*/;

    private native void addNewLocationToTemporaryCache(String itemId, String name, String lat, String lng) /*-{

		var loc = {};
		loc.name = name;
		loc.lat = lat;
		loc.lng = lng;

		$wnd.__tmp_cache_itemId2locations[itemId].push(loc);

    }-*/;

    private native String fetchAllFromTemporaryCacheJson() /*-{

		var tmp_cache_json = JSON.stringify($wnd.__tmp_cache_itemId2locations);

		$wnd.__tmp_cache_itemId2locations = null;

		return tmp_cache_json;
    }-*/;

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
        for (int i = 0; i < btnsContainer.getWidgetCount(); i++) {
            ((Button) btnsContainer.getWidget(i)).setType(ButtonType.DEFAULT);
        }

        // rollback selected locations
        selecteds.clear();
        // TODO PGU Aug 29, 2012 remove locations from the cache
    }

    private void setEnableOnCreationForm(final boolean isEnabled) {
        saveBtn.setEnabled(isEnabled);
        addBtn.setEnabled(isEnabled);
        for (int i = 0; i < btnsContainer.getWidgetCount(); i++) {
            ((Button) btnsContainer.getWidget(i)).setEnabled(isEnabled);
        }
    }

    @Override
    public ArrayList<ItemLocation> getSelectedLocations() {
        return new ArrayList<ItemLocation>(selecteds.values());
    }

    @Override
    public void removeCreationFormAndCommitNewLocations(final String itemId) {
        // remove creation form
        addPanel.setVisible(false);

        saveBtn.setEnabled(true);
        addBtn.setEnabled(true);
        btnsContainer.clear();

        // commit locations to the cache
        final ArrayList<ItemLocation> newLocations = new ArrayList<ItemLocation>(selecteds.values());
        for (final ItemLocation newLoc : newLocations) {

            addLocationToItemId(itemId, newLoc.getName(), newLoc.getLat(), newLoc.getLng());
        }

    }

    private native void addLocationToItemId(String itemId, String name, String lat, String lng) /*-{

		var loc = {};
		loc.name = name;
		loc.lat = lat;
		loc.lng = lng;

		$wnd.cache_itemId2locations[itemId].push(loc);

    }-*/;

}
