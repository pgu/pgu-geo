package pgu.client.contacts;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ContactsPlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<ContactsPlace> {
        @Override
        public String getToken(final ContactsPlace place) {
            return "";
        }

        @Override
        public ContactsPlace getPlace(final String token) {
            return new ContactsPlace();
        }
    }

}
