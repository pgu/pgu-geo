package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublicLocations implements IsSerializable, HasProfileUrlAsKey {

    @Id
    private String profileUrl;

    // item2locations/cache_items: {"education,1":["Paris","Nantes"],"experience,1":["Madrid"]}
    private String items2locations;

    //    referential/cache_referential: {"Paris":{"lat":1.2323,"lng":4.5555},"Nantes":{"lat":9.99,"lng":2.22}}
    private String referentialLocations;

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
        final PublicLocations other = (PublicLocations) obj;
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
        return "ProfileLocations [profileUrl=" + profileUrl + ", items2locations=" + items2locations
                + ", referentialLocations=" + referentialLocations + "]";
    }

    @Override
    public String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
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
