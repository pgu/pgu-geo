package pgu.client.signin.ui;

import pgu.client.signin.SigninView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SigninViewImpl extends Composite implements SigninView {

    private static SigninViewImplUiBinder uiBinder = GWT.create(SigninViewImplUiBinder.class);

    interface SigninViewImplUiBinder extends UiBinder<Widget, SigninViewImpl> {
    }

    public SigninViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
