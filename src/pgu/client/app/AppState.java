package pgu.client.app;

import pgu.shared.dto.AccessToken;

public class AppState {

    private AccessToken accessToken;
    private boolean     hasUser;

    public boolean hasUser() {
        return hasUser;
    }

    public void setHasUser(final boolean hasUser) {
        this.hasUser = hasUser;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
