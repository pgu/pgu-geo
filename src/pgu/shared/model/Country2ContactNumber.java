package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country2ContactNumber implements IsSerializable {

    @Id
    private String profileId;

    private String code2locationNames;
    private String code2contactNumber;

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
        final Country2ContactNumber other = (Country2ContactNumber) obj;
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
        return "Country2ContactNumber [userId=" + profileId + ", code2locationNames=" + code2locationNames
                + ", code2contactNumber=" + code2contactNumber + "]";
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
    }

    public String getCode2locationNames() {
        return code2locationNames;
    }

    public void setCode2locationNames(final String code2locationNames) {
        this.code2locationNames = code2locationNames;
    }

    public String getCode2contactNumber() {
        return code2contactNumber;
    }

    public void setCode2contactNumber(final String code2contactNumber) {
        this.code2contactNumber = code2contactNumber;
    }

}
