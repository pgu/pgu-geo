package pgu.client.pub;

import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.PublicProfile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PublicActivity extends AbstractActivity implements PublicPresenter {

    private final ClientFactory             clientFactory;
    private final PublicView                view;
    private final LinkedinServiceAsync      linkedinService;
    private final PublicProfileServiceAsync publicProfileService;

    private final ClientUtils               u = new ClientUtils();

    private EventBus                        eventBus;
    private final PublicPlace               place;

    public PublicActivity(final PublicPlace place, final ClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
        view = clientFactory.getPublicView();
        linkedinService = clientFactory.getLinkedinService();
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
                    public void onSuccess(final PublicProfile result) {
                        // TODO PGU Sep 17, 2012
                        GWT.log("retour...");
                        GWT.log(result.getProfile());
                    }

                });

    }

}
