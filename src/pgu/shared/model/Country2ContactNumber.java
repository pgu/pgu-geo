package pgu.shared.model;

import java.util.HashMap;
import java.util.HashSet;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country2ContactNumber implements IsSerializable {

    @Id
    private String                           userId;

    private HashMap<String, HashSet<String>> code2locationNames;
    private HashMap<String, Integer>         code2contactNumber;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (code2contactNumber == null ? 0 : code2contactNumber.hashCode());
        result = prime * result + (code2locationNames == null ? 0 : code2locationNames.hashCode());
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
        if (code2contactNumber == null) {
            if (other.code2contactNumber != null) {
                return false;
            }
        } else if (!code2contactNumber.equals(other.code2contactNumber)) {
            return false;
        }
        if (code2locationNames == null) {
            if (other.code2locationNames != null) {
                return false;
            }
        } else if (!code2locationNames.equals(other.code2locationNames)) {
            return false;
        }
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

    public HashMap<String, HashSet<String>> getCode2locationNames() {
        return code2locationNames;
    }

    public void setCode2locationNames(final HashMap<String, HashSet<String>> code2locationNames) {
        this.code2locationNames = code2locationNames;
    }

    public HashMap<String, Integer> getCode2contactNumber() {
        return code2contactNumber;
    }

    public void setCode2contactNumber(final HashMap<String, Integer> code2contactNumber) {
        this.code2contactNumber = code2contactNumber;
    }

}
