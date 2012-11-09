package pgu.client.contacts;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ContactsPlace extends Place {

    @Prefix("contacts")
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
