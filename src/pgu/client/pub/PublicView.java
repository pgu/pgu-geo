package pgu.client.pub;

import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicProfile;

import com.google.gwt.user.client.ui.IsWidget;

public interface PublicView extends IsWidget {

    void setPresenter(PublicPresenter presenter);

    void setProfile(PublicProfile profile);

    void onFetchPublicContactsSuccess(PublicContacts result);

    void initPublicMapIfNeeded();

}
