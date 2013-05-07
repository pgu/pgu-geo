package pgu.client.pub.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import pgu.client.app.utils.ClientHelper;
import pgu.client.app.utils.MarkersHelper;
import pgu.client.components.playtoolbar.PlayToolbar;
import pgu.client.components.playtoolbar.event.BwdEvent;
import pgu.client.components.playtoolbar.event.FwdEvent;
import pgu.client.components.playtoolbar.event.HasSelectedItemType;
import pgu.client.components.playtoolbar.event.HideAllEvent;
import pgu.client.components.playtoolbar.event.PauseEvent;
import pgu.client.components.playtoolbar.event.PlayEvent;
import pgu.client.components.playtoolbar.event.ShowAllEvent;
import pgu.client.components.playtoolbar.event.StartPlayingEvent;
import pgu.client.components.playtoolbar.event.StopEvent;
import pgu.client.pub.PublicPresenter;
import pgu.client.pub.PublicView;
import pgu.client.resources.ResourcesApp;
import pgu.client.resources.ResourcesApp.CssResourceApp;
import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.PublicLocations;
import pgu.shared.model.PublicMapPreferences;
import pgu.shared.utils.ChartType;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class PublicViewImpl extends Composite implements PublicView {

    private static final String PREFIX_CHART_ID = "pgu_geo_public_contacts_";

    private static final String ACCORDION_ID = "pgu_geo_profile_items_accordion";

    private static PublicViewImplUiBinder uiBinder = GWT.create(PublicViewImplUiBinder.class);

    interface PublicViewImplUiBinder extends UiBinder<Widget, PublicViewImpl> {
    }

    @UiField(provided = true)
    Section                   profileSection, contactsSection;
    @UiField
    NavLink                   locContainer;
    @UiField
    HTML                      summaryContainer, profileItemDescription //
    , lgContainer //
    , itemsAccordion //
    ;
    @UiField
    PlayToolbar               playToolbar;
    @UiField
    HTMLPanel                 spContainer, summaryPanel, profileItemPanel;
    @UiField
    HTMLPanel                 multiPanel, singlePanel;
    @UiField
    HTMLPanel worldMap, americasMap, europeMap, asiaMap, oceaniaMap, africaMap;
    @UiField
    HTMLPanel pieChart, barChart;
    @UiField
    HTMLPanel fusionPanel;

    private PublicPresenter   presenter;
    private final ClientHelper u = new ClientHelper();

    private final HashMap<String, HTMLPanel> type2chart = new HashMap<String, HTMLPanel>();

    private final CssResourceApp css;

    private final PublicViewMap          viewMap = new PublicViewMap();
    private final PublicViewLocations    viewLocations = new PublicViewLocations();
    private final PublicViewProfileItems viewProfileItems = new PublicViewProfileItems();
    private final PublicViewMarkers      viewMarkers = new PublicViewMarkers();
    private final MarkersHelper          markersHelper = new MarkersHelper();

    public PublicViewImpl(final EventBus eventBus) {

        profileSection = new Section("public:profile");
        contactsSection = new Section("public:contacts");

        initWidget(uiBinder.createAndBindUi(this));

        css = ResourcesApp.INSTANCE.css();

        pieChart.getElement().setId(PREFIX_CHART_ID + ChartType.PIE);
        barChart.getElement().setId(PREFIX_CHART_ID + ChartType.BAR);

        worldMap.getElement().setId(PREFIX_CHART_ID + ChartType.WORLD);
        americasMap.getElement().setId(PREFIX_CHART_ID + ChartType.AMERICAS);
        europeMap.getElement().setId(PREFIX_CHART_ID + ChartType.EUROPE);
        asiaMap.getElement().setId(PREFIX_CHART_ID + ChartType.ASIA);
        oceaniaMap.getElement().setId(PREFIX_CHART_ID + ChartType.OCEANIA);
        africaMap.getElement().setId(PREFIX_CHART_ID + ChartType.AFRICA);

        type2chart.put(ChartType.AFRICA, africaMap);
        type2chart.put(ChartType.AMERICAS, americasMap);
        type2chart.put(ChartType.ASIA, asiaMap);
        type2chart.put(ChartType.BAR, barChart);
        type2chart.put(ChartType.EUROPE, europeMap);
        type2chart.put(ChartType.OCEANIA, oceaniaMap);
        type2chart.put(ChartType.PIE, pieChart);
        type2chart.put(ChartType.WORLD, worldMap);

        hideAllCharts();

        playToolbar.setVisible(false);
        playToolbar.addPlayHandler(new PlayEvent.Handler() {

            @Override
            public void onPlay(final PlayEvent event) {
                GWT.log("[on play]");
                showMovieProfileItem(event.getToken());
            }

        });
        playToolbar.addStopHandler(new StopEvent.Handler() {

            @Override
            public void onStop(final StopEvent event) {
                GWT.log("[on stop]");
                hideProfileItem();
            }

        });
        playToolbar.addPauseHandler(new PauseEvent.Handler() {

            @Override
            public void onPause(final PauseEvent event) {
                // nothing to do
                GWT.log("[on pause]");
            }

        });
        playToolbar.addBwdHandler(new BwdEvent.Handler() {

            @Override
            public void onBwd(final BwdEvent event) {
                GWT.log("[on bwd]");
                showMovieProfileItem(event.getToken());
            }

        });
        playToolbar.addFwdHandler(new FwdEvent.Handler() {

            @Override
            public void onFwd(final FwdEvent event) {
                GWT.log("[on fwd]");
                showMovieProfileItem(event.getToken());
            }

        });
        playToolbar.addShowAllHandler(new ShowAllEvent.Handler() {

            @Override
            public void onShowAll(final ShowAllEvent event) {
                GWT.log("[on show all]");

                singlePanel.setVisible(false);
                multiPanel.setVisible(true);

                hideProfileCurrentLocation();

                final String selectedItemType = event.getSelectedItemType();
                viewProfileItems.displayProfileMarkers(selectedItemType);
            }

        });
        playToolbar.addHideAllHandler(new HideAllEvent.Handler() {

            @Override
            public void onHideAll(final HideAllEvent event) {
                GWT.log("[on hide all]");

                hideProfileMarkers(event);

                displayProfileCurrentLocation();
            }

        });
        playToolbar.addStartPlayingHandler(new StartPlayingEvent.Handler() {

            @Override
            public void onStartPlaying(final StartPlayingEvent event) {
                GWT.log("[on start playing]");

                hideProfileMarkers(event);

                hideProfileCurrentLocation();
            }
        });

        contactsSection.setVisible(false);

        singlePanel.setVisible(true);
        multiPanel.setVisible(false);
        itemsAccordion.setHTML("");

        summaryPanel.setVisible(true);
        profileItemPanel.setVisible(false);

    }

    private void hideProfileItem() {
        hideProfileItemArea();
        viewMarkers.deleteMovieMarkers();
    }

    private void hideProfileMarkers(final HasSelectedItemType event) {

        multiPanel.setVisible(false);
        itemsAccordion.setHTML("");
        singlePanel.setVisible(true);

        final String selectedItemType = event.getSelectedItemType();
        viewProfileItems.hideProfileMarkers(selectedItemType);
    }

    private native void collapseShow(String id) /*-{
        $wnd.$('#' + id).collapse('show');
    }-*/;

    private native void collapseHide(String id) /*-{
        $wnd.$('#' + id).collapse('hide');
    }-*/;

    private void showMovieProfileItem(final int token) {
        showProfileItemArea();

        fillProfileItemContent(token);

        viewProfileItems.showMovieProfileItemLocations(token, viewMap.getPublicMap());
    }

    private void fillProfileItemContent(final int token) {

        final String description = viewProfileItems.getSelectedProfileItemDescription(token);
        profileItemDescription.setHTML(description);
    }

    private void hideProfileItemArea() {
        if (!profileItemPanel.isVisible()) {
            return;
        }

        profileItemPanel.setVisible(false);
        summaryPanel.setVisible(true);
    }

    private void showProfileItemArea() {

        if (profileItemPanel.isVisible()) {
            return;
        }

        summaryPanel.setVisible(false);
        profileItemPanel.setVisible(true);
    }

    @Override
    public void setPresenter(final PublicPresenter presenter) {
        this.presenter = presenter;
    }

    public void setProfileName(final String firstName, final String lastName) {
        presenter.setProfileName(firstName + " " + lastName);
    }

    public void setProfileHeadline(final String headline) {
        presenter.setProfileHeadline(headline);
    }

    private void hideProfileCurrentLocation() {
        if (currentLocationMarker != null) {

            hideCurrentLocation(currentLocationMarker);
        }
    }

    public void displayProfileCurrentLocation() {

        final String locationName = locContainer.getText();
        final boolean hasLocation = !u.isVoid(locationName);

        if (hasLocation) {

            displayCurrentLocationMarkerOnPublicMap(locationName);
        }
    }

    public void setProfileLocation(final String locationName) {

        final boolean hasLocation = !u.isVoid(locationName);
        locContainer.setText(hasLocation ? locationName : "");

        displayProfileCurrentLocation();
    }

    private JavaScriptObject currentLocationMarker = null;

    private void displayCurrentLocationMarkerOnPublicMap(final String locationName) {

        if (currentLocationMarker != null) {

            showCurrentLocation(currentLocationMarker);
            return;
        }

        final JavaScriptObject publicMap = viewMap.getPublicMap();
        currentLocationMarker = markersHelper.createMarker(publicMap, locationName);
    }

    private JavaScriptObject getPublicMap() {
        return viewMap.getPublicMap();
    }

    private native void showCurrentLocation(JavaScriptObject marker) /*-{
        var map = this.@pgu.client.pub.ui.PublicViewImpl::getPublicMap()
                       ();
        marker.setMap(map);
    }-*/;

    private native void hideCurrentLocation(final JavaScriptObject marker) /*-{
        marker.setMap(null);
    }-*/;

    public void setProfileSpecialties(final String htmlSpecialties) {
        spContainer.add(new HTML(htmlSpecialties));
    }

    private void setProfileSummary(final String htmlSummary) {
        summaryContainer.setHTML(htmlSummary);
    }

    public void setProfileLanguages(final String htmlLanguages) {
        lgContainer.setHTML(htmlLanguages);
    }

    private final Image linkedinPicture = new Image();

    private void setProfileLinkedinUrl(final String url) {
        // TODO PGU Sep 25, 2012 linkedin picture
        linkedinPicture.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                Window.open(url, "linkedin_public_profile", null);
            }
        });
    }

    @Override
    public void setProfile(final FullPublicProfile fullProfile) {

        viewMap.initPublicProfileMap();

        final PublicMapPreferences publicMapPreferences = fullProfile.getPublicMapPreferences();
        final String jsonMapPreferences = publicMapPreferences == null ? null : publicMapPreferences.getValues();
        viewMap.setPreferences(jsonMapPreferences);

        final PublicLocations publicLocations = fullProfile.getPublicLocations();
        if (publicLocations == null) {
            viewLocations.initCaches(null, null);

        } else {
            viewLocations.initCaches(publicLocations.getItems2locations(), publicLocations.getReferentialLocations());
        }

        final BasePublicProfile publicProfile = fullProfile.getBasePublicProfile();
        setProfile(this, publicProfile.getValue());

        presenter.fetchPublicContacts(publicProfile.getProfileUrl());
    }

    private native void setProfile(PublicViewImpl view, String public_profile) /*-{

		var pub = JSON.parse(public_profile);
		$wnd.pgu_geo.public_profile = pub;

		var
		    first_name = pub.firstName || ''
          , last_name = pub.lastName || ''
          , headline = pub.headline || ''
          , current_location_name = pub.location || ''
          , specialties = pub.specialties || ''
          , summary = pub.summary || ''
          , linkedin_url = pub.rawPublicProfileUrl || ''
          , languages = pub.languages || ''
        ;

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileName(Ljava/lang/String;Ljava/lang/String;)
             (first_name, last_name);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileHeadline(Ljava/lang/String;)
             (headline);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileLocation(Ljava/lang/String;)
             (current_location_name);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileSpecialties(Ljava/lang/String;)
             (specialties);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileSummary(Ljava/lang/String;)
             (summary);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileLinkedinUrl(Ljava/lang/String;)
             (linkedin_url);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileLanguages(Ljava/lang/String;)
             (languages);

        this.@pgu.client.pub.ui.PublicViewImpl::setProfileItems()
             ();

    }-*/;

    private void setProfileItems() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                viewProfileItems.setProfileItems();
                addProfileItemsToPlayToolbar();
                viewProfileItems.initCachesLocation2MarkerAndItems(PublicViewImpl.this);
            }
        });
    }

    public void addProfileItemsToPlayToolbar() {
        playToolbar.addProfileItems();
        playToolbar.setVisible(playToolbar.hasItemsToDisplay());
    }

    private final LinkedHashMap<String, String> id2itemTitle = new LinkedHashMap<String, String>();
    private final LinkedHashMap<String, String> id2itemContent = new LinkedHashMap<String, String>();

    public void showItemsForLocation(final String locationName) {

        id2itemTitle.clear();
        id2itemContent.clear();

        viewProfileItems.fillViewWithProfileItems(this, locationName);

        final StringBuilder sb = new StringBuilder();
        final boolean isOpen = id2itemTitle.size() == 1;

        for (final Entry<String, String> e : id2itemTitle.entrySet()) {
            final String id = e.getKey();
            final String title = e.getValue();
            final String content = id2itemContent.get(id);

            sb.append(newAccordionGroup(isOpen, id, title, content));
        }

        itemsAccordion.setHTML(sb.toString());
    }

    private String newAccordionGroup(final boolean isOpen, final String id, final String title, final String content) {
        final String cssIn = isOpen? " in " : "";
        return
                "<div class=\"accordion-group\">                          " + //
                "  <div class=\"accordion-heading\">                      " + //
                "    <a class=\"accordion-toggle\"                        " + //
                "       data-toggle=\"collapse\"                          " + //
                "       data-parent=\"#" + ACCORDION_ID + "\"             " + //
                "       href=\"#accordion_" + id + "\">                   " + //
                title + //
                "    </a>                                                 " + //
                "  </div>                                                 " + //
                "  <div id=\"accordion_" + id + "\"                       " + //
                "       class=\"accordion-body collapse" + cssIn + "\">   " + //
                "    <div class=\"accordion-inner\">                      " + //
                content + //
                "    </div>                                               " + //
                "  </div>                                                 " + //
                "</div>                                                   " //
                ;
    }

    public void fillWithProfileItem(final String id, final String title, final String content) {

        id2itemTitle.put(id, title);
        id2itemContent.put(id, content);
    }

    private boolean hasAtLeastOneChart = false;
    private PublicContacts publicContacts;

    @Override
    public void onFetchPublicContactsSuccess(final PublicContacts publicContacts) {
        this.publicContacts = publicContacts;
        setContactsInternal();
    }

    private void setContactsInternal() {

        u.console("set contacts internal");

        hasAtLeastOneChart = false;

        hideAndClearAllCharts();
        buildCharts(publicContacts);

        fusionPanel.clear();
        parseFusionUrls(this, publicContacts.getFusionUrls());

        contactsSection.setVisible(hasAtLeastOneChart);
    }

    private void hideAndClearAllCharts() {
        hideAllCharts();

        for (final HTMLPanel chart : type2chart.values()) {
            chart.clear();
        }
    }

    private void buildCharts(final PublicContacts publicContacts) {

        chartType2containerId.clear();

        final int totalNbOfContacts = publicContacts.getTotalNbOfContacts();

        final String chartsPreferences = publicContacts.getChartsPreferences();
        parseChartsPreferences(this, chartsPreferences);

        if (chartType2containerId.isEmpty()) {
            return;
        }

        final String contactsNumberByCountry = publicContacts.getContactsNumberByCountry();
        parseContactsNumberByCountry(this, contactsNumberByCountry);

        for (final Entry<String, String> e : chartType2containerId.entrySet()) {

            final String chartType = e.getKey();
            final String containerId = e.getValue();

            if (ChartType.PIE.equals(chartType)) {
                addPieChart(totalNbOfContacts, this, containerId);

            } else if (ChartType.BAR.equals(chartType)) {
                addBarChart(totalNbOfContacts, this, containerId);

            } else {
                addGeoMap(this, containerId, ChartType.regionCode(chartType));
            }

            hasAtLeastOneChart = true;
        }
    }

    private native void addGeoMap(PublicViewImpl view, String container_id, String region_code) /*-{

        var
            google = $wnd.google
          , data_table = $wnd.pgu_geo.public_contacts_data_table
        ;

        var map_options = {height: 347, width: 556};

        if ("" != region_code) {
            map_options.region = region_code;
        }

        var geo_chart = new google.visualization.GeoChart($doc.getElementById(container_id));
        geo_chart.draw(data_table, map_options);

    }-*/;

    private native void addBarChart(int total_nb_of_contacts, PublicViewImpl view, String container_id) /*-{
        var
            google = $wnd.google
          , data_table = $wnd.pgu_geo.public_contacts_data_table
          , bar_options = {
                  title: total_nb_of_contacts + ' Contacts by countries'
                , vAxis: {title: 'Countries'}
                , width : 556
                , height : 347
            }
          , bar_chart = new google.visualization.BarChart($doc.getElementById(container_id))
        ;

        bar_chart.draw(data_table, bar_options);

    }-*/;

    private native void addPieChart(int total_nb_of_contacts, final PublicViewImpl view, final String container_id) /*-{
        var
            google = $wnd.google
          , data_table = $wnd.pgu_geo.public_contacts_data_table
          , pie_options = {
                title: total_nb_of_contacts + ' Contacts by countries'
                , is3D: true
                , width : 556
                , height : 347
            }
          , pie_chart = new google.visualization.PieChart($doc.getElementById(container_id))
        ;

        pie_chart.draw(data_table, pie_options);

    }-*/;

    private native void parseChartsPreferences(final PublicViewImpl view, final String json) /*-{
        // ['world','americas']

        if (!json) {
            return;
        }

        var chart_types = JSON.parse(json);

        for ( var i = 0, len = chart_types.length; i < len; i++) {
            var chart_type = chart_types[i];

            view.@pgu.client.pub.ui.PublicViewImpl::addChart(Ljava/lang/String;)( //
            chart_type);
        }

    }-*/;

    private final HashMap<String, String> chartType2containerId = new HashMap<String, String>();

    private void addChart(final String chartType) {

        final String chartId = PREFIX_CHART_ID + chartType;

        UIObject.setVisible(DOM.getElementById(chartId), true);

        chartType2containerId.put(chartType, chartId);
    }

    private native void parseContactsNumberByCountry(final PublicViewImpl view, final String json) /*-{
        // [['Country', 'Contacts'], ['es', 10]]

        var data;

        if (!json) {
            data = [['Country', 'Contacts']];
        } else {
            data = JSON.parse(json);
        }

        var
            google = $wnd.google
        ;

        $wnd.pgu_geo.public_contacts_data_table = google.visualization.arrayToDataTable(data);
    }-*/;

    private native void parseFusionUrls(PublicViewImpl view, String json) /*-{
        // [url1, url2]

        if (!json) {
            return;
        }

        var fusion_urls = JSON.parse(json);
        for ( var i = 0, len = fusion_urls.length; i < len; i++) {

            var fusion_url = fusion_urls[i];
            view.@pgu.client.pub.ui.PublicViewImpl::addFusionPanel(Ljava/lang/String;)( //
            fusion_url);

        }

    }-*/;

    private void addFusionPanel(final String url) {

        final Frame frame = new Frame(url);
        frame.addStyleName(css.chartWell());

        fusionPanel.add(frame);

        hasAtLeastOneChart = true;
    }

    private void hideAllCharts() {
        for (final HTMLPanel chart : type2chart.values()) {
            chart.setVisible(false);
        }
    }

    @Override
    public void showProfileNotFound() {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        Window.alert("Profile not found!");
    }

}
