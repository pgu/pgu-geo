package pgu.client.menu;

import pgu.shared.dto.LatLng;

public interface MenuPresenter {

    void goToProfile();

    void goToContacts();

    void saveLocationItem(LatLng latLng, String itemId, String text);

}
