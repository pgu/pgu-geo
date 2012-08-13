package pgu.client.profile;

import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity implements ProfilePresenter {

    private final ClientFactory clientFactory;
    private final ProfileView   view;

    private final ClientUtils   u = new ClientUtils();

    private EventBus            eventBus;

    public ProfileActivity(final ProfilePlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getProfileView();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        panel.setWidget(view.asWidget());
    }

}
