package pgu.client.profile;

import pgu.client.app.AppContext;
import pgu.client.app.event.LocationAddNewEvent;
import pgu.client.app.event.LocationShowOnMapEvent;
import pgu.client.app.event.LocationSuccessDeleteEvent;
import pgu.client.app.event.LocationsSuccessSaveEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.service.ProfileServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditLocationActivity extends AbstractActivity {

    private final EditLocationView               view;
    private final AppContext                     ctx;
    private final ClientHelper                   u     = new ClientHelper();
    private EventBus                             eventBus;

    private final ProfileServiceAsync            profileService;

    private String                               itemConfigId;
    private String                               locName;

    public EditLocationActivity(final ClientFactory clientFactory, final String itemConfigId, final String locName,
            final AppContext ctx) {

        this.ctx = ctx;
        this.itemConfigId = itemConfigId;
        this.locName = locName;

        view = clientFactory.getEditLocationView();

        profileService = clientFactory.getProfileService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

        this.eventBus = eventBus;
        view.setPresenter(this);

        final boolean isNew = u.isVoid(locName);

        if (isNew) {
            view.displayNewLocationWidget();

        } else {
            view.displayEditLocationWidget();
        }

        view.show();
    }

    @Override
    public void onStop() {
        itemConfigId = null;
        locName = null;
    }

    public String getItemConfigId() {
        return itemConfigId;
    }

    public String getLocationName() {
        return locName;
    }

    public void addExistingLocations(final String json_copyCacheItems, final String json_copyCacheReferential) {
        profileService.saveLocations( //
                ctx.getProfileId() //
                , json_copyCacheItems //
                , json_copyCacheReferential //
                , //
                new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.onAddExistingLocationsSuccess();
                        u.fire(eventBus, new LocationsSuccessSaveEvent(itemConfigId));
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        view.onAddExistingLocationsFailure(caught);
                        super.onFailure(caught);
                    }

                }
                //
                );
    }

    public void addNewLocation() {
        u.fire(eventBus, new LocationAddNewEvent(itemConfigId));
    }

    public void deleteLocations(final String json_copyCacheItems, final String json_copyCacheReferential) {
        profileService.saveLocations( //
                //
                ctx.getProfileId() //
                , json_copyCacheItems //
                , json_copyCacheReferential //
                //
                , new AsyncCallbackApp<Void>(eventBus) {

                    @Override
                    public void onSuccess(final Void result) {
                        view.onDeleteLocationsSuccess();
                        u.fire(eventBus, new LocationSuccessDeleteEvent(itemConfigId));
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        view.onDeleteLocationsFailure(caught);
                        super.onFailure(caught);
                    }

                });
    }

    public void displayOnMap() {
        u.fire(eventBus, new LocationShowOnMapEvent(locName));
    }

}
