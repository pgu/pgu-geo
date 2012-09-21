package pgu.client.pub.ui;

import pgu.client.pub.PublicPresenter;
import pgu.client.pub.PublicView;
import pgu.shared.dto.PublicProfile;

import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PublicViewImpl extends Composite implements PublicView {

    private static PublicViewImplUiBinder uiBinder = GWT.create(PublicViewImplUiBinder.class);

    interface PublicViewImplUiBinder extends UiBinder<Widget, PublicViewImpl> {
    }

    @UiField(provided = true)
    Section                 profileSection, contactsSection;

    private PublicPresenter presenter;

    public PublicViewImpl() {

        profileSection = new Section("public:profile");
        contactsSection = new Section("public:contacts");

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(final PublicPresenter presenter) {
        this.presenter = presenter;
    }

    public void setProfileName(final String firstName, final String lastName) {
        presenter.setProfileName(firstName + " " + lastName);
    }

    @Override
    public void setProfile(final PublicProfile profile) {
        setProfile(this, profile.getProfile());

        // profile.getPreferences()
    }

    private native void setProfile(PublicViewImpl view, String profile) /*-{

		var j_profile = JSON.parse(profile);

		@pgu.client.pub.ui.PublicViewUtils::setProfileName(Lpgu/client/pub/ui/PublicViewImpl;Lcom/google/gwt/core/client/JavaScriptObject;)(view,j_profile);

    }-*/;

}
