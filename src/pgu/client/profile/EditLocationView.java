package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.utils.Notification;

import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;

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

    String getLocationsJson(String itemId);

    void hide();

}
