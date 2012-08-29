package pgu.client.profile;

import pgu.shared.dto.Profile;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfilePresenter presenter);

    void setProfile(Profile profile);

    void refreshLocationsForItem(String itemId);

}
