package pgu.client.contacts.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import pgu.client.app.event.ChartsApiIsAvailableEvent;
import pgu.client.contacts.ContactsPresenter;
import pgu.client.contacts.ContactsView;
import pgu.shared.dto.Connections;
import pgu.shared.dto.Country;
import pgu.shared.dto.Location;
import pgu.shared.dto.Person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class ContactsViewImpl extends Composite implements ContactsView, ChartsApiIsAvailableEvent.Handler {

    private static ContactsViewImplUiBinder uiBinder = GWT.create(ContactsViewImplUiBinder.class);

    interface ContactsViewImplUiBinder extends UiBinder<Widget, ContactsViewImpl> {
    }

    public ContactsViewImpl(final EventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        eventBus.addHandler(ChartsApiIsAvailableEvent.TYPE, this);
    }

    private ContactsPresenter presenter;

    @Override
    public void setPresenter(final ContactsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setConnections(final Connections connections) {

        final ArrayList<Person> persons = connections.getValues();
        if (persons == null) {
            return;
        }

        final HashMap<String, Integer> code2weight = new HashMap<String, Integer>();
        for (final Person p : persons) {

            final Location location = p.getLocation();
            if (location == null) {
                continue;
            }

            final Country country = location.getCountry();
            if (country == null) {
                continue;
            }

            final String code = country.getCode();
            if (code == null) {
                continue;
            }

            if (code2weight.containsKey(code)) {
                final Integer count = code2weight.get(code) + 1;
                code2weight.put(code, count);

            } else {
                code2weight.put(code, 1);
            }
        }

        logResult(code2weight);

        final TreeMap<Integer, ArrayList<String>> weight2codes = new TreeMap<Integer, ArrayList<String>>();
        for (final Entry<String, Integer> e : code2weight.entrySet()) {
            final String countryCode = e.getKey();
            final Integer weight = e.getValue();

            if (weight2codes.containsKey(weight)) {
                weight2codes.get(weight).add(countryCode);

            } else {
                final ArrayList<String> countryCodes = new ArrayList<String>();
                countryCodes.add(countryCode);
                weight2codes.put(weight, countryCodes);
            }
        }

        int count = 1;
        for (final Entry<Integer, ArrayList<String>> e : weight2codes.entrySet()) {
            final Integer weight = e.getKey();
            final ArrayList<String> countryCodes = e.getValue();

            for (final String countryCode : countryCodes) {
                new Timer() {

                    @Override
                    public void run() {
                        // TODO PGU Aug 14, 2012 add a marker without geocoder for already existing connections
                        addMarker(countryCode, Integer.toString(weight));
                        GWT.log(countryCode + " is done");
                    }

                }.schedule(count * 1000);

                count += 2;
            }
        }
    }

    private void logResult(final HashMap<String, Integer> code2weight) {
        final StringBuilder sb = new StringBuilder();
        for (final Entry<String, Integer> e : code2weight.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append(",");
        }
        GWT.log(sb.toString());
    }

    public static native void addMarker(String countryCode, String weight) /*-{

        var
            geocoder = @pgu.client.app.utils.GeocoderUtils::geocoder()()
		  , map = @pgu.client.profile.ui.ProfileUtils::profileMap()()
		  , google = @pgu.client.app.utils.GoogleUtils::google()()
		;

		geocoder.geocode(
						{
							'address' : 'country: ' + countryCode
						},
						function(results, status) {

							if (status != google.maps.GeocoderStatus.OK) {
								$wnd
										.alert("Geocode was not successful for the following reason: "
												+ status);
								return;
							}

							var loc = results[0].geometry.location;

							//                          $wnd.map.setCenter(loc);
							$wnd.console.log(loc);


							var marker = new google.maps.Marker({
								map : map,
								position : results[0].geometry.location,
								animation : google.maps.Animation.DROP,
								title : weight + " contacts"
							});

						});

    }-*/;

    private boolean isChartsApiAvailable = false;

    @Override
    public void onChartsApiIsAvailable(final ChartsApiIsAvailableEvent event) {
        GWT.log("!!! ok, charts is ON, let's do some geocharts !!!");
        isChartsApiAvailable = true;

        // TODO PGU Oct 29, 2012 if isChartsApiAvailable is false, let's check that $wnd.google.visualization.GeoChart existe and update isChartsApiAvailable.
    }

    // is user logged in linkedin?
    // if no, then offer him to authorize the app
    // then we access its account and its contacts
    // else
    // - if the user is in our db
    // - then get its contacts
    // - else offer him to authorize the app

    // if he connects, then show a logout btn
    // if he connects, update the appstate with his profile

}
