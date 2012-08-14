package pgu.client.app;

import pgu.shared.dto.RequestToken;

public class AppState {

    private RequestToken requestToken;
    private String       oauthCode;
    private boolean      hasUser;

    public boolean hasUser() {
        return hasUser;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(final RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public void setOAuthCode(final String oauthCode) {
        this.oauthCode = oauthCode;
    }

    public String getOAuthCode() {
        return oauthCode;
    }

    public void setHasUser(final boolean hasUser) {
        this.hasUser = hasUser;
    }

}
