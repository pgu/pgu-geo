package pgu.client.app;

import pgu.shared.dto.AccessToken;

public class AppState {

    private String      userId;
    private String      publicProfileUrl;
    private AccessToken accessToken;
    private boolean     hasUser;

    @Deprecated
    public boolean hasUser() {
        return hasUser;
    }

    @Deprecated
    public void setHasUser(final boolean hasUser) {
        this.hasUser = hasUser;
    }

    @Deprecated
    public AccessToken getAccessToken() {
        return accessToken;
    }

    @Deprecated
    public void setAccessToken(final AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Deprecated
    public String getUserId() {
        return userId;
    }

    @Deprecated
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    @Deprecated
    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    @Deprecated
    public void setPublicProfileUrl(final String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }


}
