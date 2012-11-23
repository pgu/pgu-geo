package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.AppContext;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ProfileLoadedEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.Level;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.profile.event.FetchCustomLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SavePublicLocationsEvent;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.client.profile.ui.ProfileViewHelper;
import pgu.client.service.LinkedinServiceAsync;
import pgu.client.service.ProfileServiceAsync;
import pgu.client.service.PublicProfileServiceAsync;
import pgu.shared.model.PublicProfile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ProfileActivity extends AbstractActivity implements ProfilePresenter //
, LocationsSuccessSaveEvent.Handler //
, LocationSuccessDeleteEvent.Handler //
, LocationAddNewEvent.Handler //
, LocationShowOnMapEvent.Handler //
, SaveLocationEvent.Handler //
, SaveMapPreferencesEvent.Handler //
, ShowdownLoadedEvent.Handler //
, MapsApiLoadedEvent.Handler //
, ProfileLoadedEvent.Handler //
, FetchPublicPreferencesEvent.Handler //
, SavePublicLocationsEvent.Handler //
, FetchCustomLocationsEvent.Handler //
, SavePublicProfileEvent.Handler //
{

    private final ClientFactory                  clientFactory;
    private final ProfileView                    view;
    private final LinkedinServiceAsync           linkedinService;
    private final PublicProfileServiceAsync      publicProfileService;
    private final ProfileServiceAsync            profileService;
    private final AppContext                     ctx;

    private final ClientUtils                    u     = new ClientUtils();

    private EventBus                             eventBus;
    private String                               itemConfigId;

    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();

    public ProfileActivity(final ProfilePlace place, final ClientFactory clientFactory, final AppContext ctx) {
        this.clientFactory = clientFactory;
        this.ctx = ctx;
        view = clientFactory.getProfileView();
        linkedinService = clientFactory.getLinkedinService();
        publicProfileService = clientFactory.getPublicProfileService();
        profileService = clientFactory.getProfileService();
    }

    @Override
    public void onLocationShowOnMap(final LocationShowOnMapEvent event) {
        view.showOnMap(event.getLocName());
    }

    @Override
    public void onLocationAddNew(final LocationAddNewEvent event) {
        itemConfigId = event.getItemConfigId();

        view.showSaveWidget();
    }

    private boolean hasToShowProfile = false;

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        hRegs.clear();
        hRegs.add(view.addSaveLocationHandler(this));
        hRegs.add(view.addLocationShowOnMapHandler(this));
        hRegs.add(view.addSaveMapPreferencesHandler(this));

        hRegs.add(view.addFetchCustomLocationsHandler(this));
        hRegs.add(view.addFetchPublicPreferencesHandler(this));
        hRegs.add(view.addSavePublicLocationsHandler(this));

        hRegs.add(view.addSavePublicProfileHandler(this));

        hRegs.add(eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(LocationSuccessDeleteEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(LocationAddNewEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(LocationShowOnMapEvent.TYPE, this));

        hRegs.add(eventBus.addHandler(ShowdownLoadedEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(MapsApiLoadedEvent.TYPE, this));

        hRegs.add(eventBus.addHandler(ProfileLoadedEvent.TYPE, this));

        panel.setWidget(view.asWidget());

        if (isAppReady(ctx)) {
            showProfile();

        } else {
            hasToShowProfile = true;
        }
    }

    private void showProfile() {
        view.showProfile();

        // TODO PGU Nov 20, 2012 show waiting indicator while loading the app
        // u.fire(eventBus, new ShowWaitingIndicatorEvent());
        // linkedinService.fetchProfile( //
        // clientFactory.getAppState().getAccessToken() //
        // , new AsyncCallbackApp<Profile>(eventBus) {
        //
        // @Override
        // public void onSuccess(final Profile profile) {
        // // u.fire(eventBus, new HideWaitingIndicatorEvent());
        // // view.setProfile(profile);
        //
        // }
        // });
    }

    private void fetchPublicPreferences() {
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

                        // update public preferences
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

    private void saveLocationsAsync() {
        new Timer() {

            @Override
            public void run() {
                initUserLocations(eventBus);
            }

            // TODO PGU Sep 21, 2012 temporary fix: fire an event when all locations are done
        }.schedule(3000);
    }

    private void initUserLocations(final EventBus eventBus) {
        // TODO PGU Sep 25, 2012 if userAndLocations != currentUserAndLocations
        // TODO PGU Sep 25, 2012 clean locations when not used anymore
        // if (profile.getUserAndLocations() == null) {
        if (!userHasLocations()) {

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

    private native boolean userHasLocations() /*-{
		// TODO profile.getUserAndLocations() == null
		return true;
    }-*/;

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
        ProfileViewHelper.refreshHtmlLocationsForItem(event.getItemConfigId());
    }

    @Override
    public void onLocationSuccessDelete(final LocationSuccessDeleteEvent event) {
        ProfileViewHelper.refreshHtmlLocationsForItem(event.getItemConfigId());
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

    @Override
    public void onSaveLocation(final SaveLocationEvent event) {
        if (u.isVoid(itemConfigId)) {
            return;
        }

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        LocationsUtils.copyLocationCaches();

        final String locationName = event.getLocationName();
        final String lat = event.getLat();
        final String lng = event.getLng();

        final boolean isDoublon = LocationsUtils.isDoublon(itemConfigId, locationName, lat, lng);
        if (isDoublon) {

            LocationsUtils.deleteCopies();
            u.fire(eventBus, new HideWaitingIndicatorEvent());
            u.fire(eventBus, new NotificationEvent(Level.WARNING, //
                    "This location " + locationName + " is already associated to this item"));

        } else {
            LocationsUtils.addGeopointToCopyCache(locationName, lat, lng);
            LocationsUtils.addLocation2ItemInCopyCache(itemConfigId, locationName);

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

                            view.hideSaveWidget();

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

        // TODO PGU we should have [name, item_id, lat and lng]
        // and call directly the service
        // ProfileViewUtils.addNewLocation(this, itemConfigId, locationName);
    }

    @Deprecated
    public void saveLocationService(final boolean isDoublon, final String locationName) {

    }

    public void showNotificationWarning(final String msg) {
        view.showNotificationWarning(msg);
    }

    @Override
    public void onSaveMapPreferences(final SaveMapPreferencesEvent event) {
        publicProfileService.saveMapPreferences( //
                clientFactory.getAppState().getUserId() //
                , event.getMapPreferences() //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    @Override
    public void onStop() {

        for (HandlerRegistration hReg : hRegs) {
            hReg.removeHandler();
            hReg = null;
        }

        hRegs.clear();

        super.onStop();
    }

    @Override
    public void onShowdownLoaded(final ShowdownLoadedEvent event) {
        showProfileAsync();
    }

    @Override
    public void onMapsApiLoaded(final MapsApiLoadedEvent event) {
        showProfileAsync();
    }

    @Override
    public void onProfileLoaded(final ProfileLoadedEvent event) {
        showProfileAsync();
    }

    private void showProfileAsync() {
        if (hasToShowProfile && isAppReady(ctx)) {
            hasToShowProfile = false;
            showProfile();
        }
    }

    private boolean areExternalApisLoaded(final AppContext ctx) {
        return ctx.isShowdownLoaded() //
                && ctx.isMapsApiLoaded() //
                ;
    }

    private boolean isAppReady(final AppContext ctx) {
        return ctx.isProfileLoaded() && areExternalApisLoaded(ctx);
    }

    @Override
    public void onSavePublicLocations(final SavePublicLocationsEvent event) {
        // if the user has no registered locations
        // then save our current cache silently
        saveLocationsAsync();
    }

    @Override
    public void onFetchPublicPreferences(final FetchPublicPreferencesEvent event) {
        // TODO PGU Nov 20, 2012 fetch preferences in parallel (+event+profileState)
        // TODO PGU Nov 20, 2012 move this method inside a 'profile service' and not in the public
        // service
        // TODO PGU Nov 20, 2012 create a small entity only for the public preferences
        fetchPublicPreferences();
    }

    @Override
    public void onFetchCustomLocations(final FetchCustomLocationsEvent event) {
        // TODO PGU Nov 22, 2012
        //        LocationsUtils.initCaches(profile.getUserAndLocations());
    }

    @Override
    public void onSavePublicProfile(final SavePublicProfileEvent event) {
        // TODO Auto-generated method stub

    }

}
