package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OauthAuthorizationStart implements IsSerializable {

    private String       authorizationUrl;
    private RequestToken requestToken;

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(final String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(final RequestToken requestToken) {
        this.requestToken = requestToken;
    }

}
