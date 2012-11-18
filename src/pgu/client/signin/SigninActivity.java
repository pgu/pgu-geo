package pgu.client.signin;

import pgu.client.app.utils.ClientUtils;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SigninActivity {

    private final SigninView                     view;
    private EventBus                             eventBus;

    private final ClientUtils                    u     = new ClientUtils();

    public SigninActivity(final SigninView view) {
        this.view = view;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;

        //        view.addSaveLocationHandler(this);
        //        eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this);

        view.reparseByLinkedin();

        //        u.fire(eventBus, new ShowWaitingIndicatorEvent());
    }

}
