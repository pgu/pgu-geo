package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country2ContactNumber implements IsSerializable {

    @Id
    private String userId;

    private String code2locationNames;
    private String code2contactNumber;

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
        final Country2ContactNumber other = (Country2ContactNumber) obj;
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
        return "Country2ContactNumber [userId=" + userId + ", code2locationNames=" + code2locationNames
                + ", code2contactNumber=" + code2contactNumber + "]";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
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
