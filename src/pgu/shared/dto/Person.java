package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Person implements IsSerializable {

    private String   firstName;
    private String   lastName;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

}
