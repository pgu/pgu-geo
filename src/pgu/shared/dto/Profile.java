package pgu.shared.dto;

import pgu.shared.model.UserAndLocations;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Profile implements IsSerializable {

    private String           json;
    private UserAndLocations userAndLocations;

    public String getJson() {
        return json;
    }

    public void setJson(final String json) {
        this.json = json;
    }

    public void setUserAndLocations(final UserAndLocations userAndLocations) {
        this.userAndLocations = userAndLocations;
    }

    public UserAndLocations getUserAndLocations() {
        return userAndLocations;
    }

}
