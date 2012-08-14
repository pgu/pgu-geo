package pgu.client.oauth.ui;

import pgu.client.oauth.OAuthPresenter;
import pgu.client.oauth.OAuthView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class OAuthViewImpl extends Composite implements OAuthView {

    private static OAuthViewImplUiBinder uiBinder = GWT.create(OAuthViewImplUiBinder.class);

    interface OAuthViewImplUiBinder extends UiBinder<Widget, OAuthViewImpl> {
    }

    @UiField
    Anchor                 oauthLink;
    @UiField
    TextBox                oauthCodeInput;
    @UiField
    Button                 submitBtn;

    private OAuthPresenter presenter;

    public OAuthViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(final OAuthPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Anchor getOAuthLinkWidget() {
        return oauthLink;
    }

    @UiHandler("oauthCodeInput")
    public void keyPressCodeInput(final KeyPressEvent e) {
        if (e.getCharCode() == KeyCodes.KEY_ENTER) {
            presenter.setOauthCode(oauthCodeInput.getText());
        }
    }

    @UiHandler("submitBtn")
    public void clickSubmitBtn(final ClickEvent e) {
        presenter.setOauthCode(oauthCodeInput.getText());
    }

}
