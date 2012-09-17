package pgu.client.pub.ui;

import pgu.client.pub.PublicPresenter;
import pgu.client.pub.PublicView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PublicViewImpl extends Composite implements PublicView {

    private static PublicViewImplUiBinder uiBinder = GWT.create(PublicViewImplUiBinder.class);

    interface PublicViewImplUiBinder extends UiBinder<Widget, PublicViewImpl> {
    }

    private PublicPresenter presenter;

    public PublicViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(final PublicPresenter presenter) {
        this.presenter = presenter;
    }

}
