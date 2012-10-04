package pgu.client.pub.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.GoogleUtils;
import pgu.client.app.utils.LanguagesUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkdownUtils;
import pgu.client.app.utils.MarkersUtils;
import pgu.client.app.utils.ProfileItemsUtils;
import pgu.client.components.playtoolbar.PlayToolbar;
import pgu.client.components.playtoolbar.event.BwdEvent;
import pgu.client.components.playtoolbar.event.FwdEvent;
import pgu.client.components.playtoolbar.event.PauseEvent;
import pgu.client.components.playtoolbar.event.PlayEvent;
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

    private static PublicViewImplUiBinder uiBinder = GWT.create(PublicViewImplUiBinder.class);

    interface PublicViewImplUiBinder extends UiBinder<Widget, PublicViewImpl> {
    }

    @UiField(provided = true)
    Section                   profileSection;
    @UiField
    NavLink                   locContainer;
    @UiField
    HTML                      summaryContainer, profileItemDescription;
    @UiField
    PlayToolbar               playToolbar;
    @UiField
    HTMLPanel                 lgContainer, spContainer, summaryPanel, profileItemPanel;

    private PublicPresenter   presenter;
    private final ClientUtils u = new ClientUtils();

    public PublicViewImpl() {

        profileSection = new Section("public:profile");

        initWidget(uiBinder.createAndBindUi(this));

        summaryPanel.getElement().setId("pgu_geo_public_summary_container");

        playToolbar.addPlayHandler(new PlayEvent.Handler() {

            @Override
            public void onPlay(final PlayEvent event) {
                showProfileItem(event.getToken());
            }

        });

        playToolbar.addStopHandler(new StopEvent.Handler() {

            @Override
            public void onStop(final StopEvent event) {
                hideProfileItem();
                displayProfileLocation();
            }

        });
        playToolbar.addPauseHandler(new PauseEvent.Handler() {

            @Override
            public void onPause(final PauseEvent event) {
                // nothing to do
            }

        });
        playToolbar.addBwdHandler(new BwdEvent.Handler() {

            @Override
            public void onBwd(final BwdEvent event) {
                showProfileItem(event.getToken());
            }

        });
        playToolbar.addFwdHandler(new FwdEvent.Handler() {

            @Override
            public void onFwd(final FwdEvent event) {
                showProfileItem(event.getToken());
            }

        });

        profileItemPanel.setVisible(false);
    }

    private void hideProfileItem() {
        hideProfileItemArea();

        MarkersUtils.deleteMarkers();
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
        showSummary();
    }

    private void showProfileItemArea() {

        GWT.log("----------show profile item area");

        GWT.log("item panel " + profileItemPanel.isVisible());

        if (profileItemPanel.isVisible()) {
            return;
        }

        GWT.log("hide summary ");

        hideSummary();
        profileItemPanel.setVisible(true);
    }

    @Override
    public void setPresenter(final PublicPresenter presenter) {
        this.presenter = presenter;
    }

    private native void showSummary() /*-{
		$wnd.$('#pgu_geo_public_summary_container').collapse('show');
    }-*/;

    private native void hideSummary() /*-{
		$wnd.$('#pgu_geo_public_summary_container').collapse('hide');
    }-*/;

    public void setProfileName(final String firstName, final String lastName) {
        presenter.setProfileName(firstName + " " + lastName);
    }

    public void setProfileHeadline(final String headline) {
        presenter.setProfileHeadline(headline);
    }

    public void displayProfileLocation() {

        final String locationName = locContainer.getText();
        final boolean hasLocation = !u.isVoid(locationName);

        if (hasLocation) {

            createMarkerOnPublicMap(locationName);
        }
    }

    public void setProfileLocation(final String locationName) {

        final boolean hasLocation = !u.isVoid(locationName);
        locContainer.setText(hasLocation ? locationName : "");

        displayProfileLocation();
    }

    private void createMarkerOnPublicMap(final String locationName) {

        final JavaScriptObject google = GoogleUtils.google();
        final JavaScriptObject map = PublicUtils.publicProfileMap();

        if (google == null || map == null) {

            new Timer() {

                @Override
                public void run() {
                    Scheduler.get().scheduleDeferred(new Command() {
                        @Override
                        public void execute() {
                            createMarkerOnPublicMap(locationName);
                        }
                    });
                }

            }.schedule(1000);
            return;

        }

        MarkersUtils.createMarkerOnPublicMap(locationName);
    }

    public void setProfileSpecialties(final String specialtiesLabel) {
        for (final String specialty : specialtiesLabel.split(", ")) {

            if (u.isVoid(specialty)) {
                continue;
            }

            spContainer.add(new HTML(specialty));
        }
    }

    public LanguagesUtils getLanguagesUtils() {
        return new LanguagesUtils(lgContainer);
    }

    private void setProfileSummary(final String summary) {
        summaryContainer.setHTML(MarkdownUtils.markdown(summary));
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

		var languages_utils = view.@pgu.client.pub.ui.PublicViewImpl::getLanguagesUtils()();
		@pgu.client.app.utils.LanguagesUtils::setProfileLanguages(Lpgu/client/app/utils/LanguagesUtils;Lcom/google/gwt/core/client/JavaScriptObject;)(languages_utils,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileSummary(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfilePublicUrl(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileItems(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

    }-*/;

    public void addProfileItemsToPlayToolbar() {
        playToolbar.addProfileItems();
    }

}
