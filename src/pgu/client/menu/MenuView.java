package pgu.client.menu;

import com.github.gwtbootstrap.client.ui.base.HasHref;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget {

    interface LogWidget extends HasVisibility, HasHref {
    }

    void setPresenter(MenuPresenter presenter);

    HasVisibility getWaitingIndicator();

    LogWidget getLoginWidget();

    LogWidget getLogoutWidget();

    HasVisibility getAppstatsWidget();

    HasVisibility getProfileWidget();

    HasVisibility getContactsWidget();

    HasText getLocationSearchWidget();

    void setItemId(String id);

}
