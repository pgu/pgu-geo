package pgu.shared.dto;

import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.PublicLocations;
import pgu.shared.model.PublicMapPreferences;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FullPublicProfile implements IsSerializable {

    private BasePublicProfile    basePublicProfile;
    private PublicLocations      publicLocations;
    private PublicMapPreferences publicMapPreferences;

    public BasePublicProfile getBasePublicProfile() {
        return basePublicProfile;
    }

    public void setBasePublicProfile(final BasePublicProfile basePublicProfile) {
        this.basePublicProfile = basePublicProfile;
    }

    public PublicLocations getPublicLocations() {
        return publicLocations;
    }

    public void setPublicLocations(final PublicLocations publicLocations) {
        this.publicLocations = publicLocations;
    }

    public PublicMapPreferences getPublicMapPreferences() {
        return publicMapPreferences;
    }

    public void setPublicMapPreferences(final PublicMapPreferences publicMapPreferences) {
        this.publicMapPreferences = publicMapPreferences;
    }

}
