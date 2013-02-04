package pgu.client.pub;

import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;

import com.google.gwt.user.client.ui.IsWidget;

public interface PublicView extends IsWidget {

    void setPresenter(PublicPresenter presenter);

    void setProfile(BasePublicProfile profile);

    void onFetchPublicContactsSuccess(PublicContacts result);

    void showProfileNotFound();

    void onFetchMapPreferencesSuccess(String values);

}
