package pgu.client.profile.ui;

import pgu.client.profile.EditLocationView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditLocationViewImpl extends Composite implements EditLocationView {

    private static EditLocationViewImplUiBinder uiBinder = GWT.create(EditLocationViewImplUiBinder.class);

    interface EditLocationViewImplUiBinder extends UiBinder<Widget, EditLocationViewImpl> {
    }

    @UiField
    Modal       container;
    @UiField
    Button      saveBtn, cancelBtn, addBtn;
    @UiField
    ProgressBar progressBar;
    @UiField
    HTMLPanel   notification;

    public EditLocationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        progressBar.setVisible(false);
    }

    @UiHandler("cancelBtn")
    public void clickCancel(final ClickEvent e) {
        container.hide();
    }

    @UiHandler("addBtn")
    public void clickAdd(final ClickEvent e) {
        container.hide();
        // TODO PGU Aug 27, 2012 new LocationEditEvent
    }

}
