package pgu.client.pub.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.GoogleUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkersUtils;
import pgu.client.app.utils.ProfileItemsUtils;
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
import pgu.shared.dto.PublicProfile;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class PublicViewImpl extends Composite implements PublicView {

    private static final String ACCORDION_ID = "pgu_geo_profile_items_accordion";

    private static PublicViewImplUiBinder uiBinder = GWT.create(PublicViewImplUiBinder.class);

    interface PublicViewImplUiBinder extends UiBinder<Widget, PublicViewImpl> {
    }

    @UiField(provided = true)
    Section                   profileSection;
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
    HTMLPanel                 spContainer, summaryPanel, profileItemPanel //
    , multiPanel, singlePanel;

    private PublicPresenter   presenter;
    private final ClientUtils u = new ClientUtils();

    public PublicViewImpl() {

        profileSection = new Section("public:profile");

        initWidget(uiBinder.createAndBindUi(this));

        playToolbar.addPlayHandler(new PlayEvent.Handler() {

            @Override
            public void onPlay(final PlayEvent event) {
                GWT.log("[on play]");
                showProfileItem(event.getToken());
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
                showProfileItem(event.getToken());
            }

        });
        playToolbar.addFwdHandler(new FwdEvent.Handler() {

            @Override
            public void onFwd(final FwdEvent event) {
                GWT.log("[on fwd]");
                showProfileItem(event.getToken());
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
                ProfileItemsUtils.displayProfileMarkers(selectedItemType);
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

        singlePanel.setVisible(true);
        multiPanel.setVisible(false);

        summaryPanel.setVisible(true);
        profileItemPanel.setVisible(false);

    }

    private void hideProfileMarkers(final HasSelectedItemType event) {

        multiPanel.setVisible(false);
        singlePanel.setVisible(true);

        final String selectedItemType = event.getSelectedItemType();
        ProfileItemsUtils.hideProfileMarkers(selectedItemType);
    }

    private native void collapseShow(String id) /*-{
        $wnd.$('#' + id).collapse('show');
    }-*/;

    private native void collapseHide(String id) /*-{
        $wnd.$('#' + id).collapse('hide');
    }-*/;

    private void hideProfileItem() {
        hideProfileItemArea();

        MarkersUtils.deleteMovieMarkers();
    }

    private void showProfileItem(final int token) {
        showProfileItemArea();

        fillProfileItemContent(token);

        ProfileItemsUtils.showProfileItemLocations(token, PublicUtils.publicProfileMap());
    }

    private void fillProfileItemContent(final int token) {

        final String description = ProfileItemsUtils.getSelectedProfileItemDescription(token);
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

        final JavaScriptObject google = GoogleUtils.google();
        final JavaScriptObject publicMap = PublicUtils.publicProfileMap();

        if (google == null || publicMap == null) {

            new Timer() {

                @Override
                public void run() {
                    Scheduler.get().scheduleDeferred(new Command() {
                        @Override
                        public void execute() {
                            displayCurrentLocationMarkerOnPublicMap(locationName);
                        }
                    });
                }

            }.schedule(1000);

            return;
        }

        currentLocationMarker = MarkersUtils.createMarker(publicMap, locationName);
    }

    private native void showCurrentLocation(JavaScriptObject marker) /*-{
        var map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();
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

    private void setProfilePublicUrl(final String url) {
        // TODO PGU Sep 25, 2012 linkedin picture
        linkedinPicture.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                Window.open(url, "linkedin_public_profile", null);
            }
        });
    }

    @Override
    public void setProfile(final PublicProfile profile) {

        LocationsUtils.initCaches(profile.getUserAndLocations());

        setProfile(this, profile.getProfile());
    }

    private native void setProfile(PublicViewImpl view, String profile) /*-{

		var j_profile = JSON.parse(profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileName(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.pub.ui.PublicViewUtils::setProfileHeadline(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.pub.ui.PublicViewUtils::setProfileLocation(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.pub.ui.PublicViewUtils::setProfileSpecialties(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileLanguages(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileSummary(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfilePublicUrl(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileItems(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

    }-*/;

    public void addProfileItemsToPlayToolbar() {
        playToolbar.addProfileItems();
    }

    private final HashMap<String, String> id2itemTitle = new HashMap<String, String>();
    private final HashMap<String, String> id2itemContent = new HashMap<String, String>();

    public void showItemsForLocation(final String locationName) {

        id2itemTitle.clear();
        id2itemContent.clear();

        ProfileItemsUtils.fillViewWithProfileItems(this, locationName);

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

}
