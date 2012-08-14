package pgu.client.profile;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileView extends IsWidget {

    void setPresenter(ProfilePresenter presenter);

    HasText getProfileBoard();

}
