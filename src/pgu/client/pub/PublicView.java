package pgu.client.pub;

import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;

import com.google.gwt.user.client.ui.IsWidget;

public interface PublicView extends IsWidget {

    void setPresenter(PublicPresenter presenter);

    void setProfile(FullPublicProfile profile);

    void onFetchPublicContactsSuccess(PublicContacts result);

    void showProfileNotFound();

}
