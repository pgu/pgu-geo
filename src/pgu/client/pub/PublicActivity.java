package pgu.client.pub;

import java.util.ArrayList;

import pgu.client.app.mvp.PublicClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.pub.event.FetchPublicContactsEvent;
import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;
import pgu.client.pub.ui.PublicUtils;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicProfile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class PublicActivity extends AbstractActivity implements PublicPresenter //
, FetchPublicContactsEvent.Handler //
{

    private final PublicClientFactory       clientFactory;
    private final PublicView                view;
    private final PublicProfileServiceAsync publicProfileService;

    private final ClientUtils               u = new ClientUtils();

    private EventBus                        eventBus;
    private final PublicPlace               place;

    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();

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

        hRegs.clear();
        hRegs.add(view.addFetchPublicContactsHandler(this));

        panel.setWidget(view.asWidget());
        PublicUtils.initPublicProfileMap();

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

    @Override
    public void onFetchPublicContacts(final FetchPublicContactsEvent event) {
        publicProfileService.fetchPublicContacts( //
                event.getUserId(), //
                new AsyncCallbackApp<PublicContacts>(eventBus) {

                    @Override
                    public void onSuccess(final PublicContacts result) {
                        view.setContacts(result);
                    }

                });
    }

}
