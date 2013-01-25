package pgu.client.profile;

import pgu.client.app.utils.HasNotifications;

public interface EditLocationView extends HasNotifications {

    void setPresenter(EditLocationActivity editLocationActivity);

    void show();

    void displayNewLocationWidget();

    void displayEditLocationWidget();

    void onAddExistingLocationsSuccess();

    void onAddExistingLocationsFailure(Throwable caught);

    void onDeleteLocationsSuccess();

    void onDeleteLocationsFailure(Throwable caught);

}
