package pgu.client.profile.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.app.utils.LanguagesUtils;
import pgu.client.app.utils.LocationsUtils;
import pgu.client.app.utils.MarkdownUtils;
import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;
import pgu.shared.dto.Profile;
import pgu.shared.utils.PublicProfileItem;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileViewImpl extends Composite implements ProfileView {

    private static ProfileViewImplUiBinder uiBinder = GWT.create(ProfileViewImplUiBinder.class);

    interface ProfileViewImplUiBinder extends UiBinder<Widget, ProfileViewImpl> {
    }

    @UiField
    Heading                                                 nameBasic;
    @UiField
    Paragraph                                               headlineBasic;
    @UiField
    Popover                                                 summaryBasic;
    @UiField
    Button                                                  summaryBasicBtn;

    @UiField(provided = true)
    Section                                                 overviewSection, experienceSection, educationSection;
    @UiField
    HTMLPanel                                               lgContainer, spContainer;
    @UiField
    Button expPublicState, eduPublicState;
    @UiField
    Tooltip expPublicTooltip, eduPublicTooltip;

    @UiField
    NavLink                                                 locContainer;

    private final ClientUtils                               u               = new ClientUtils();

    public ProfileViewImpl() {

        overviewSection = new Section("profile:overview");
        experienceSection = new Section("profile:experience");
        educationSection = new Section("profile:education");

        initWidget(uiBinder.createAndBindUi(this));
    }

    private static boolean isEduPublic = false;
    private static boolean isExpPublic = false;

    @UiHandler("expPublicState")
    public void clickOnExpPublicState(final ClickEvent e) {

        updatePublicHeader(!isExpPublic, PublicProfileItem.experiences);
        presenter.updatePublicProfile(PublicProfileItem.experiences);
    }

    @UiHandler("eduPublicState")
    public void clickOnEduPublicState(final ClickEvent e) {

        updatePublicHeader(!isEduPublic, PublicProfileItem.educations);
        presenter.updatePublicProfile(PublicProfileItem.educations);
    }

    public void updatePublicHeader(final boolean isPublic, final String publicProfileItem) {
        GWT.log("update " + isPublic + ", " + publicProfileItem);

        if (PublicProfileItem.experiences.equals(publicProfileItem)) {
            expPublicState.setIcon(isPublic ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isExpPublic = isPublic;

        } else if (PublicProfileItem.educations.equals(publicProfileItem)) {
            eduPublicState.setIcon(isPublic ? IconType.EYE_OPEN : IconType.EYE_CLOSE);
            isEduPublic = isPublic;

        }
    }

    private static ProfilePresenter staticPresenter;

    public static native void exportMethods() /*-{
      $wnd.pgu_geo.add_new_location = $entry(@pgu.client.profile.ui.ProfileViewImpl::addNewLocation(Ljava/lang/String;));
      $wnd.pgu_geo.edit_location = $entry(@pgu.client.profile.ui.ProfileViewImpl::editLocation(Ljava/lang/String;Ljava/lang/String;));
    }-*/;

    public static void editLocation(final String itemConfigId, final String locName) {
        staticPresenter.editLocation(itemConfigId, locName);
    }

    public static void addNewLocation(final String itemConfigId) {
        staticPresenter.addNewLocation(itemConfigId);
    }

    @UiHandler("summaryBasicBtn")
    public void clickSummaryBasic(final ClickEvent e) {
        summaryBasic.toggle();
    }

    @UiHandler("locContainer")
    public void clickLocContainer(final ClickEvent e) {
        presenter.showLocationOnMap(locContainer.getText());
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        staticPresenter = presenter;
        this.presenter = presenter;
    }

    @Override
    public void setProfile(final Profile profile) {

        LocationsUtils.initCaches(profile.getUserAndLocations());
        ProfileViewUtils.initCaches();

        setProfile(this, profile.getJson());
    }

    private void setProfileName(final String firstname, final String lastname) {
        nameBasic.setText(firstname + " " + lastname);
    }

    private void setProfileHeadline(final String headline) {
        headlineBasic.setText(headline);
    }

    private void setProfileSpecialties(final String specialtiesLabel) {
        for (final String specialty : specialtiesLabel.split(", ")) {

            if (u.isVoid(specialty)) {
                continue;
            }

            spContainer.add(new HTML(specialty));
        }
    }

    private void setProfileLocation(final String locationName) {

        final boolean hasLocation = !u.isVoid(locationName);
        locContainer.setText(hasLocation ? locationName : "");

        if (hasLocation) {
            LocationsUtils.addCurrentLocationToCache(locationName);
        }
    }

    private void setProfileSummary(final String summary) {
        summaryBasic.getElement().getFirstChildElement().setAttribute("data-content", MarkdownUtils.markdown(summary));
    }

    void setProfileId(final String id) {
        presenter.setProfileId(id);
    }

    void setProfilePublicUrl(final String url) {
        presenter.setProfilePublicUrl(url);
    }

    public LanguagesUtils getLanguagesUtils() {
        return new LanguagesUtils(lgContainer);
    }

    private native void setProfile(ProfileViewImpl view, String profile) /*-{

		@pgu.client.profile.ui.ProfileViewUtils::initDelayForCallingGeocoder()();

		var j_profile = JSON.parse(profile);
		@pgu.client.profile.ui.ProfileUtils::cacheProfile(Lcom/google/gwt/core/client/JavaScriptObject;)(j_profile);

		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileId(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.profile.ui.ProfileSummaryUtils::setProfilePublicUrl(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileName(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileHeadline(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileLocation(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);
		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileSpecialties(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

        var languages_utils = view.@pgu.client.profile.ui.ProfileViewImpl::getLanguagesUtils()();
        @pgu.client.app.utils.LanguagesUtils::setProfileLanguages(Lpgu/client/app/utils/LanguagesUtils;Lcom/google/gwt/core/client/JavaScriptObject;)(languages_utils,j_profile);

		@pgu.client.profile.ui.ProfileSummaryUtils::setProfileSummary(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		@pgu.client.profile.ui.ProfilePositionsUtils::updateProfilePositions(Lpgu/client/profile/ui/ProfileViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

		// TODO display "wish" locations
		// TODO display "holidays" locations

		var positions = j_profile.positions;
		$doc.getElementById('pgu_geo.profile:xp_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createExperienceTable(Lcom/google/gwt/core/client/JavaScriptObject;)(positions);

		var educations = j_profile.educations;
		$doc.getElementById('pgu_geo.profile:edu_table').innerHTML = //
		@pgu.client.profile.ui.ProfileViewUtils::createEducationTable(Lcom/google/gwt/core/client/JavaScriptObject;)(educations);

    }-*/;

    @Override
    public void showPublicPreferences(final String publicPreferences) {

        final String prefs = u.isVoid(publicPreferences) ? "" : publicPreferences;
        PublicProfileUtils.showPublicPreferences(this, prefs);
    }

    @Override
    public String getPublicProfile() {
        return PublicProfileUtils.getPublicProfile();
    }

    @Override
    public void confirmChangeOnPublicProfile(final String publicProfileItem) {

        Tooltip tooltip;
        if (PublicProfileItem.experiences.equals(publicProfileItem)) {
            tooltip = expPublicTooltip;

        } else if (PublicProfileItem.educations.equals(publicProfileItem)) {
            tooltip = eduPublicTooltip;
        } else {
            throw new IllegalArgumentException("item? " + publicProfileItem);
        }

        final Tooltip _tooltip = tooltip;
        _tooltip.show();
        new Timer(){

            @Override
            public void run() {
                _tooltip.hide();
            }

        }.schedule(3000);
    }

    public static void updateCachePublicPreferences() {
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItem.experiences, isExpPublic);
        PublicProfileUtils.updatePublicProfileItem(PublicProfileItem.educations, isEduPublic);
    }

    @Override
    public String getPublicPreferences() {
        return PublicProfileUtils.json_publicPreferences();
    }
}
