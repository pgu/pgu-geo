package pgu.shared.dto;

import javax.persistence.Id;

import pgu.shared.model.UserAndLocations;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublicProfile implements IsSerializable {

    @Id
    private String userId;

    private String preferences;
    private String url;
    private String profile;
    private UserAndLocations userAndLocations;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (userId == null ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PublicProfile other = (PublicProfile) obj;
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PublicProfile [userId=" + userId + ", preferences=" + preferences + ", url=" + url + ", profile="
                + profile + ", userAndLocations=" + userAndLocations + "]";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(final String preferences) {
        this.preferences = preferences;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }

    public UserAndLocations getUserAndLocations() {
        return userAndLocations;
    }

    public void setUserAndLocations(final UserAndLocations userAndLocations) {
        this.userAndLocations = userAndLocations;
    }

}
