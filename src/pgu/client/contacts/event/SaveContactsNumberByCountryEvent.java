package pgu.client.contacts.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SaveContactsNumberByCountryEvent extends GwtEvent<SaveContactsNumberByCountryEvent.Handler> {

    public interface HasSaveContactsNumberByCountryHandlers extends HasHandlers {
        HandlerRegistration addSaveContactsNumberByCountryHandler(SaveContactsNumberByCountryEvent.Handler handler);
    }

    public interface Handler extends EventHandler {
        void onSaveContactsNumberByCountry(SaveContactsNumberByCountryEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final String jsonContactsNumberByCountry;

    public SaveContactsNumberByCountryEvent(final String jsonContactsNumberByCountry) {
        this.jsonContactsNumberByCountry = jsonContactsNumberByCountry;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onSaveContactsNumberByCountry(this);
    }

    public String getContactsNumberByCountry() {
        return jsonContactsNumberByCountry;
    }

}
