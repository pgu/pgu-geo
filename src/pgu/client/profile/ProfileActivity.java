package pgu.client.profile;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.ItemLocation;
import pgu.shared.dto.Profile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity implements ProfilePresenter //
        , LocationsSuccessSaveEvent.Handler //
        , LocationSuccessDeleteEvent.Handler //
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

        eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this);
        eventBus.addHandler(LocationSuccessDeleteEvent.TYPE, this);

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
    public void addNewLocation(final String itemId) {
        // itemId for a position or an education

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory);
        editLocationActivity.start(null, itemId);

    }

    @Override
    public void editLocation(final String itemId, final String locName, final String locLat, final String locLng) {

        final ItemLocation itemLocation = new ItemLocation();
        itemLocation.setName(locName);
        itemLocation.setLat(locLat);
        itemLocation.setLng(locLng);

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory);
        editLocationActivity.start(itemLocation, itemId);
    }

    @Override
    public void onLocationsSuccessSave(final LocationsSuccessSaveEvent event) {
        // TODO PGU Aug 29, 2012 check on the lat/lng
        u.addNewLocationsToItem(event.getItemId(), event.getNewItemLocations());
    }

    @Override
    public void showLocationOnMap(final ItemLocation itemLocation) {
        u.fire(eventBus, new LocationShowOnMapEvent(itemLocation));
    }

    @Override
    public void onLocationSuccessDelete(final LocationSuccessDeleteEvent event) {
        u.removeLocationFromItem(event.getItemId(), event.getDeletedItemLocation());
    }

    @Override
    public void setProfileId(final String id) {
        clientFactory.getAppState().setUserId(id);
    }

}
