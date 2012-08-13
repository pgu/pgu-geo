package pgu.client.contacts.ui;

import pgu.client.contacts.ContactsPresenter;
import pgu.client.contacts.ContactsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContactsViewImpl extends Composite implements ContactsView {

    private static ContactsViewImplUiBinder uiBinder = GWT.create(ContactsViewImplUiBinder.class);

    interface ContactsViewImplUiBinder extends UiBinder<Widget, ContactsViewImpl> {
    }

    public ContactsViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private ContactsPresenter presenter;

    @Override
    public void setPresenter(final ContactsPresenter presenter) {
        this.presenter = presenter;
    }

}
