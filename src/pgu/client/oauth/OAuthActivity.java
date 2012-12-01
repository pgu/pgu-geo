package pgu.client.oauth;

import pgu.client.app.event.HideWaitingIndicatorEvent;
import pgu.client.app.event.ShowWaitingIndicatorEvent;
import pgu.client.app.mvp.ClientFactory;
import pgu.client.app.utils.AsyncCallbackApp;
import pgu.client.app.utils.ClientHelper;
import pgu.client.service.LinkedinServiceAsync;
import pgu.shared.dto.AccessToken;
import pgu.shared.dto.OauthAuthorizationStart;
import pgu.shared.dto.RequestToken;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class OAuthActivity extends AbstractActivity implements OAuthPresenter {

    private final Place                redirectPlace;
    private final ClientFactory        clientFactory;
    private final OAuthView            view;
    private final ClientHelper          u = new ClientHelper();
    private final LinkedinServiceAsync linkedinService;

    private EventBus                   eventBus;
    private RequestToken               requestToken;

    public OAuthActivity(final Place place, final ClientFactory clientFactory) {
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

                requestToken = oas.getRequestToken();
                view.setOAuthUrl(oas.getAuthorizationUrl());
            }

        });
    }

    @Override
    public void setOauthCode(final String oauthCode) {
        linkedinService.getAccessToken(oauthCode, requestToken, new AsyncCallbackApp<AccessToken>(eventBus) {

            @Override
            public void onSuccess(final AccessToken accessToken) {
                clientFactory.getAppState().setAccessToken(accessToken);
                clientFactory.getAppState().setHasUser(true);
                eventBus.fireEvent(new PlaceChangeEvent(redirectPlace));
            }

        });

    }
}
