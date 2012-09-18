package pgu.client.profile;

import pgu.shared.dto.Profile;
import pgu.shared.dto.PublicPreferences;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfilePresenter presenter);

    void setProfile(Profile profile);

    String getPublicProfile();

    void showPublicPreferencesAndUpdatePublicProfile(PublicPreferences publicPreferences);

    void confirmChangeOnPublicProfile(String publicProfileItem);

}
