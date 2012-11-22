package pgu.client.profile;


public interface ProfilePresenter {

    void addNewLocation(String itemConfigId);

    void editLocation(String itemConfigId, String locName);

    void updatePublicProfile(String publicProfileItem);

}
