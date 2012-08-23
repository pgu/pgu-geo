package pgu.client.profile;

import java.util.ArrayList;

import pgu.shared.dto.Position;

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfilePresenter presenter);

    void setProfile(ArrayList<Position> positions);

}
