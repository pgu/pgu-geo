package pgu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import pgu.client.service.GreetingService;
import pgu.client.service.GreetingServiceAsync;
import pgu.shared.Connections;
import pgu.shared.Country;
import pgu.shared.Location;
import pgu.shared.OauthAuthorizationStart;
import pgu.shared.Person;
import pgu.shared.RequestToken;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Pgu_contacts implements EntryPoint {

    private final GreetingServiceAsync greetingService       = GWT.create(GreetingService.class);

    private final Button               oauthStartBtn         = new Button("Start LinkedIn authorization");
    private final Button               refreshBtn            = new Button("refresh");
    private final Button               logInLinkedin         = new Button("Login");

    private final Label                oauthCodeLabel        = new Label("Authorization code");
    private final TextBox              oauthCodeInput        = new TextBox();
    private final Anchor               oauthAuthorizationUrl = new Anchor("Authorization url >>");

    private final VerticalPanel        container             = new VerticalPanel();

    private String                     oauthCode             = "";

    @Override
    public void onModuleLoad() {

        oauthCodeLabel.setVisible(false);
        oauthCodeInput.setVisible(false);
        oauthAuthorizationUrl.setVisible(false);

        container.add(oauthStartBtn);
        container.add(oauthAuthorizationUrl);
        container.add(oauthCodeLabel);
        container.add(oauthCodeInput);
        container.add(refreshBtn);

        // container.add(logInLinkedin);
        RootPanel.get().add(container);

        addRefreshHandler();
        addLoginLinkedinHandler();
        addOauthStartBtnHandler();
        addOauthCodeInputHandler();
    }

    private void addRefreshHandler() {
        refreshBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                searchAndMarkContactsCountries();
            }
        });
    }

    private void addOauthCodeInputHandler() {
        oauthCodeInput.addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(final KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    oauthCode = oauthCodeInput.getText().trim();
                    searchAndMarkContactsCountries();
                }
            }

        });
    }

    private void searchAndMarkContactsCountries() {

        greetingService.fetchConnections(oauthCode, requestToken, new AsyncCallback<Connections>() {

            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("failure");
                oauthStartBtn.setVisible(true);
                oauthCodeLabel.setVisible(false);
                oauthCodeInput.setVisible(false);
                oauthAuthorizationUrl.setVisible(false);

            }

            @Override
            public void onSuccess(final Connections connections) {

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

                final StringBuilder sb = new StringBuilder();
                for (final Entry<String, Integer> e : code2weight.entrySet()) {
                    sb.append(e.getKey());
                    sb.append(": ");
                    sb.append(e.getValue());
                    sb.append(",");
                }
                GWT.log(sb.toString());

                int count = 1;
                for (final Entry<String, Integer> e : code2weight.entrySet()) {
                    final String countryCode = e.getKey();
                    final Integer weight = e.getValue();

                    new Timer() {

                        @Override
                        public void run() {
                            addMarker(countryCode, Integer.toString(weight));
                            GWT.log(countryCode + " is done");
                        }

                    }.schedule(count * 1000);
                    count += 2;
                }

            }

        });

    }

    public static native void addMarker(String countryCode, String weight) /*-{

		$wnd.geocoder
				.geocode(
						{
							'address' : 'country: ' + countryCode
						},
						function(results, status) {

							if (status != $wnd.google.maps.GeocoderStatus.OK) {
								$wnd
										.alert("Geocode was not successful for the following reason: "
												+ status);
								return;
							}

							var loc = results[0].geometry.location;

							//							$wnd.map.setCenter(loc);
							$wnd.console.log(loc);

							var marker = new $wnd.google.maps.Marker({
								map : $wnd.map,
								position : results[0].geometry.location,
								animation : $wnd.google.maps.Animation.DROP,
								title : weight + " contacts"
							});

						});

    }-*/;

    private RequestToken requestToken = null;

    private void addOauthStartBtnHandler() {
        oauthStartBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                greetingService.getLinkedinUrlAuthorization(new AsyncCallback<OauthAuthorizationStart>() {

                    @Override
                    public void onFailure(final Throwable caught) {
                        GWT.log("failure");
                        oauthStartBtn.setVisible(true);
                        oauthCodeLabel.setVisible(false);
                        oauthCodeInput.setVisible(false);
                        oauthAuthorizationUrl.setVisible(false);

                    }

                    @Override
                    public void onSuccess(final OauthAuthorizationStart oauthAuthorizationStart) {
                        requestToken = oauthAuthorizationStart.getRequestToken();

                        final String authorizationUrl = oauthAuthorizationStart.getAuthorizationUrl();

                        oauthAuthorizationUrl.setHref(authorizationUrl);
                        Window.open(authorizationUrl, "linkedIn", "");

                        oauthStartBtn.setVisible(false);
                        oauthCodeLabel.setVisible(true);
                        oauthCodeInput.setVisible(true);
                        oauthAuthorizationUrl.setVisible(true);
                    }

                });
            }
        });
    }

    private void addLoginLinkedinHandler() {
        logInLinkedin.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                greetingService.logInLinkedin(new AsyncCallback<Void>() {

                    @Override
                    public void onFailure(final Throwable caught) {
                        GWT.log("onFailure");
                    }

                    @Override
                    public void onSuccess(final Void result) {
                        GWT.log("success");
                    }

                });
            }
        });
    }
}
