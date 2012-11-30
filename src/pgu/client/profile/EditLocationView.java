package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.utils.HasNotifications;
import pgu.client.app.utils.Notification;

import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasVisibility;

public interface EditLocationView extends HasNotifications {

    HasVisibleHandlers getCloseHandler();

    ArrayList<Notification> getNotifications();

    HasClickHandlers getAddHandler();

    HasClickHandlers getSaveWidget();

    HasClickHandlers getShowOnMapHandler();

    HasClickHandlers getDeleteHandler();

    void displayEditLocationWidget(String locationName, boolean isFromLinkedin);

    void show();

    void hide();

    HasVisibility getWaitingIndicator();

    void disableCreationForm();

    void resetCreationForm();

    ArrayList<String> getSelectedLocations();

    void removeCreationFormAndShowClose();

    void displayNewLocationWidget(String itemConfigId);

    HasVisibility getCloseWidget();

    void disableEditionForm();

    void enableEditionForm();

    void removeEditionFormAndShowClose();

    void copyLocationCaches();

    boolean isLocationFromLinkedin(String itemConfigId, String locName);

    void removeLocationFromCopyCaches(String itemConfigId, String locationName);

}
