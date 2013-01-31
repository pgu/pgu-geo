package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublicProfile implements IsSerializable {

    @Id
    private String profileId;

    private String preferences;
    private String mapPreferences;
    private String url;
    private String profile;
    private UserAndLocations userAndLocations;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (profileId == null ? 0 : profileId.hashCode());
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
        if (profileId == null) {
            if (other.profileId != null) {
                return false;
            }
        } else if (!profileId.equals(other.profileId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PublicProfile [userId=" + profileId + ", preferences=" + preferences + ", mapPreferences="
                + mapPreferences + ", url=" + url + ", profile=" + profile + ", userAndLocations=" + userAndLocations
                + "]";
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
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

    @Deprecated
    public UserAndLocations getUserAndLocations() {
        return userAndLocations;
    }

    public void setUserAndLocations(final UserAndLocations userAndLocations) {
        this.userAndLocations = userAndLocations;
    }

    public String getMapPreferences() {
        return mapPreferences;
    }

    public void setMapPreferences(final String mapPreferences) {
        this.mapPreferences = mapPreferences;
    }

}
