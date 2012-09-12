package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserAndLocations implements IsSerializable {

    @Id
    private String userId;

    private String items2locations;
    private String referentialLocations;

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
        final UserAndLocations other = (UserAndLocations) obj;
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
        return "UserAndLocations [userId=" + userId + ", items2locations=" + items2locations
                + ", referentialLocations=" + referentialLocations + "]";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
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
