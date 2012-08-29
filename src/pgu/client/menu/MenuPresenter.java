package pgu.client.menu;

import pgu.shared.dto.LatLng;

public interface MenuPresenter {

    void goToProfile();

    void goToContacts();

    void saveLocation(LatLng latLng, String locationName);

}
