package pgu.client.pub;

import pgu.client.app.AppContext;
import pgu.client.app.event.ChartsApiLoadedEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.pub.event.FetchPublicContactsEvent;
import pgu.client.pub.event.UserHeadlineEvent;
import pgu.client.pub.event.UserNameEvent;
import pgu.client.service.PublicProfileService;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class PublicActivity implements PublicPresenter //
, FetchPublicContactsEvent.Handler //
, ShowdownLoadedEvent.Handler //
, MapsApiLoadedEvent.Handler //
, ChartsApiLoadedEvent.Handler //
{

    private final PublicView                view;
    private final PublicProfileServiceAsync publicProfileService = GWT.create(PublicProfileService.class);
    private final AppContext                ctx;

    private final ClientHelper               u                    = new ClientHelper();

    private EventBus                        eventBus;

    public PublicActivity(final PublicView view, final AppContext ctx) {
        this.view = view;
        this.ctx = ctx;
    }

    private boolean hasToSetPublic = false;

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;

        view.setPresenter(this);

        view.addFetchPublicContactsHandler(this);

        eventBus.addHandler(ShowdownLoadedEvent.TYPE, this);
        eventBus.addHandler(MapsApiLoadedEvent.TYPE, this);
        eventBus.addHandler(ChartsApiLoadedEvent.TYPE, this);

        panel.setWidget(view.asWidget());

        if (isAppReady(ctx)) {
            setPublic();

        } else {
            hasToSetPublic = true;
        }

    }

    public void setPublic() {

        view.initPublicMapIfNeeded();

        publicProfileService.fetchPublicProfileByUrl( //
                Window.Location.getHash(), // // TODO PGU Nov 18, 2012 review this url
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
        if (hasToSetPublic && isAppReady(ctx)) {
            hasToSetPublic = false;
            setPublic();
        }
    }

    private boolean areExternalApisLoaded(final AppContext ctx) {
        return ctx.isShowdownLoaded() //
                && ctx.isChartsApiLoaded() //
                && ctx.isMapsApiLoaded() //
                ;
    }

    private boolean isAppReady(final AppContext ctx) {
        // TODO PGU Nov 21, 2012 profile and contacts loaded from service
        return areExternalApisLoaded(ctx);
    }

}
