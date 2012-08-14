package pgu.client.oauth.ui;

import pgu.client.oauth.OAuthPresenter;
import pgu.client.oauth.OAuthView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class OAuthViewImpl extends Composite implements OAuthView {

    private static OAuthViewImplUiBinder uiBinder = GWT.create(OAuthViewImplUiBinder.class);

    interface OAuthViewImplUiBinder extends UiBinder<Widget, OAuthViewImpl> {
    }

    private OAuthPresenter presenter;

    public OAuthViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(final OAuthPresenter presenter) {
        this.presenter = presenter;
    }

}
