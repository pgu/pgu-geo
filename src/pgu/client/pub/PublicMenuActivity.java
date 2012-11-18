package pgu.client.pub;

import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class PublicMenuActivity implements //
UserNameEvent.Handler //
, UserHeadlineEvent.Handler //
{

    private final PublicMenuView      view;

    public PublicMenuActivity(final PublicMenuView view) {
        this.view = view;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        eventBus.addHandler(UserNameEvent.TYPE, this);
        eventBus.addHandler(UserHeadlineEvent.TYPE, this);

        panel.setWidget(view.asWidget());
    }

    @Override
    public void onUserName(final UserNameEvent event) {
        view.setUserName(event.getUserName());
    }

    @Override
    public void onUserHeadline(final UserHeadlineEvent event) {
        view.setUserHeadline(event.getUserHeadline());
    }

}
