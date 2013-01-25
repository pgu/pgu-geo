package pgu.client.profile;

import java.util.ArrayList;

import pgu.client.app.AppContext;
import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.event.MapsApiLoadedEvent;
import pgu.client.app.event.NotificationEvent;
import pgu.client.app.event.ProfileLoadedEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.event.ShowdownLoadedEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.app.utils.Level;
import pgu.client.service.ProfileServiceAsync;
import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class ProfileActivity extends AbstractActivity implements //
LocationsSuccessSaveEvent.Handler //
, LocationSuccessDeleteEvent.Handler //
, LocationAddNewEvent.Handler //
, ShowdownLoadedEvent.Handler //
, MapsApiLoadedEvent.Handler //
, ProfileLoadedEvent.Handler //
{

    private final ClientFactory                  clientFactory;
    private final ProfileView                    view;
    private final AppContext                     ctx;
    private final ClientHelper                    u     = new ClientHelper();
    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();
    private EventBus                             eventBus;

    private final ProfileServiceAsync            profileService;

    private boolean                              hasToShowProfile = false;

    public ProfileActivity(final ProfilePlace place, final ClientFactory clientFactory, final AppContext ctx) {
        this.clientFactory = clientFactory;
        this.ctx = ctx;
        view = clientFactory.getProfileView();
        profileService = clientFactory.getProfileService();
    }

    @Override
    public void onLocationAddNew(final LocationAddNewEvent event) {
        final String itemConfigId = event.getItemConfigId();

        view.showSaveWidget(itemConfigId);
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        hRegs.clear();

        hRegs.add(eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this));
        hRegs.add(eventBus.addHandler(LocationSuccessDeleteEvent.TYPE, this));

        hRegs.add(eventBus.addHandler(LocationAddNewEvent.TYPE, this));

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

    public void addNewLocation(final String itemConfigId) {
        // itemId for a position or an education

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory, itemConfigId, "", ctx);
        editLocationActivity.start(null, eventBus);
    }

    public void editLocation(final String itemConfigId, final String locName) {

        final EditLocationActivity editLocationActivity = new EditLocationActivity(clientFactory, itemConfigId, locName, ctx);
        editLocationActivity.start(null, eventBus);
    }

    @Override
    public void onLocationsSuccessSave(final LocationsSuccessSaveEvent event) {
        view.onLocationsSuccessSave(event.getItemConfigId());
    }

    @Override
    public void onLocationSuccessDelete(final LocationSuccessDeleteEvent event) {
        view.onLocationSuccessDelete(event.getItemConfigId());
    }

    public void showNotificationWarning(final String msg) {
        view.showNotificationWarning(msg);
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

    public void saveMapPreferences(final String mapPreferences) {
        profileService.saveMapPreferences( //
                //
                ctx.getProfileId() //
                , mapPreferences //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        // no-op
                    }

                });
    }

    public void saveLastSearchLocation(final String locationName, final String lat, final String lng //
            , final String jsonCopyCacheItems, final String jsonCopyCacheReferential //
            , final String itemConfigIdToSave) {

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

        profileService.saveLocations( //
                //
                ctx.getProfileId() //
                , jsonCopyCacheItems //
                , jsonCopyCacheReferential //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        u.fire(eventBus, new HideWaitingIndicatorEvent());
                        view.onSaveLastSearchLocationSuccess(locationName, itemConfigIdToSave);
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        view.onSaveLastSearchLocationFailure(caught);
                        super.onFailure(caught);
                    }

                });

    }

    public void sendNotif(final Level level, final String msg) {
        u.fire(eventBus, new NotificationEvent(level, msg));
    }

    public void fetchPublicPreferences() {
        profileService.fetchPublicPreferences( //
                ctx.getProfileId() //
                , new AsyncCallbackApp<PublicPreferences>(eventBus) {

                    @Override
                    public void onSuccess(final PublicPreferences result) {
                        view.onFetchPublicPreferencesSuccess(result);
                    }

                });
    }

    public void updateLocationsSilently(final String json_copyCacheItems, final String json_copyCacheReferential) {
        profileService.saveLocations( //
                //
                ctx.getProfileId() //
                , json_copyCacheItems //
                , json_copyCacheReferential //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.onUpdateLocationsSilentlySuccess();
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        view.onUpdateLocationsSilentlyFailure(caught);
                    }

                });
    }

    public void fetchProfileLocations() {
        profileService.fetchProfileLocations( //
                ctx.getProfileId() //
                , new AsyncCallbackApp<ProfileLocations>(eventBus) {

                    @Override
                    public void onSuccess(final ProfileLocations profileLocations) {
                        view.onFetchProfileLocationsSuccess(profileLocations);
                    }

                });
    }

    public void updatePublicProfileSilently(final String jsonPublicProfile) {
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

    public void savePublicPreferences(final String public_preference, final String jsonPublicPreferences) {
        profileService.savePublicPreferences( //
                //
                ctx.getProfileId() //
                , jsonPublicPreferences //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.confirmChangeOnPublicProfile(public_preference);
                    }

                });
    }

    public void fetchMapPreferences() {
        profileService.fetchMapPreferences( //
                ctx.getProfileId() //
                , new AsyncCallbackApp<MapPreferences>(eventBus) {

                    @Override
                    public void onSuccess(final MapPreferences result) {
                        view.onFetchMapPreferencesSuccess(result.getValues());
                    }
                });
    }

}
