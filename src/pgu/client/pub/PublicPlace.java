package pgu.client.pub;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PublicPlace extends Place {

    private final String publicUrl;

    public PublicPlace(final String publicUrl) {
        this.publicUrl = publicUrl;
    }

    @Prefix("!public")
    public static class Tokenizer implements PlaceTokenizer<PublicPlace> {

        @Override
        public String getToken(final PublicPlace place) {
            return place.getPublicUrl();
        }

        @Override
        public PublicPlace getPlace(final String token) {
            return new PublicPlace(token);
        }
    }

    public String getPublicUrl() {
        return publicUrl;
    }
}

