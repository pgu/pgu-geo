package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LatLng implements IsSerializable {

    private String lat;
    private String lng;

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

    @Override
    public String toString() {
        return "LatLng [lat=" + lat + ", lng=" + lng + "]";
    }

}
