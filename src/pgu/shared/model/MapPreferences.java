package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MapPreferences implements IsSerializable {

    @Id
    private String profileId;

    // {zoom: 2, center_lat:0.00, center_lng:0.00}
    private String values;

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
        final MapPreferences other = (MapPreferences) obj;
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
        return "MapPreferences [profileId=" + profileId + ", values=" + values + "]";
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(final String values) {
        this.values = values;
    }

}
