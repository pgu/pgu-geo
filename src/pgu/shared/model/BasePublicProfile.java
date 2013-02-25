package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BasePublicProfile implements IsSerializable, HasProfileUrlAsKey {

    @Id
    private String profileUrl;

    private String value;

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
        final BasePublicProfile other = (BasePublicProfile) obj;
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
        return "BasePublicProfile [profileUrl=" + profileUrl + ", value=" + value + "]";
    }

    @Override
    public String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
