package pgu.client.pub;

public interface PublicPresenter {

    void setProfileName(String name);

    void setProfileHeadline(String headline);

    void fetchPublicContacts(String profileUrl);

}
