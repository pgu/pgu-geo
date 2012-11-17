package pgu.client.signin;

import java.util.ArrayList;

import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.ClientUtils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SigninActivity extends AbstractActivity {

    private final ClientFactory             clientFactory;
    private final SigninView               view;

    private final ClientUtils               u = new ClientUtils();

    private EventBus                        eventBus;

    private final ArrayList<HandlerRegistration> hRegs = new ArrayList<HandlerRegistration>();

    public SigninActivity(final SigninPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        view = clientFactory.getSigninView();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        hRegs.clear();
        //        hRegs.add(view.addSaveLocationHandler(this));
        //        eventBus.addHandler(LocationsSuccessSaveEvent.TYPE, this);

        panel.setWidget(view.asWidget());
        view.reparseByLinkedin();

        u.fire(eventBus, new ShowWaitingIndicatorEvent());

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

}
