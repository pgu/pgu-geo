package pgu.client.oauth;

import com.google.gwt.user.client.ui.IsWidget;

public interface OAuthView extends IsWidget {

    void setPresenter(OAuthPresenter presenter);

    void setOAuthUrl(String authorizationUrl);

}
