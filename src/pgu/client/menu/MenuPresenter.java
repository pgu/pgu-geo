package pgu.client.menu;


public interface MenuPresenter {

    void goToProfile();

    void goToContacts();

    void saveLocation(String itemLocation);

    void showNotificationWarning(String msg);

    void openPublicProfile();

}
