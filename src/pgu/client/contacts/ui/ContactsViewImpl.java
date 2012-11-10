package pgu.client.contacts.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import pgu.client.app.event.ChartsApiIsAvailableEvent;
import pgu.client.app.utils.ChartsUtils;
import pgu.client.contacts.ContactsView;
import pgu.shared.model.Country2ContactNumber;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Popover;
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
import com.google.gwt.user.client.ui.HTML;
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
    CheckBox barChartBtn, pieChartBtn;
    @UiField
    HTMLPanel worldMap, americasMap, europeMap, asiaMap, oceaniaMap, africaMap;
    @UiField
    HTMLPanel pieChart, barChart;
    @UiField
    HTMLPanel loadingPanel, chartsPanel;
    @UiField
    TextBox fusionBox;
    @UiField
    Button addBtn;
    @UiField
    HTMLPanel fusionPanel, contactsNamesPanel;
    @UiField
    Button closeContactsNamesPanel;
    @UiField
    HTML contactsNamesHtml;
    @UiField
    Popover infoPop, fusionInfoPop;
    @UiField
    Button infoPopBtn, fusionInfoPopBtn;

    private final HashMap<String, String> region2names = new HashMap<String, String>();

    public ContactsViewImpl(final EventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        pieChart.getElement().setId("pgu_geo_contacts_piechart");
        barChart.getElement().setId("pgu_geo_contacts_barchart");

        worldMap.getElement().setId("pgu_geo_contacts_map_world");
        americasMap.getElement().setId("pgu_geo_contacts_map_americas");
        europeMap.getElement().setId("pgu_geo_contacts_map_europe");
        asiaMap.getElement().setId("pgu_geo_contacts_map_asia");
        oceaniaMap.getElement().setId("pgu_geo_contacts_map_oceania");
        africaMap.getElement().setId("pgu_geo_contacts_map_africa");

        contactsNamesPanel.getElement().setId("pgu_geo_contacts_names_panel");

        loadingPanel.setVisible(true);
        chartsPanel.setVisible(false);

        pieChart.setVisible(true);
        barChart.setVisible(false);

        worldMap.setVisible(true);
        americasMap.setVisible(false);
        europeMap.setVisible(false);
        asiaMap.setVisible(false);
        oceaniaMap.setVisible(false);
        africaMap.setVisible(false);

        eventBus.addHandler(ChartsApiIsAvailableEvent.TYPE, this);

        infoPop.setHeading("Charts");
        infoPop.setText("<p>Clicking on the regions of the geocharts will display your contacts' names.</p>" + //
                "<br/>" + //
                "<p>Note that this information is only available here and <b>not</b> on your public profile.</p>");

        fusionInfoPop.setHeading("Fusion tables");
        fusionInfoPop.setText("<p>How to create a fusion tables?</p>" + //
                "<p>See this <a href=\"http://support.google.com/fusiontables/answer/184641/\" target=\"blank\">video tutorial</a> from google.</p>" + //
                "<br/>" + //
                "<p>You'll need this <a href=\"javascript:;\" onclick=\"pgu_geo.download_contacts_csv();false;\" >csv file</a>.</p>");

        region2names.put("FR", //
                "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, " //
                + "Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne, Alice Alicia, Bruno Bourne" //
                );

        region2names.put("ES", "Toto toto, Titi titi, Toto toto, Titi titi, Toto toto, Titi titi, Toto toto, Titi titi, Toto toto, Titi titi");

    }

    public static native void exportMethods() /*-{
        $wnd.pgu_geo.download_contacts_csv = $entry(@pgu.client.contacts.ui.ContactsViewImpl::downloadContactsCsv());
    }-*/;

    public static void downloadContactsCsv() {

        final StringBuilder csv = new StringBuilder();
        csv.append("Country,Contacts number\r\n");

        for (final Entry<String, Integer> e : code2contactNumber.entrySet()) {
            csv.append(e.getKey());
            csv.append(",");
            csv.append(e.getValue());
            csv.append("\r\n");
        }

        downloadContactsCsvInternal(csv.toString());
    }

    public static native void downloadContactsCsvInternal(String csv) /*-{

        if ($wnd.navigator.appName != 'Microsoft Internet Explorer') {
            $wnd.open('data:text/csv;charset=utf-8,' + $wnd.encodeURI(csv), 'contacts.csv');

        } else {
            var popup = $wnd.open('','csv','');
            popup.document.body.innerHTML = '<pre>' + csv + '</pre>';
        }

    }-*/;

    @UiHandler("infoPopBtn")
    public void clickInfoPop(final ClickEvent e) {
        infoPop.toggle();
    }

    @UiHandler("fusionInfoPopBtn")
    public void clickFusionInfoPop(final ClickEvent e) {
        fusionInfoPop.toggle();
    }

    public void openAndShowContactNames(final String regionCode) {

        final String contactsNames = region2names.get(regionCode);

        if (contactsNames == null) {
            hideContactsNamesPanel();
            contactsNamesHtml.setHTML("");
            return;
        }

        final StringBuilder sb = new StringBuilder();
        for (final String contactName : contactsNames.split(", ")) {
            sb.append(contactName);
            sb.append(", ");
        }

        contactsNamesHtml.setHTML(sb.toString());
        showContactsNamesPanel();
    }

    @UiHandler("closeContactsNamesPanel")
    public void clickCloseContactsNamesPanel(final ClickEvent e) {
        hideContactsNamesPanel();
        contactsNamesHtml.setHTML("");
    }

    private native void showContactsNamesPanel() /*-{
        $wnd.$('#pgu_geo_contacts_names_panel').collapse('show');
    }-*/;

    private native void hideContactsNamesPanel() /*-{
        $wnd.$('#pgu_geo_contacts_names_panel').collapse('hide');
    }-*/;

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

        fusionPanel.add(hp);

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

    @UiHandler("pieChartBtn")
    public void clickPieChart(final ClickEvent e) {
        pieChart.setVisible(pieChartBtn.getValue());
    }

    @UiHandler("barChartBtn")
    public void clickBarChart(final ClickEvent e) {
        barChart.setVisible(barChartBtn.getValue());
    }

    static final HashMap<String, Integer> code2contactNumber = new HashMap<String, Integer>();

    @Override
    public void showCharts(final Country2ContactNumber country2contactNumber) {

        final HashMap<String, Integer> contactNumbers = country2contactNumber.getCode2contactNumber();

        code2contactNumber.clear();
        code2contactNumber.putAll(contactNumbers);

        // TODO PGU Nov 6, 2012 to sort the data by an order for the pie and bar charts
        weight2codes.clear();
        for (final Entry<String, Integer> e : contactNumbers.entrySet()) {
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

        // TODO PGU Nov 10, 2012 send an event to get the names associated to the countries

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
            }
        }
        buildGeoChartsUI(this);
    }

    private native void buildGeoChartsUI(ContactsViewImpl view) /*-{
        $wnd.console.log('build geo chart');

        var
            google = $wnd.google
          , data = $wnd.pgu_geo.contacts_table
          , data_table = google.visualization.arrayToDataTable(data)
        ;

        //
        // geo charts
        // see options @ https://google-developers.appspot.com/chart/interactive/docs/gallery/geochart
        var maps = [
            {id:'pgu_geo_contacts_map_world', options: {height:347}} // default width: 556px
           ,{id:'pgu_geo_contacts_map_americas', options: {region:'019'}}
           ,{id:'pgu_geo_contacts_map_europe', options: {region:150}}
           ,{id:'pgu_geo_contacts_map_asia', options: {region:142}}
           ,{id:'pgu_geo_contacts_map_oceania', options: {region:'009'}}
           ,{id:'pgu_geo_contacts_map_africa', options: {region:'002'}}
        ];

        var click_region_handler = function(e) {
            view.@pgu.client.contacts.ui.ContactsViewImpl::openAndShowContactNames(Ljava/lang/String;)(e.region);
        };

        for (var i = 0, len = maps.length; i < len; i++) {
            var map = maps[i];

            var geo_chart = new google.visualization.GeoChart($doc.getElementById(map.id));
            geo_chart.draw(data_table, map.options);

            // https://developers.google.com/chart/interactive/docs/events?hl=en
            google.visualization.events.addListener(geo_chart, 'regionClick', click_region_handler);
        }

        //
        // charts
        var click_chart_handler = function(the_chart) {

            var selection = the_chart.getSelection();
            if (selection.length != 1) {
                $wnd.console.log('!! different of one ');
                $wnd.console.log(selection);
                // TODO PGU hide the contacts area
                return;
            }

            var
                item = selection[0]
              , region = data_table.getFormattedValue(item.row, 0) // item.column
            ;

            view.@pgu.client.contacts.ui.ContactsViewImpl::openAndShowContactNames(Ljava/lang/String;)(region.toUpperCase());
        };

        //
        // pie chart
        var
            pie_options = {title:'83 Contacts by countries', is3D: true}
          , pie_chart = new google.visualization.PieChart($doc.getElementById('pgu_geo_contacts_piechart'))
        ;

        pie_chart.draw(data_table, pie_options);

        var click_pie_handler = function() {
            return click_chart_handler(pie_chart);
        }

        google.visualization.events.addListener(pie_chart, 'select', click_pie_handler);

        //
        // bar chart
        var
            bar_options = {
                  title: '83 Contacts by countries'
                , vAxis: {title: 'Country'}
                , width : 556
                , height : 347
            }
          , bar_chart = new google.visualization.BarChart($doc.getElementById('pgu_geo_contacts_barchart'))
        ;

        bar_chart.draw(data_table, bar_options);

        var click_bar_handler = function() {
            return click_chart_handler(bar_chart);
        }

        google.visualization.events.addListener(bar_chart, 'select', click_bar_handler);

    }-*/;

    private native void addDataRow(final String countryCode, final int weight) /*-{
        $wnd.console.log('addDataRow ' + countryCode + ', ' + weight);

        $wnd.pgu_geo.contacts_table.push([countryCode, weight]);
    }-*/;

    private native void initDataTable() /*-{
        $wnd.console.log('initDataTable ');

        $wnd.pgu_geo.contacts_table = [];
        $wnd.pgu_geo.contacts_table.push(['Country', 'Contacts']);

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

    @Override
    public void onChartsApiIsAvailable(final ChartsApiIsAvailableEvent event) {
        GWT.log("!!! ok, charts is ON, let's do some geocharts !!!");

        if (hasToBuildGeoChartWhenReady) {
            buildGeoCharts();
        }
    }

    @Override
    public void showLoadingPanel() {
        chartsPanel.setVisible(false);
        loadingPanel.setVisible(true);
    }

    @Override
    public void showChartsPanel() {
        loadingPanel.setVisible(false);
        chartsPanel.setVisible(true);
    }

}
