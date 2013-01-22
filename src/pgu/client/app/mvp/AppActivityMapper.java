package pgu.client.app.mvp;

import pgu.client.app.AppContext;
import pgu.client.contacts.ContactsActivity;
import pgu.client.contacts.ContactsPlace;
import pgu.client.profile.ProfileActivity;
import pgu.client.profile.ProfilePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;
    private final AppContext    ctx;

    public AppActivityMapper(final ClientFactory clientFactory, final AppContext ctx) {
        this.clientFactory = clientFactory;
        this.ctx = ctx;
    }

    @Override
    public Activity getActivity(final Place place) {

        // TODO PGU Nov 18, 2012 if user is not logged in then window.location.replace("signinplace");

        if (place instanceof ProfilePlace) {
            return new ProfileActivity((ProfilePlace) place, clientFactory, ctx);

        } else if (place instanceof ContactsPlace) {
            return new ContactsActivity((ContactsPlace) place, clientFactory, ctx);

        }

        return null;
    }

}
