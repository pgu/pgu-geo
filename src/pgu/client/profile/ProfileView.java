package pgu.client.profile;

import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.profile.event.FetchCustomLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SavePublicLocationsEvent;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget //
, SaveLocationEvent.HasSaveLocationHandlers //
, LocationShowOnMapEvent.HasLocationShowOnMapHandlers //
, SaveMapPreferencesEvent.HasSaveMapPreferencesHandlers //
, FetchPublicPreferencesEvent.HasFetchPublicPreferencesHandlers //
, SavePublicLocationsEvent.HasSavePublicLocationsHandlers //
, FetchCustomLocationsEvent.HasFetchCustomLocationsHandlers //
, SavePublicProfileEvent.HasSavePublicProfileHandlers //
{

    void setPresenter(ProfilePresenter presenter);

    String getPublicProfile();

    void showPublicPreferences(String publicPreferences);

    void confirmChangeOnPublicProfile(String publicProfileItem);

    String getPublicPreferences();

    void hideSaveWidget();

    void showSaveWidget();

    void showOnMap(String locName);

    void showNotificationWarning(String msg);

    void showProfile();

    void setLocationsInfo(ProfileLocations profileLocations);

    void setPublicPreferencesInfo(PublicPreferences result);

    void refreshHtmlLocationsForItem(String itemConfigId);

    String getJsonPublicProfile();

}
