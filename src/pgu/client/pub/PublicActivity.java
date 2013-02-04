package pgu.client.pub;

import java.util.Date;

import pgu.client.app.AppContext;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;
import pgu.client.service.PublicProfileService;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class PublicActivity implements PublicPresenter //
, ShowdownLoadedEvent.Handler //
, MapsApiLoadedEvent.Handler //
, ChartsApiLoadedEvent.Handler //
{

    private final PublicView                view;
    private final PublicProfileServiceAsync publicProfileService = GWT.create(PublicProfileService.class);
    private final AppContext                ctx;

    private final ClientHelper              u                    = new ClientHelper();

    private EventBus                        eventBus;
    private String                          profileUrl;

    public PublicActivity(final PublicView view, final AppContext ctx, final String profileUrl) {
        this.view = view;
        this.ctx = ctx;
        this.profileUrl = profileUrl;
    }

    private boolean hasToShowPublic = false;

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        GWT.log(" !! public activity start !! " + new Date().getTime());

        this.eventBus = eventBus;

        view.setPresenter(this);

        eventBus.addHandler(ShowdownLoadedEvent.TYPE, this);
        eventBus.addHandler(MapsApiLoadedEvent.TYPE, this);
        eventBus.addHandler(ChartsApiLoadedEvent.TYPE, this);

        panel.setWidget(view.asWidget());

        if (isAppReady(ctx)) {
            showPublic();

        } else {
            hasToShowPublic = true;
        }
    }

    private void showPublic() {
        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        publicProfileService.fetchPublicProfileByUrl( //
                profileUrl, //
                new AsyncCallbackApp<BasePublicProfile>(eventBus) {

                    @Override
                    public void onSuccess(final BasePublicProfile profile) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());

                        if (profile == null) {
                            view.showProfileNotFound();
                            return;
                        }

                        if (!profile.getProfileUrl().equals(profileUrl)) {
                            return; // obsolete response
                        }

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
    public void onChartsApiLoaded(final ChartsApiLoadedEvent event) {
        setPublicAsync();
    }

    @Override
    public void onMapsApiLoaded(final MapsApiLoadedEvent event) {
        setPublicAsync();
    }

    @Override
    public void onShowdownLoaded(final ShowdownLoadedEvent event) {
        setPublicAsync();
    }

    private void setPublicAsync() {
        if (hasToShowPublic && isAppReady(ctx)) {
            hasToShowPublic = false;
            showPublic();
        }
    }

    private boolean areExternalApisLoaded(final AppContext ctx) {
        return ctx.isShowdownLoaded() //
                && ctx.isChartsApiLoaded() //
                && ctx.isMapsApiLoaded() //
                ;
    }

    private boolean isAppReady(final AppContext ctx) {
        return areExternalApisLoaded(ctx);
    }

    @Override
    public void fetchPublicContacts(final String profileId) {
        publicProfileService.fetchPublicContacts( //
                profileId, //
                new AsyncCallbackApp<PublicContacts>(eventBus) {

                    @Override
                    public void onSuccess(final PublicContacts result) {

                        if (!result.getProfileUrl().equals(profileUrl)) {
                            return; // obsolete
                        }

                        view.onFetchPublicContactsSuccess(result);
                    }

                });
    }

    public void changeProfile(final String profileUrl) {
        this.profileUrl = profileUrl;
        u.console("> profile_url " + profileUrl);
        // TODO PGU Feb 4, 2013 clear profile data
        // TODO PGU Feb 4, 2013 clear contacts
    }

}
