package pgu.client.profile;

public interface ProfilePresenter {

    void addNewLocation(String itemId);

    void editLocation(String itemId, String locName, String locLat, String locLng);

    void searchForLocation(String locationName);

}
