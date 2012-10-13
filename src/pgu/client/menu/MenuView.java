package pgu.client.menu;

import pgu.client.menu.event.GoToAppStatsEvent;
import pgu.client.menu.event.GoToContactsEvent;
import pgu.client.menu.event.GoToProfileEvent;
import pgu.client.menu.event.GoToPublicProfileEvent;

import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget //
, GoToProfileEvent.HasGoToProfileHandlers //
, GoToContactsEvent.HasGoToContactsHandlers //
, GoToPublicProfileEvent.HasGoToPublicProfileHandlers //
, GoToAppStatsEvent.HasGoToAppStatsHandlers //
{

    void setIsAdmin(boolean isAdmin);

    void setLogoutUrl(String logoutUrl);

    void setLoginUrl(String loginUrl);

    void setIsSuperAdmin(boolean isSuperAdmin);

}
