package pgu.client.pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PublicPlace extends Place {

    private final String publicUrl;

    public PublicPlace(final String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public static class Tokenizer implements PlaceTokenizer<PublicPlace> {
        @Override
        public String getToken(final PublicPlace place) {
            GWT.log("## " + place.getPublicUrl());
            return place.getPublicUrl();
        }

        @Override
        public PublicPlace getPlace(final String token) {
            GWT.log("### " + token);
            return new PublicPlace(token);
        }
    }

    public String getPublicUrl() {
        return publicUrl;
    }
}

