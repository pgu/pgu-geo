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

    HasVisibility getNewLocationWidget();

    HasVisibility getEditLocationWidget();

    void showOtherExistingItemLocations(String itemId);

    HasClickHandlers getSaveWidget();

    ArrayList<ItemLocation> getSelectedItemLocations();

    HasText getFormTitle();

}
