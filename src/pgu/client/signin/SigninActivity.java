package pgu.client.signin;

import pgu.client.app.utils.ClientHelper;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SigninActivity {

    private final SigninView                     view;
    private EventBus                             eventBus;

    private final ClientHelper                    u     = new ClientHelper();

    public SigninActivity(final SigninView view) {
        this.view = view;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        panel.setWidget(view.asWidget());

        //        view.addSaveLocationHandler(this);
        //        eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this);

        view.reparseByLinkedin();

        //        u.fire(eventBus, new ShowWaitingIndicatorEvent());
    }

}
