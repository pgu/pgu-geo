package pgu.client.contacts.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import pgu.client.app.event.ChartsApiIsAvailableEvent;
import pgu.client.app.utils.ChartsUtils;
import pgu.client.contacts.ContactsPresenter;
import pgu.client.contacts.ContactsView;
import pgu.shared.dto.Connections;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class ContactsViewImpl extends Composite implements ContactsView, ChartsApiIsAvailableEvent.Handler {

    private static ContactsViewImplUiBinder uiBinder = GWT.create(ContactsViewImplUiBinder.class);

    interface ContactsViewImplUiBinder extends UiBinder<Widget, ContactsViewImpl> {
    }

    interface ChartStyle extends CssResource {
        String chartWell();
    }

    @UiField
    ChartStyle style;
    @UiField
    CheckBox worldBtn, americasBtn, europeBtn, asiaBtn, oceaniaBtn, africaBtn;
    @UiField
    HTMLPanel worldMap, americasMap, europeMap, asiaMap, oceaniaMap, africaMap;
    @UiField
    TextBox fusionBox;
    @UiField
    Button addBtn;
    @UiField
    HTMLPanel fusionContainer;


    public ContactsViewImpl(final EventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        worldMap.getElement().setId("pgu_geo_contacts_map_world");
        americasMap.getElement().setId("pgu_geo_contacts_map_americas");
        europeMap.getElement().setId("pgu_geo_contacts_map_europe");
        asiaMap.getElement().setId("pgu_geo_contacts_map_asia");
        oceaniaMap.getElement().setId("pgu_geo_contacts_map_oceania");
        africaMap.getElement().setId("pgu_geo_contacts_map_africa");

        eventBus.addHandler(ChartsApiIsAvailableEvent.TYPE, this);
    }

    private ContactsPresenter presenter;

    @Override
    public void setPresenter(final ContactsPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("fusionBox")
    public void keydownOnAddFusionTable(final KeyDownEvent event) {

        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

            event.preventDefault();
            event.stopPropagation();

            addFusionTable();
        }
    }

    @UiHandler("addBtn")
    public void clickAddFusion(final ClickEvent e) {
        addFusionTable();
    }

    private void addFusionTable() {
        final String url = fusionBox.getText();

        if ("".equals(url.trim()) || !url.startsWith("https://www.google.com/fusiontables/embedviz")) {
            return;
        }

        //        https://www.google.com/fusiontables/embedviz?viz=MAP&q=select+col0%3E%3E1+from+1dlLBDtnjrqYG7W3eamJX3-1ogV8XdGHXkX2wOaU&h=false&lat=47.74359286233701&lng=6.064453125&z=3&t=1&l=col0%3E%3E1
        //        https://www.google.com/fusiontables/embedviz?viz=GVIZ&t=BAR&containerId=gviz_canvas&q=select+col0%3E%3E0%2C+col1%3E%3E0+from+1dlLBDtnjrqYG7W3eamJX3-1ogV8XdGHXkX2wOaU&qrs=+where+col0%3E%3E0+%3E%3D+&qre=+and+col0%3E%3E0+%3C%3D+&qe=+limit+13&width=500&height=300

        final Frame frame = new Frame(url);
        frame.addStyleName(style.chartWell());

        final Button closeBtn = new Button();
        closeBtn.setText("x");
        closeBtn.addStyleName("close");
        closeBtn.getElement().getStyle().setMarginTop(11, Unit.PX);

        final HorizontalPanel hp = new HorizontalPanel();
        hp.getElement().getStyle().setFloat(Style.Float.LEFT);
        hp.add(frame);
        hp.add(closeBtn);

        fusionContainer.add(hp);

        closeBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                hp.removeFromParent();
            }
        });
    }

    @UiHandler("worldBtn")
    public void clickWorld(final ClickEvent e) {
        worldMap.setVisible(worldBtn.getValue());
    }

    @UiHandler("americasBtn")
    public void clickAmericas(final ClickEvent e) {
        americasMap.setVisible(americasBtn.getValue());
    }

    @UiHandler("europeBtn")
    public void clickEurope(final ClickEvent e) {
        europeMap.setVisible(europeBtn.getValue());
    }

    @UiHandler("asiaBtn")
    public void clickAsia(final ClickEvent e) {
        asiaMap.setVisible(asiaBtn.getValue());
    }

    @UiHandler("oceaniaBtn")
    public void clickOceania(final ClickEvent e) {
        oceaniaMap.setVisible(oceaniaBtn.getValue());
    }

    @UiHandler("africaBtn")
    public void clickAfrica(final ClickEvent e) {
        africaMap.setVisible(africaBtn.getValue());
    }

    @Override
    public void setConnections(final Connections connections) {
        /*
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
         */
        //        ca: 3,it: 1,cz: 2,us: 3,td: 1,gb: 4,au: 1,de: 3,fr: 38,ru: 1,ch: 13,es: 9,be: 1

        final HashMap<String, Integer> code2weight = new HashMap<String, Integer>();
        code2weight.put("ca", 3);
        code2weight.put("it", 1);
        code2weight.put("cz", 2);
        code2weight.put("us", 3);
        code2weight.put("td", 1);
        code2weight.put("gb", 4);
        code2weight.put("au", 1);
        code2weight.put("de", 3);
        code2weight.put("fr", 38);
        code2weight.put("ru", 1);
        code2weight.put("ch", 13);
        code2weight.put("es", 9);
        code2weight.put("be", 1);

        weight2codes.clear();
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

        GWT.log("is api loaded? " + ChartsUtils.isApiLoaded());

        if (!ChartsUtils.isApiLoaded()) {
            hasToBuildGeoChartWhenReady = true;

        } else {
            hasToBuildGeoChartWhenReady = false;
            buildGeoCharts();

        }

    }

    private final TreeMap<Integer, ArrayList<String>> weight2codes = new TreeMap<Integer, ArrayList<String>>();
    private boolean hasToBuildGeoChartWhenReady = false;

    private void buildGeoCharts() {

        initDataTable();
        for (final Entry<Integer, ArrayList<String>> e : weight2codes.entrySet()) {
            final Integer weight = e.getKey();
            final ArrayList<String> countryCodes = e.getValue();

            for (final String countryCode : countryCodes) {
                addDataRow(countryCode, weight);
                //                new Timer() {
                //
                //                    @Override
                //                    public void run() {
                //                        // TODO PGU Aug 14, 2012 add a marker without geocoder for already existing connections
                //                        addMarker(countryCode, Integer.toString(weight));
                //                        GWT.log(countryCode + " is done");
                //                    }
                //
                //                }.schedule(count * 1000);
                //
                //                count += 2;
            }
        }
        buildGeoChartsUI();

        // TODO PGU Oct 30, 2012 proposer un chart avec des bars et combien de maps avec quel zone (une world, une europe, etc), enfin, l'url d'une fusion table. + help/info sur le link google pour en créer une
        // + proposer de telecharger un .csv avec ses données
    }

    private native void buildGeoChartsUI() /*-{
        $wnd.console.log('build geo chart');

        var data = $wnd.pgu_geo.contacts_table;
        $wnd.console.log(data);

        var dataTable = $wnd.google.visualization.arrayToDataTable(data);

        // see options @ https://google-developers.appspot.com/chart/interactive/docs/gallery/geochart
        var options = {'region':150};

        var maps = [
            {'id':'pgu_geo_contacts_map_world', 'options':{}}
           ,{'id':'pgu_geo_contacts_map_americas','options':{'region':'019'}}
           ,{'id':'pgu_geo_contacts_map_europe','options':{'region':150}}
           ,{'id':'pgu_geo_contacts_map_asia','options':{'region':142}}
           ,{'id':'pgu_geo_contacts_map_oceania','options':{'region':'009'}}
           ,{'id':'pgu_geo_contacts_map_africa','options':{'region':'002'}}
        ];

        for (var i = 0, len = maps.length; i < len; i++) {

            var map = maps[i];

            var chart = new $wnd.google.visualization.GeoChart($doc.getElementById(map.id));
            chart.draw(dataTable, map.options);
        }

    }-*/;

    private native void addDataRow(final String countryCode, final int weight) /*-{
        $wnd.console.log('addDataRow ' + countryCode + ', ' + weight);

        $wnd.pgu_geo.contacts_table.push([countryCode, weight]);
    }-*/;

    private native void initDataTable() /*-{
        $wnd.console.log('initDataTable ');

        $wnd.pgu_geo.contacts_table = [];
        $wnd.pgu_geo.contacts_table.push(['Country', 'Connections']);

    }-*/;

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

    @Override
    public void onChartsApiIsAvailable(final ChartsApiIsAvailableEvent event) {
        GWT.log("!!! ok, charts is ON, let's do some geocharts !!!");
        // TODO PGU Oct 29, 2012 if isChartsApiAvailable is false, let's check that $wnd.google.visualization.GeoChart existe and update isChartsApiAvailable.
        if (hasToBuildGeoChartWhenReady) {
            buildGeoCharts();
        }
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
