package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemLocation implements IsSerializable {

    private String name;
    private String lat;
    private String lng;

    @Override
    public String toString() {
        return "ItemLocation [name=" + name + ", lat=" + lat + ", lng=" + lng + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(final String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(final String lng) {
        this.lng = lng;
    }

    public ItemLocation copy() {
        final ItemLocation copy = new ItemLocation();
        copy.setLat(lat);
        copy.setLng(lng);
        copy.setName(name);
        return copy;
    }

}
