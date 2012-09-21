package pgu.client.pub.ui;

import pgu.client.app.utils.ClientUtils;
import pgu.client.pub.PublicMenuView;

import com.github.gwtbootstrap.client.ui.Brand;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PublicMenuViewImpl extends Composite implements PublicMenuView {

    private static PublicMenuViewImplUiBinder uiBinder = GWT.create(PublicMenuViewImplUiBinder.class);

    interface PublicMenuViewImplUiBinder extends UiBinder<Widget, PublicMenuViewImpl> {
    }

    @UiField
    Brand userName, headline;

    private final ClientUtils u = new ClientUtils();

    public PublicMenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setUserName(final String userName) {
        this.userName.setText(u.isVoid(userName) ? "" : userName);
    }

    @Override
    public void setUserHeadline(final String userHeadline) {
        headline.setText(u.isVoid(userHeadline) ? "" : userHeadline);
    }

}
