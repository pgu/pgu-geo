package pgu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Pgu_contacts implements EntryPoint {

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    @Override
    public void onModuleLoad() {

        final Button logInLinkedin = new Button("Login");
        logInLinkedin.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                greetingService.logInLinkedin(new AsyncCallback<Void>() {

                    @Override
                    public void onFailure(final Throwable caught) {
                        GWT.log("onFailure");
                    }

                    @Override
                    public void onSuccess(final Void result) {
                        GWT.log("success");
                    }

                });
            }
        });

        final VerticalPanel vp = new VerticalPanel();
        vp.add(logInLinkedin);

        RootPanel.get().add(vp);

    }
}
