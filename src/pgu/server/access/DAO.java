package pgu.server.access;

import pgu.shared.model.BaseContacts;
import pgu.shared.model.BaseProfile;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.ChartsPreferences;
import pgu.shared.model.ContactsNumberByCountry;
import pgu.shared.model.Country2ContactNames;
import pgu.shared.model.Country2ContactNumber;
import pgu.shared.model.FusionUrls;
import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.PublicPreferences;
import pgu.shared.model.PublicProfile;
import pgu.shared.model.UserAndLocations;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {

    static {
        ObjectifyService.register(UserAndLocations.class);
        ObjectifyService.register(PublicProfile.class);

        ObjectifyService.register(Country2ContactNames.class);
        ObjectifyService.register(Country2ContactNumber.class);

        ObjectifyService.register(ChartsPreferences.class);
        ObjectifyService.register(FusionUrls.class);

        ObjectifyService.register(ContactsNumberByCountry.class);

        ObjectifyService.register(ProfileLocations.class);
        ObjectifyService.register(PublicPreferences.class);

        ObjectifyService.register(BasePublicProfile.class);
        ObjectifyService.register(BaseProfile.class);
        ObjectifyService.register(BaseContacts.class);

        ObjectifyService.register(MapPreferences.class);
    }

}
