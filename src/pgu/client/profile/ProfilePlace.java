package pgu.client.profile;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ProfilePlace extends Place {

    @Prefix("profile")
    public static class Tokenizer implements PlaceTokenizer<ProfilePlace> {
        @Override
        public String getToken(final ProfilePlace place) {
            return "";
        }

        @Override
        public ProfilePlace getPlace(final String token) {
            return new ProfilePlace();
        }
    }

}
