package pgu.client.profile;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.menu.ui.MenuViewUtils;
import pgu.client.profile.ui.ProfileViewUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.dto.Profile;
import pgu.shared.dto.PublicProfile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity implements ProfilePresenter //
, LocationsSuccessSaveEvent.Handler //
, LocationSuccessDeleteEvent.Handler //
, LocationAddNewEvent.Handler //
, LocationShowOnMapEvent.Handler //
{

    private final ClientFactory             clientFactory;
    private final ProfileView               view;
    private final LinkedinServiceAsync      linkedinService;
    private final PublicProfileServiceAsync publicProfileService;

    private final ClientUtils               u = new ClientUtils();

    private EventBus                        eventBus;

    public ProfileActivity(final ProfilePlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getProfileView();
        linkedinService = clientFactory.getLinkedinService();
        publicProfileService = clientFactory.getPublicProfileService();
    }

    private String                     itemConfigId;

    @Override
    public void onLocationShowOnMap(final LocationShowOnMapEvent event) {
        view.showOnMap(event.getLocName());
    }

    @Override
    public void onLocationAddNew(final LocationAddNewEvent event) {
        itemConfigId = event.getItemConfigId();

        view.showMap();
        view.getLocationSearchWidget().setText("");
        view.getLocationSearchWidget().setFocus(true);
        view.getSaveWidget().setVisible(true);

    }

    @Override
    public void saveLocation(final String locationName) {

        if (u.isVoid(itemConfigId)) {
            return;
        }

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        LocationsUtils.copyLocationCaches();
        MenuViewUtils.addNewLocation(this, itemConfigId, locationName);
    }

    public void saveLocationService(final boolean isDoublon, final String locationName) {

        if (isDoublon) {
            LocationsUtils.deleteCopies();
            u.fire(eventBus, new HideWaitingIndicatorEvent());
            u.fire(eventBus, new NotificationEvent(Level.WARNING, //
                    "This location " + locationName + " is already associated to this item"));
            return;
        }

        linkedinService.saveLocations( //
                //
                clientFactory.getAppState().getUserId() //
                , LocationsUtils.json_copyCacheItems() //
                , LocationsUtils.json_copyCacheReferential() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {

                        LocationsUtils.replaceCachesByCopies();

                        u.fire(eventBus, new HideWaitingIndicatorEvent());

                        view.getSaveWidget().setVisible(false);
                        u.fire(eventBus, new LocationsSuccessSaveEvent(itemConfigId));

                        final StringBuilder msg = new StringBuilder();
                        msg.append("The location \"");
                        msg.append(locationName);
                        msg.append("\" has been successfully added.");

                        u.fire(eventBus, new NotificationEvent(Level.SUCCESS, msg.toString()));
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        LocationsUtils.deleteCopies();

                        super.onFailure(caught);
                    }


                });

    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this);
        eventBus.addHandler(LocationSuccessDeleteEvent.TYPE, this);
        eventBus.addHandler(LocationAddNewEvent.TYPE, this);
        eventBus.addHandler(LocationShowOnMapEvent.TYPE, this);

        panel.setWidget(view.asWidget());

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        linkedinService.fetchProfile( //
                clientFactory.getAppState().getAccessToken() //
                , new AsyncCallbackApp<Profile>(eventBus) {

                    @Override
                    public void onSuccess(final Profile profile) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());
                        view.setProfile(profile);

                        // if the user has no registered locations
                        // then save our current cache silently
                        new Timer() {

                            @Override
                            public void run() {
                                initUserLocations(eventBus, profile);
                            }

                        }.schedule(3000); // TODO PGU Sep 21, 2012 temporary fix: fire an event when all locations are done

                        publicProfileService.fetchPreferencesOnly( //
                                clientFactory.getAppState().getUserId(), //
                                new AsyncCallbackApp<PublicProfile>(eventBus) {

                                    @Override
                                    public void onSuccess(final PublicProfile publicProfile) {

                                        final boolean isCreation = publicProfile == null;
                                        if (isCreation) {
                                            view.showPublicPreferences(null);

                                        } else {
                                            final String publicPreferences = publicProfile.getPreferences();
                                            view.showPublicPreferences(publicPreferences);

                                        }

                                        final PublicProfile updated = getUpdatedPublicProfile();

                                        publicProfileService.saveProfile( //
                                                updated, //
                                                new AsyncCallbackApp<Void>(eventBus) {

                                                    @Override
                                                    public void onSuccess(final Void result) {
                                                        // no-op
                                                    }

                                                });
                                    }

                                });
                    }

                    private void initUserLocations(final EventBus eventBus, final Profile profile) {
                        // TODO PGU Sep 25, 2012 if userAndLocations != currentUserAndLocations
                        // TODO PGU Sep 25, 2012 clean locations when not used anymore
                        if (profile.getUserAndLocations() == null) {

                            LocationsUtils.copyLocationCaches();

                            linkedinService.saveLocations( //
                                    //
                                    clientFactory.getAppState().getUserId() //
                                    , LocationsUtils.json_copyCacheItems() //
                                    , LocationsUtils.json_copyCacheReferential() //
                                    //
                                    , new AsyncCallbackApp<Void>(eventBus) {

                                        @Override
                                        public void onSuccess(final Void result) {
                                            LocationsUtils.replaceCachesByCopies();
                                        }

                                        @Override
                                        public void onFailure(final Throwable caught) {
                                            LocationsUtils.deleteCopies();
                                        }

                                    });
                        }
                    }
                });

    }

    @Override
    public void addNewLocation(final String itemConfigId) {
        // itemId for a position or an education

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory);
        editLocationActivity.start(itemConfigId, "");

    }

    @Override
    public void editLocation(final String itemConfigId, final String locName) {

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory);
        editLocationActivity.start(itemConfigId, locName);
    }

    @Override
    public void onLocationsSuccessSave(final LocationsSuccessSaveEvent event) {
        ProfileViewUtils.refreshHtmlLocationsForItem(event.getItemConfigId());
    }

    @Override
    public void showLocationOnMap(final String locationName) {
        u.fire(eventBus, new LocationShowOnMapEvent(locationName));
    }

    @Override
    public void onLocationSuccessDelete(final LocationSuccessDeleteEvent event) {
        ProfileViewUtils.refreshHtmlLocationsForItem(event.getItemConfigId());
    }

    @Override
    public void setProfileId(final String id) {
        clientFactory.getAppState().setUserId(id);
    }

    @Override
    public void setProfilePublicUrl(final String url) {
        clientFactory.getAppState().setPublicProfileUrl(url);
    }

    @Override
    public void updatePublicProfile(final String publicProfileItem) {

        final PublicProfile updated = getUpdatedPublicProfile();

        publicProfileService.saveProfile( //
                updated, //
                new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.confirmChangeOnPublicProfile(publicProfileItem);
                    }

                });
    }

    private PublicProfile getUpdatedPublicProfile() {

        final PublicProfile updated = new PublicProfile();
        updated.setUserId(clientFactory.getAppState().getUserId());

        updated.setPreferences(view.getPublicPreferences());
        updated.setProfile(view.getPublicProfile());


        // TODO PGU Sep 18, 2012 factorize this
        final int length = "http://www.linkedin.com/".length();
        final String linkedInSuffix = clientFactory.getAppState().getPublicProfileUrl().substring(length);

        updated.setUrl(linkedInSuffix);

        return updated;
    }

}
