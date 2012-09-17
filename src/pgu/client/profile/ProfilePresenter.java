package pgu.client.profile;


public interface ProfilePresenter {

    void addNewLocation(String itemConfigId);

    void editLocation(String itemConfigId, String locName);

    void showLocationOnMap(String locationName);

    void setProfileId(String id);

    void setProfilePublicUrl(String url);

}
