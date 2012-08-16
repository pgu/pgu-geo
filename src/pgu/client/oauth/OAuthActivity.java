package pgu.client.oauth;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientUtils;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.OauthAuthorizationStart;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class OAuthActivity extends AbstractActivity implements OAuthPresenter {

    private final Place                redirectPlace;
    private final ClientFactory        clientFactory;
    private final OAuthView            view;
    private final ClientUtils          u = new ClientUtils();
    private final LinkedinServiceAsync linkedinService;

    private EventBus                   eventBus;

    public OAuthActivity(final Place place, final ClientFactory clientFactory) {
        GWT.log("redirect place: " + place);
        redirectPlace = place;
        this.clientFactory = clientFactory;
        view = clientFactory.getOAuthView();
        linkedinService = clientFactory.getLinkedinService();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        this.eventBus = eventBus;
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        u.fire(eventBus, new ShowWaitingIndicatorEvent());
        linkedinService.getLinkedinUrlAuthorization(new AsyncCallbackApp<OauthAuthorizationStart>(eventBus) {

            @Override
            public void onSuccess(final OauthAuthorizationStart oas) {
                u.fire(eventBus, new HideWaitingIndicatorEvent());

                clientFactory.getAppState().setRequestToken(oas.getRequestToken());
                // TODO PGU add to display an iframe or a link with the oas.getAuthorizationUrl();
                view.getOAuthLinkWidget().setHref(oas.getAuthorizationUrl());
            }

        });
    }

    @Override
    public void setOauthCode(final String oauthCode) {
        clientFactory.getAppState().setOAuthCode(oauthCode);
        clientFactory.getAppState().setHasUser(true);
        clientFactory.getPlaceController().goTo(redirectPlace);
    }

}
