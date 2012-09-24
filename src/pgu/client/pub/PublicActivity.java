package pgu.client.pub;

import pgu.client.app.mvp.PublicClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.PublicProfile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PublicActivity extends AbstractActivity implements PublicPresenter {

    private final PublicClientFactory       clientFactory;
    private final PublicView                view;
    private final PublicProfileServiceAsync publicProfileService;

    private final ClientUtils               u = new ClientUtils();

    private EventBus                        eventBus;
    private final PublicPlace               place;

    public PublicActivity(final PublicPlace place, final PublicClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
        view = clientFactory.getPublicView();
        publicProfileService = clientFactory.getPublicProfileService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);

        panel.setWidget(view.asWidget());

        publicProfileService.fetchPublicProfileByUrl( //
                place.getPublicUrl(), //
                new AsyncCallbackApp<PublicProfile>(eventBus) {

                    @Override
                    public void onSuccess(final PublicProfile profile) {
                        view.setProfile(profile);
                    }

                });

    }

    @Override
    public void setProfileName(final String name) {
        u.fire(eventBus, new UserNameEvent(name));
    }

    @Override
    public void setProfileHeadline(final String headline) {
        u.fire(eventBus, new UserHeadlineEvent(headline));
    }

}
