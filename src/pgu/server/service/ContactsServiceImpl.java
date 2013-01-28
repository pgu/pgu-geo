package pgu.server.service;

import pgu.client.service.ContactsService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.model.ChartsPreferences;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ContactsServiceImpl extends RemoteServiceServlet implements ContactsService {

    private final DAO           dao             = new DAO();
    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();

    @Override
    public void saveChartsPreferences(final String userId, final String jsonChartTypes) {
        final ChartsPreferences chartsPreferences = new ChartsPreferences();
        chartsPreferences.setProfileId(userId);
        chartsPreferences.setValues(jsonChartTypes);
        dao.ofy().async().put(chartsPreferences);
    }

}
