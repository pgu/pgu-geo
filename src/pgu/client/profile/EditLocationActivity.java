package pgu.client.profile;

import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;

import com.google.web.bindery.event.shared.EventBus;

public class EditLocationActivity {

    private final EditLocationView view;
    private EventBus               eventBus;
    private final ClientUtils      u = new ClientUtils();

    public EditLocationActivity(final ClientFactory clientFactory) {
        view = clientFactory.getEditLocationView();
    }

    public void start(final EventBus eventBus) {
        this.eventBus = eventBus;
        // TODO PGU Aug 27, 2012 behaviour of the view
        // click on a location: popup with: information: name, lat, lng; actions: show on the map, delete it
        // click on new location: popup with: btn add a new location (redirects to the map), btn for each existing
        // location, btn for save selected locations
    }

}
