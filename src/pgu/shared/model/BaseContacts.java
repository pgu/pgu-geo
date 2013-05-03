package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BaseContacts implements IsSerializable {

    @Id
    private String profileId;

    private int    totalNbOfContacts;
    private String value;

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
        final BaseContacts other = (BaseContacts) obj;
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
        return "BaseContacts [profileId=" + profileId + ", totalNbOfContacts=" + totalNbOfContacts + ", value=" + value + "]";
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public int getTotalNbOfContacts() {
        return totalNbOfContacts;
    }

    public void setTotalNbOfContacts(final int totalNbOfContacts) {
        this.totalNbOfContacts = totalNbOfContacts;
    }

}
