package pgu.shared.dto;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Profile implements IsSerializable {

    private String                   json;
    private HashMap<Integer, String> id2location;

    public String getJson() {
        return json;
    }

    public void setJson(final String json) {
        this.json = json;
    }

    public HashMap<Integer, String> getId2location() {
        return id2location;
    }

    public void setId2location(final HashMap<Integer, String> id2location) {
        this.id2location = id2location;
    }

}
