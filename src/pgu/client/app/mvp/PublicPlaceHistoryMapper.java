package pgu.client.app.mvp;

import pgu.client.pub.PublicPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers(PublicPlace.Tokenizer.class)
public interface PublicPlaceHistoryMapper  extends PlaceHistoryMapper {

}
