package pgu.client.signin;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SigninPlace extends Place {

    @Prefix("signin")
    public static class Tokenizer implements PlaceTokenizer<SigninPlace> {
        @Override
        public String getToken(final SigninPlace place) {
            return "";
        }

        @Override
        public SigninPlace getPlace(final String token) {
            return new SigninPlace();
        }
    }

}
