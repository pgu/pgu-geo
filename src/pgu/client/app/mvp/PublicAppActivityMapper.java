package pgu.client.app.mvp;

import pgu.client.pub.PublicActivity;
import pgu.client.pub.PublicPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class PublicAppActivityMapper implements ActivityMapper {

    private final PublicClientFactory clientFactory;

    public PublicAppActivityMapper(final PublicClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }


    @Override
    public Activity getActivity(final Place place) {

        if (place instanceof PublicPlace) {
            return new PublicActivity((PublicPlace) place, clientFactory);

        }

        return null;
    }

}
