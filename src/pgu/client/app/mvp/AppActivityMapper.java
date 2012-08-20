package pgu.client.app.mvp;

import pgu.client.contacts.ContactsActivity;
import pgu.client.contacts.ContactsPlace;
import pgu.client.profile.ProfileActivity;
import pgu.client.profile.ProfilePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public AppActivityMapper(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(final Place place) {

        // TODO PGU Aug 20, 2012 to restore
        // if (!clientFactory.getAppState().hasUser()) {
        // return new OAuthActivity(place, clientFactory);
        //
        // }

        if (place instanceof ProfilePlace) {
            return new ProfileActivity((ProfilePlace) place, clientFactory);

        } else if (place instanceof ContactsPlace) {
            return new ContactsActivity((ContactsPlace) place, clientFactory);

        }

        return null;
    }

}
