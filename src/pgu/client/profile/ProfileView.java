package pgu.client.profile;

import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfileActivity presenter);

    void onSavePublicPreferencesSuccess(String publicProfileItem);

    void showSaveWidget(String itemConfigId);

    void showNotificationWarning(String msg);

    void showProfile();

    void onFetchProfileLocationsSuccess(ProfileLocations profileLocations);

    String getJsonRawProfile();

    boolean isProfileSetInView();

    void onFetchMapPreferencesSuccess(MapPreferences mapPreferences);

    void onSaveLastSearchLocationSuccess(String locationName, String itemConfigIdToSave);

    void onSaveLastSearchLocationFailure(Throwable caught);

    void onUpdateLocationsSilentlySuccess();

    void onUpdateLocationsSilentlyFailure(Throwable caught);

    void onFetchPublicPreferencesSuccess(PublicPreferences result);

    void onLocationsSuccessSave(String itemConfigId);

    void onLocationSuccessDelete(String itemConfigId);

    void onLocationShowOnMap(String locName);

}
