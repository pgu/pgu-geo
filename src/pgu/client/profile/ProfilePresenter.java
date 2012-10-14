package pgu.client.profile;


public interface ProfilePresenter {

    void addNewLocation(String itemConfigId);

    void editLocation(String itemConfigId, String locName);

    void setProfileId(String id);

    void setProfilePublicUrl(String url);

    void updatePublicProfile(String publicProfileItem);

}
