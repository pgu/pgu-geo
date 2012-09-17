package pgu.client.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<ProfilePlace> {
        @Override
        public String getToken(final ProfilePlace place) {
            GWT.log("profile place");
            return "";
        }

        @Override
        public ProfilePlace getPlace(final String token) {
            GWT.log("profile place");
            return new ProfilePlace();
        }
    }

}
