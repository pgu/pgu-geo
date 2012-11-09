package pgu.shared.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country2ContactNames implements IsSerializable {

    @Id
    private String userId;

    private HashMap<String, ArrayList<String>> values;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (userId == null ? 0 : userId.hashCode());
        result = prime * result + (values == null ? 0 : values.hashCode());
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
        final Country2ContactNames other = (Country2ContactNames) obj;
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        if (values == null) {
            if (other.values != null) {
                return false;
            }
        } else if (!values.equals(other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Country2ContactNames [userId=" + userId + ", values=" + values + "]";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public HashMap<String, ArrayList<String>> getValues() {
        return values;
    }

    public void setValues(final HashMap<String, ArrayList<String>> values) {
        this.values = values;
    }

}
