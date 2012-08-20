package pgu.client.profile.ui;

import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;

import com.github.gwtbootstrap.client.ui.Heading;
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
    Heading   nameBasic, headlineBasic;
    @UiField
    Paragraph summaryBasic;

    @UiField(provided = true)
    Section   positionsSection, educationSection;

    public ProfileViewImpl() {
        positionsSection = new Section("profile:positions");
        educationSection = new Section("profile:section");

        initWidget(uiBinder.createAndBindUi(this));

        // http://www.linkedin.com/pub/pascal-guilcher/2/3b1/955
        nameBasic.setText("Pascal Guilcher");
        headlineBasic.setText("Senior Web Java J2EE Engineer Developer at SFEIR");
        summaryBasic
                .setText("A senior Java J2EE web developer with broad experience in web-based development, mainly with AJAX interface and Java-based frameworks such as J2EE, Spring and Hibernate. Resourceful, opened to different technologies, willing to join a state of the art web project to build up a successful career.");
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
                return headlineBasic.getText();
            }

            @Override
            public void setText(final String text) {
                // profileBoard.setText(text);
                // TODO PGU Aug 20, 2012 enable it
            }

        };
    }

}
