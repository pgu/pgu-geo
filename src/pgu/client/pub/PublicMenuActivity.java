package pgu.client.pub;

import pgu.client.app.mvp.PublicClientFactory;
import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;

import com.google.web.bindery.event.shared.EventBus;

public class PublicMenuActivity implements //
UserNameEvent.Handler //
, UserHeadlineEvent.Handler //
{

    private final PublicMenuView      view;
    private EventBus                  eventBus;
    private final PublicClientFactory clientFactory;

    public PublicMenuActivity(final PublicClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getPublicMenuView();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.addHandler(UserNameEvent.TYPE, this);
        eventBus.addHandler(UserHeadlineEvent.TYPE, this);
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
