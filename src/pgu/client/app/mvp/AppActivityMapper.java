package pgu.client.app.mvp;

import pgu.client.contacts.ContactsActivity;
import pgu.client.contacts.ContactsPlace;
import pgu.client.profile.ProfileActivity;
import pgu.client.profile.ProfilePlace;
import pgu.client.pub.PublicActivity;
import pgu.client.pub.PublicPlace;

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

        // if !PublicProfile
        //        if (!clientFactory.getAppState().hasUser()) {
        //            return new OAuthActivity(place, clientFactory);
        //
        //        }

        if (place instanceof ProfilePlace) {
            return new ProfileActivity((ProfilePlace) place, clientFactory);

        } else if (place instanceof ContactsPlace) {
            return new ContactsActivity((ContactsPlace) place, clientFactory);

        } else if (place instanceof PublicPlace) {
            return new PublicActivity((PublicPlace) place, clientFactory);

        }

        return null;
    }

}
