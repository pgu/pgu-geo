package pgu.client.profile;

import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.profile.event.FetchProfileLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveLocationsEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SavePublicPreferencesEvent;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget //
, SaveLocationEvent.HasSaveLocationHandlers //
, LocationShowOnMapEvent.HasLocationShowOnMapHandlers //
, SaveMapPreferencesEvent.HasSaveMapPreferencesHandlers //
, FetchPublicPreferencesEvent.HasFetchPublicPreferencesHandlers //
, SaveLocationsEvent.HasSaveLocationsHandlers //
, FetchProfileLocationsEvent.HasFetchProfileLocationsHandlers //
, SavePublicProfileEvent.HasSavePublicProfileHandlers //
, SavePublicPreferencesEvent.HasSavePublicPreferencesHandlers //
{

    void setPresenter(ProfilePresenter presenter);

    @Deprecated
    String getPublicProfile();

    void confirmChangeOnPublicProfile(String publicProfileItem);

    void hideSaveWidget();

    void showSaveWidget();

    void showOnMap(String locName);

    void showNotificationWarning(String msg);

    void showProfile();

    void setProfileLocations(ProfileLocations profileLocations);

    void setPublicPreferencesInfo(PublicPreferences result);

    void refreshHtmlLocationsForItem(String itemConfigId);

    String getJsonPublicProfile();

    String getJsonRawProfile();

    void removeUnusedLocations();

    void addGeopointToCopyCache(String locationName, String lat, String lng);

    void copyLocationCaches();

    String json_copyCacheItems();

    String json_copyCacheReferential();

    void replaceCachesByCopies();

    void deleteCopies();

    String getJsonPublicPreferences();

    boolean isLocationDoublon(String itemConfigId, String locationName, String lat, String lng);

    void addLocation2ItemInCopyCache(String itemConfigId, String locationName);

}
