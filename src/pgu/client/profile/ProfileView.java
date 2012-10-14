package pgu.client.profile;

import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.shared.dto.Profile;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget //
, SaveLocationEvent.HasSaveLocationHandlers //
, LocationShowOnMapEvent.HasLocationShowOnMapHandlers //
{

    void setPresenter(ProfilePresenter presenter);

    void setProfile(Profile profile);

    String getPublicProfile();

    void showPublicPreferences(String publicPreferences);

    void confirmChangeOnPublicProfile(String publicProfileItem);

    String getPublicPreferences();

    void hideSaveWidget();

    void showSaveWidget();

    void showOnMap(String locName);

    void showNotificationWarning(String msg);

}
