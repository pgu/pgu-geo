package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Position implements IsSerializable, ItemWithLocation {

    private Long     id;
    private Location location;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

}
