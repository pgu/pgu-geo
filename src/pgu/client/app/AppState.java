package pgu.client.app;

import pgu.shared.dto.RequestToken;

public class AppState {

    private RequestToken requestToken;

    public AppState() {
    }

    public boolean hasUser() {
        return false;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(final RequestToken requestToken) {
        this.requestToken = requestToken;
    }

}
