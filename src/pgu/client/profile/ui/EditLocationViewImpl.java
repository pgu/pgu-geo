package pgu.client.profile.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pgu.client.app.utils.ClientHelper;
import pgu.client.app.utils.Notification;
import pgu.client.app.utils.NotificationImpl;
import pgu.client.profile.EditLocationActivity;
import pgu.client.profile.EditLocationView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavPills;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.event.HiddenEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
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

    private final ClientHelper             u                  = new ClientHelper();
    private final EditLocationViewHelper      viewHelper = new EditLocationViewHelper();
    private final ProfileLocationsHelper      locationsHelper = new ProfileLocationsHelper();

    private EditLocationActivity presenter;

    private Timer                                timerCloseView = null;

    public EditLocationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        closeBtn.setVisible(false);
        progressBar.setVisible(false);
    }

    @UiHandler("addBtn")
    public void onClickAdd(final ClickEvent event) {
        presenter.addNewLocation();
    }


    @UiHandler("saveBtn")
    public void onClickSave(final ClickEvent event) {
        if (selecteds.isEmpty()) {
            return;
        }

        showWaitingIndicator();
        setEnableOnCreationForm(false);

        locationsHelper.copyLocationCaches();
        final String itemConfigId = presenter.getItemConfigId();

        for (final String locationName: selecteds) {
            locationsHelper.addLocation2ItemInCopyCache(itemConfigId, locationName);
        }

        presenter.addExistingLocations( //
                locationsHelper.json_copyCacheItems() //
                , locationsHelper.json_copyCacheReferential() //
                );
    }

    @UiHandler("deleteBtn")
    public void onClickDelete(final ClickEvent event) {

        final String item_config_id = presenter.getItemConfigId();
        final String location_name = presenter.getLocationName();

        if (viewHelper.isLocationFromLinkedin(item_config_id, location_name)) {
            return;
        }

        showWaitingIndicator();

        disableEditionForm();
        locationsHelper.copyLocationCaches();

        viewHelper.removeLocationFromCopyCaches(item_config_id, location_name);

        presenter.deleteLocations( //
                locationsHelper.json_copyCacheItems() //
                , locationsHelper.json_copyCacheReferential() //
                );
    }

    @UiHandler("displayOnMapBtn")
    public void onClickDisplayOnMap(final ClickEvent event) {
        hidePopup();
        presenter.displayOnMap();
    }

    private void showWaitingIndicator() {
        progressBar.setVisible(true);
    }

    private void hideWaitingIndicator() {
        progressBar.setVisible(false);
    }

    @UiHandler("container")
    public void onHiddenContainer(final HiddenEvent event) {
        closeBtn.setVisible(false);

        for (final Notification notif : notifications) {
            if (notif != null) {
                notif.removeFromParent();
            }
        }

        if (timerCloseView != null) {
            timerCloseView.cancel();
        }

        presenter.onStop();
    }

    private void hideViewWithDelay() {
        timerCloseView = new Timer() {

            @Override
            public void run() {
                hidePopup();
            }


        };
        timerCloseView.schedule(3000);
    }

    private void hidePopup() {
        container.hide();
    }

    @UiHandler("closeBtn")
    public void clickClose(final ClickEvent e) {
        hidePopup();
    }

    @UiHandler("addBtn")
    public void clickAdd(final ClickEvent e) {
        hidePopup();
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

    private JavaScriptObject getOtherLocationNames(final String itemConfigId) {
        return viewHelper.getOtherLocationNames(itemConfigId);
    }

    private native void getOtherItemLocationsFromCache(String item_config_id, EditLocationViewImpl view) /*-{

		var other_location_names = this.@pgu.client.profile.ui.EditLocationViewImpl::getOtherLocationNames(Ljava/lang/String;)
		                                (item_config_id);

        for ( var i = 0, len = other_location_names.length; i < len; i++) {

			var other_location_name = other_location_names[i];

			view.@pgu.client.profile.ui.EditLocationViewImpl::addOtherExistingItemLocation(Ljava/lang/String;)
			     (other_location_name);
		}
    }-*/;

    private void addOtherExistingItemLocation(final String locationName) {
        otherItemLocations.add(locationName);
    }

    @Override
    public void displayNewLocationWidget() {

        container.setTitle("Add locations");

        showOtherExistingItemLocations(presenter.getItemConfigId());

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
    public void displayEditLocationWidget() {

        final String item_config_id = presenter.getItemConfigId();
        final String locationName = presenter.getLocationName();

        final boolean isFromLinkedin = viewHelper.isLocationFromLinkedin(item_config_id, locationName);

        container.setTitle(locationName);

        viewHelper.showLatitudeAndLongitude(this, locationName);

        // ///////////////
        addPanel.setVisible(false);
        editPanel.setVisible(true);

        addBtn.setVisible(false);
        saveBtn.setVisible(false);
        displayOnMapBtn.setVisible(true);
        deleteBtn.setVisible(!isFromLinkedin);
    }

    private void showLatitudeAndLongitude(final String latitude, final String longitude) {
        locationLatUI.setText(latitude);
        locationLngUI.setText(longitude);
    }

    @Override
    public Notification newNotification() {
        final NotificationImpl notif = new NotificationImpl(notification, 3000);
        notifications.add(notif);
        return notif;
    }

    private void resetCreationForm() {
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

    private void removeCreationFormAndShowClose() {

        addPanel.setVisible(false);
        saveBtn.setVisible(false);
        addBtn.setVisible(false);
        closeBtn.setVisible(true);

        saveBtn.setEnabled(true);
        addBtn.setEnabled(true);

        otherLocationsContainer.clear();
    }

    private void disableEditionForm() {
        displayOnMapBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    private void enableEditionForm() {
        displayOnMapBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

    private void removeEditionFormAndShowClose() {
        editPanel.setVisible(false);
        displayOnMapBtn.setVisible(false);
        deleteBtn.setVisible(false);
        closeBtn.setVisible(true);

        displayOnMapBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

    @Override
    public void setPresenter(final EditLocationActivity presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAddExistingLocationsSuccess() {
        locationsHelper.replaceCachesByCopies();

        hideWaitingIndicator();
        removeCreationFormAndShowClose();

        final StringBuilder msg = getSuccessMessage(selecteds);
        u.showNotificationSuccess(msg, this);

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
    public void onAddExistingLocationsFailure(final Throwable caught) {
        locationsHelper.deleteCopies();

        hideWaitingIndicator();
        resetCreationForm();

        u.showNotificationError(caught, this);
    }

    @Override
    public void onDeleteLocationsSuccess() {
        locationsHelper.replaceCachesByCopies();

        hideWaitingIndicator();
        removeEditionFormAndShowClose();

        final StringBuilder msg = getSuccessMessage(presenter.getLocationName());
        u.showNotificationSuccess(msg, this);

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
    public void onDeleteLocationsFailure(final Throwable caught) {
        locationsHelper.deleteCopies();

        hideWaitingIndicator();
        enableEditionForm();

        u.showNotificationError(caught, this);
    }


}
