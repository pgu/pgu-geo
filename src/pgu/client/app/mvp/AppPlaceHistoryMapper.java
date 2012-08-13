package pgu.client.app.mvp;

import pgu.client.contacts.ContactsPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
//
ContactsPlace.Tokenizer.class //
// , ImportBooksPlace.Tokenizer.class //
// , SetupPlace.Tokenizer.class //

})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
