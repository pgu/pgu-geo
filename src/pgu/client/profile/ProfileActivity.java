package pgu.client.profile;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationSaveEvent;
import pgu.client.app.event.LocationSearchEvent;
import pgu.client.app.event.RefreshLocationsEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.Profile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity implements ProfilePresenter //
        , LocationSaveEvent.Handler //
        , RefreshLocationsEvent.Handler //
{

    private final ClientFactory        clientFactory;
    private final ProfileView          view;
    private final LinkedinServiceAsync linkedinService;

    private final ClientUtils          u = new ClientUtils();

    private EventBus                   eventBus;

    public ProfileActivity(final ProfilePlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getProfileView();
        linkedinService = clientFactory.getLinkedinService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        eventBus.addHandler(LocationSaveEvent.TYPE, this);
        eventBus.addHandler(RefreshLocationsEvent.TYPE, this);

        panel.setWidget(view.asWidget());

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        linkedinService.fetchProfile( //
                clientFactory.getAppState().getAccessToken() //
                , new AsyncCallbackApp<Profile>(eventBus) {

                    @Override
                    public void onSuccess(final Profile profile) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());
                        view.setProfile(profile);
                    }

                });
    }

    @Override
    public void searchForLocation(final String itemId, final String anchorId, final String text) {
        u.fire(eventBus, new LocationSearchEvent(itemId, text));
    }

    @Override
    public void addNewLocation(final String itemId) {
        // itemId for a position or an education

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory);
        editLocationActivity.start(null, itemId);

    }

    @Override
    public void onLocationSave(final LocationSaveEvent event) {
        // TODO PGU add the new location to the list
        // for education: education.id
        // for xp: position.id
        // location: item.id, label, latlng,
        // hashcode/equals by latlng and name
        // itemLocation

    }

    @Override
    public void onRefreshLocations(final RefreshLocationsEvent event) {
        view.refreshLocationsForItem(event.getItemId());
    }

}
