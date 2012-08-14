package pgu.client.profile.ui;

import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProfileViewImpl extends Composite implements ProfileView {

    private static ProfileViewImplUiBinder uiBinder = GWT.create(ProfileViewImplUiBinder.class);

    interface ProfileViewImplUiBinder extends UiBinder<Widget, ProfileViewImpl> {
    }

    public ProfileViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    // is user logged in linkedin?
    // if no, then offer him to authorize the app to access its account
    // then with his code, he allows the call to its profile and contacts
    // else,
    // -- if the user is in our database
    // -- then we get its profile
    // -- else we offer him to let the app to connect to its account (the same as above)
}
