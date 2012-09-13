package pgu.client.profile;

import pgu.shared.dto.ItemLocation;

public interface ProfilePresenter {

    void addNewLocation(String itemConfigId);

    void editLocation(String itemConfigId, String locName, String locLat, String locLng);

    void showLocationOnMap(ItemLocation itemLocation);

    void setProfileId(String id);

}
