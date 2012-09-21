package pgu.client.pub.ui;

import pgu.client.pub.PublicMenuView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PublicMenuViewImpl extends Composite implements HasText, PublicMenuView {

    private static PublicMenuViewImplUiBinder uiBinder = GWT.create(PublicMenuViewImplUiBinder.class);

    interface PublicMenuViewImplUiBinder extends UiBinder<Widget, PublicMenuViewImpl> {
    }

    public PublicMenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Button button;

    public PublicMenuViewImpl(final String firstName) {
        initWidget(uiBinder.createAndBindUi(this));
        button.setText(firstName);
    }

    @UiHandler("button")
    void onClick(final ClickEvent e) {
        Window.alert("Hello!");
    }

    @Override
    public void setText(final String text) {
        button.setText(text);
    }

    @Override
    public String getText() {
        return button.getText();
    }

}
