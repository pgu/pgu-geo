package pgu.shared.dto;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EducationWithLocation implements IsSerializable, ItemWithLocation {

    private final Education education;
    private final Location  location = new Location();

    public EducationWithLocation(final Education education, final HashMap<Long, String> educationId2location) {
        this.education = education;

        final String locationName = educationId2location.get(education.getId());
        if (locationName != null) {
            location.setName(locationName);
        }

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
