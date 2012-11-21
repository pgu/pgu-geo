package pgu.client.pub;

import pgu.client.pub.event.FetchPublicContactsEvent;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicProfile;

import com.google.gwt.user.client.ui.IsWidget;

public interface PublicView extends IsWidget //
, FetchPublicContactsEvent.HasFetchPublicContactsHandlers //
{

    void setPresenter(PublicPresenter presenter);

    void setProfile(PublicProfile profile);

    void setContacts(PublicContacts result);

    void initPublicMapIfNeeded();

}
