package pgu.client.app.mvp;

import pgu.client.contacts.ContactsPlace;
import pgu.client.profile.ProfilePlace;
import pgu.client.pub.PublicPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
    //
    ContactsPlace.Tokenizer.class //
    , ProfilePlace.Tokenizer.class //
    , PublicPlace.Tokenizer.class //

})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
