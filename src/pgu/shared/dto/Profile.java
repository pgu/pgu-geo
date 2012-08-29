package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Profile implements IsSerializable {

    private Location location;
    private String   json;
    private String   itemId2locations;

    public String getJson() {
        return json;
    }

    public void setJson(final String json) {
        this.json = json;
    }

    public String getItemId2locations() {
        return itemId2locations;
    }

    public void setItemId2locations(final String itemId2locations) {
        this.itemId2locations = itemId2locations;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

}
