package pgu.client.pub;

import pgu.client.pub.event.FetchPublicContactsEvent;
import pgu.shared.dto.PublicProfile;
import pgu.shared.model.PublicContacts;

import com.google.gwt.user.client.ui.IsWidget;

public interface PublicView extends IsWidget //
, FetchPublicContactsEvent.HasFetchPublicContactsHandlers //
{

    void setPresenter(PublicPresenter presenter);

    void setProfile(PublicProfile profile);

    void setContacts(PublicContacts result);

}
