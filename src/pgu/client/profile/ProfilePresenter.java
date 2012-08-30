package pgu.client.profile;

import pgu.shared.dto.ItemLocation;

public interface ProfilePresenter {

    void addNewLocation(String itemId);

    void editLocation(String itemId, String locName, String locLat, String locLng);

    void showLocationOnMap(ItemLocation itemLocation);

}
