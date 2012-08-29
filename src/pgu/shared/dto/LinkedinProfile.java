package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LinkedinProfile implements IsSerializable {

    private Location   location;
    private Educations educations;
    private Positions  positions;

    public Educations getEducations() {
        return educations;
    }

    public void setEducations(final Educations educations) {
        this.educations = educations;
    }

    public Positions getPositions() {
        return positions;
    }

    public void setPositions(final Positions positions) {
        this.positions = positions;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

}
