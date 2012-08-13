package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Location implements IsSerializable {

    private String  name;
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

}
