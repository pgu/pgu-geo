package pgu.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContactsServiceAsync {

    void saveChartsPreferences(String userId, String jsonChartTypes, AsyncCallback<Void> asyncCallbackApp);

}
