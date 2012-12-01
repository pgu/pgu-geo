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
import pgu.client.profile.event.FetchMapPreferencesEvent;
import pgu.client.profile.event.FetchProfileLocationsEvent;
import pgu.client.profile.event.FetchPublicPreferencesEvent;
import pgu.client.profile.event.SaveLocationEvent;
import pgu.client.profile.event.SaveLocationsEvent;
import pgu.client.profile.event.SaveMapPreferencesEvent;
import pgu.client.profile.event.SavePublicPreferencesEvent;
import pgu.client.profile.event.SavePublicProfileEvent;
import pgu.client.service.ProfileServiceAsync;
import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
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
, SaveLocationsEvent.Handler //
, FetchProfileLocationsEvent.Handler //
, SavePublicProfileEvent.Handler //
, SavePublicPreferencesEvent.Handler //
, FetchMapPreferencesEvent.Handler //
{

    private final ClientFactory                  clientFactory;
    private final ProfileView                    view;
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
        hRegs.add(view.addLocationShowOnMapHandler(this));
        hRegs.add(view.addSaveLocationHandler(this));
        hRegs.add(view.addSaveMapPreferencesHandler(this));

        hRegs.add(view.addFetchProfileLocationsHandler(this));
        hRegs.add(view.addFetchPublicPreferencesHandler(this));
        hRegs.add(view.addFetchMapPreferencesHandler(this));

        hRegs.add(view.addSaveLocationsHandler(this));
        hRegs.add(view.addSavePublicProfileHandler(this));
        hRegs.add(view.addSavePublicPreferencesHandler(this));

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

        if (view.isProfileSetInView()) {
            return;
        }

        profileService.saveProfile( //
                //
                ctx.getProfileId() //
                , getJsonProfile() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // do nothing
                    }

                });

        view.showProfile();
    }

    private String getJsonProfile() {
        return view.getJsonRawProfile();
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
        view.refreshHtmlLocationsForItem(event.getItemConfigId());
    }

    @Override
    public void onLocationSuccessDelete(final LocationSuccessDeleteEvent event) {
        view.refreshHtmlLocationsForItem(event.getItemConfigId());
    }

    @Override
    public void onSaveLocation(final SaveLocationEvent event) {
        if (u.isVoid(itemConfigId)) {
            return;
        }

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        final String locationName = event.getLocationName();
        final String lat = event.getLat();
        final String lng = event.getLng();

        final boolean isDoublon = view.isLocationDoublon(itemConfigId, locationName, lat, lng);
        if (isDoublon) {

            u.fire(eventBus, new HideWaitingIndicatorEvent());
            u.fire(eventBus, new NotificationEvent(Level.WARNING, //
                    "This location " + locationName + " is already associated to this item"));

        } else {

            view.copyLocationCaches();
            view.addGeopointToCopyCache(locationName, lat, lng);
            view.addLocation2ItemInCopyCache(itemConfigId, locationName);

            profileService.saveLocations( //
                    //
                    ctx.getProfileId() //
                    , view.json_copyCacheItems() //
                    , view.json_copyCacheReferential() //
                    //
                    , new AsyncCallbackApp<Void>(eventBus) {

                        @Override
                        public void onSuccess(final Void result) {

                            view.replaceCachesByCopies();

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
                            view.deleteCopies();

                            super.onFailure(caught);
                        }

                    });
        }
    }

    public void showNotificationWarning(final String msg) {
        view.showNotificationWarning(msg);
    }

    @Override
    public void onSaveMapPreferences(final SaveMapPreferencesEvent event) {

        profileService.saveMapPreferences( //
                //
                ctx.getProfileId() //
                , event.getMapPreferences() //
                //
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
    public void onSaveLocations(final SaveLocationsEvent event) {

        view.removeUnusedLocations();
        view.copyLocationCaches();

        profileService.saveLocations( //
                //
                ctx.getProfileId() //
                , view.json_copyCacheItems() //
                , view.json_copyCacheReferential() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.replaceCachesByCopies();
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        view.deleteCopies();
                    }

                });
    }

    @Override
    public void onFetchPublicPreferences(final FetchPublicPreferencesEvent event) {
        profileService.fetchPublicPreferences(ctx.getProfileId(), new AsyncCallbackApp<PublicPreferences>(eventBus) {

            @Override
            public void onSuccess(final PublicPreferences result) {
                view.setPublicPreferencesInfo(result);
            }

        });
    }

    @Override
    public void onFetchProfileLocations(final FetchProfileLocationsEvent event) {
        profileService.fetchProfileLocations(ctx.getProfileId(), new AsyncCallbackApp<ProfileLocations>(eventBus) {

            @Override
            public void onSuccess(final ProfileLocations profileLocations) {
                view.setProfileLocations(profileLocations);
            }

        });
    }

    @Override
    public void onSavePublicProfile(final SavePublicProfileEvent event) {
        final String jsonPublicProfile = view.getJsonPublicProfile();

        profileService.savePublicProfile( //
                //
                ctx.getProfileId() //
                , jsonPublicProfile //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    @Override
    public void onSavePublicPreferences(final SavePublicPreferencesEvent event) {

        final String type = event.getType();

        profileService.savePublicPreferences( //
                //
                ctx.getProfileId() //
                , view.getJsonPublicPreferences() //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.confirmChangeOnPublicProfile(type);
                    }

                });
    }

    @Override
    public void onFetchMapPreferences(final FetchMapPreferencesEvent event) {
        profileService.fetchMapPreferences(ctx.getProfileId(), new AsyncCallbackApp<MapPreferences>(eventBus) {

            @Override
            public void onSuccess(final MapPreferences result) {
                view.setMapPreferences(result.getValues());
            }
        });
    }

}
