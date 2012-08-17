package pgu.client.oauth.ui;

import pgu.client.oauth.OAuthPresenter;
import pgu.client.oauth.OAuthView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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

        oauthLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();

                Window.open(oauthLink.getHref(), "linkedin", "height=500, width=500");
            }
        });
    }

    @Override
    public void setPresenter(final OAuthPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("oauthCodeInput")
    public void keyPressCodeInput(final KeyPressEvent e) {

        if (e.getCharCode() == KeyCodes.KEY_ENTER) {
            e.preventDefault();
            e.stopPropagation();

            presenter.setOauthCode(oauthCodeInput.getText());
        }
    }

    @UiHandler("submitBtn")
    public void clickSubmitBtn(final ClickEvent e) {
        presenter.setOauthCode(oauthCodeInput.getText());
    }

    @Override
    public void setOAuthUrl(final String authorizationUrl) {
        oauthLink.setHref(authorizationUrl);
    }

}
