package pgu.client.app;

import pgu.shared.dto.AccessToken;

public class AppState {

    private String      userId;
    private String      publicProfileUrl;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    public void setPublicProfileUrl(final String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }


}
