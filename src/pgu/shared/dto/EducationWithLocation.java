package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EducationWithLocation implements IsSerializable, ItemWithLocation {

    private final Education education;
    private final Location  location = new Location();

    public EducationWithLocation(final Education education) {
        this.education = education;
    }

    @Override
    public Long getId() {
        return education.getId();
    }

    @Override
    public Location getLocation() {
        return location;
    }

}
