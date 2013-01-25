package pgu.client.profile;

import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfileActivity presenter);

    void confirmChangeOnPublicProfile(String publicProfileItem);

    void showSaveWidget(String itemConfigId);

    void showNotificationWarning(String msg);

    void showProfile();

    void onFetchProfileLocationsSuccess(ProfileLocations profileLocations);

    String getJsonRawProfile();

    boolean isProfileSetInView();

    void onFetchMapPreferencesSuccess(String values);

    void onSaveLastSearchLocationSuccess(String locationName, String itemConfigIdToSave);

    void onSaveLastSearchLocationFailure(Throwable caught);

    void onUpdateLocationsSilentlySuccess();

    void onUpdateLocationsSilentlyFailure(Throwable caught);

    void onFetchPublicPreferencesSuccess(PublicPreferences result);

    void onLocationsSuccessSave(String itemConfigId);

    void onLocationSuccessDelete(String itemConfigId);

    void onLocationShowOnMap(String locName);

}
