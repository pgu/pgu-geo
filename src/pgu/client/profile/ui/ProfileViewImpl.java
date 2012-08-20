package pgu.client.profile.ui;

import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Section;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ProfileViewImpl extends Composite implements ProfileView {

    private static ProfileViewImplUiBinder uiBinder = GWT.create(ProfileViewImplUiBinder.class);

    interface ProfileViewImplUiBinder extends UiBinder<Widget, ProfileViewImpl> {
    }

    @UiField
    Paragraph profileBoard;

    @UiField(provided = true)
    Section   profileSection, positionsSection, educationSection;

    public ProfileViewImpl() {
        profileSection = new Section("profile:profile");
        positionsSection = new Section("profile:positions");
        educationSection = new Section("profile:section");

        initWidget(uiBinder.createAndBindUi(this));
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HasText getProfileBoard() {
        return new HasText() {

            @Override
            public String getText() {
                return profileBoard.getText();
            }

            @Override
            public void setText(final String text) {
                // profileBoard.setText(text);
                // TODO PGU Aug 20, 2012 enable it
            }

        };
    }

}
