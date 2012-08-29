package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.utils.Notification;
import pgu.shared.dto.ItemLocation;

import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVisibility;

public interface EditLocationView {

    HasVisibleHandlers getCloseHandler();

    ArrayList<Notification> getNotifications();

    HasClickHandlers getAddHandler();

    void show();

    void showOtherExistingItemLocations(String itemId);

    HasClickHandlers getSaveWidget();

    HasText getFormTitle();

    void displayNewLocationWidget();

    void displayEditLocationWidget();

    String getAllItemsWithAllLocationsJson(String itemId);

    void hide();

    HasVisibility getWaitingIndicator();

    Notification newNotification();

    void disableCreationForm();

    void resetCreationForm();

    ArrayList<ItemLocation> getSelectedLocations();

    void removeCreationFormAndCommitNewLocations(String itemId);

}
