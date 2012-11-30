package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserAndLocations implements IsSerializable {

    @Id
    private String profileId;

    private String items2locations;
    private String referentialLocations;

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
        final UserAndLocations other = (UserAndLocations) obj;
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
        return "UserAndLocations [userId=" + profileId + ", items2locations=" + items2locations
                + ", referentialLocations=" + referentialLocations + "]";
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
    }

    public String getItems2locations() {
        return items2locations;
    }

    public void setItems2locations(final String items2locations) {
        this.items2locations = items2locations;
    }

    public String getReferentialLocations() {
        return referentialLocations;
    }

    public void setReferentialLocations(final String referentialLocations) {
        this.referentialLocations = referentialLocations;
    }

}
