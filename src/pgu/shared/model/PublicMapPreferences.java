package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublicMapPreferences implements IsSerializable {

    @Id
    private String profileUrl;

    // {zoom: 2, center_lat:0.00, center_lng:0.00}
    private String values;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (profileUrl == null ? 0 : profileUrl.hashCode());
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
        final PublicMapPreferences other = (PublicMapPreferences) obj;
        if (profileUrl == null) {
            if (other.profileUrl != null) {
                return false;
            }
        } else if (!profileUrl.equals(other.profileUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MapPreferences [profileUrl=" + profileUrl + ", values=" + values + "]";
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getValues() {
        return values;
    }

    public void setValues(final String values) {
        this.values = values;
    }

}
