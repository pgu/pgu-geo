package pgu.client.menu;

import pgu.client.menu.event.GoToContactsEvent;
import pgu.client.menu.event.GoToProfileEvent;
import pgu.client.menu.event.GoToPublicProfileEvent;

import com.github.gwtbootstrap.client.ui.base.HasHref;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget //
, GoToProfileEvent.HasGoToProfileHandlers //
, GoToContactsEvent.HasGoToContactsHandlers //
, GoToPublicProfileEvent.HasGoToPublicProfileHandlers //
{

    interface LogWidget extends HasVisibility, HasHref {
    }

    interface ProfilePlayMenuWidget extends HasVisibility {

        void init();
    }

    interface LocationSearchWidget extends HasText {

        void setFocus(boolean isFocused);

    }

    HasVisibility getWaitingIndicator();

    HasVisibility getProfileWidget();

    HasVisibility getContactsWidget();

    void setIsAdmin(boolean isAdmin);

    void setLogoutUrl(String logoutUrl);

    void setLoginUrl(String loginUrl);

    void setIsSuperAdmin(boolean isSuperAdmin);

}
