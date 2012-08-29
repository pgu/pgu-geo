package pgu.client.menu;

import pgu.shared.dto.ItemLocation;

public interface MenuPresenter {

    void goToProfile();

    void goToContacts();

    void saveLocation(ItemLocation itemLocation);

}
