package pgu.server.service;

import pgu.client.service.PublicProfileService;
import pgu.shared.dto.PublicPreferences;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PublicProfileServiceImpl extends RemoteServiceServlet implements PublicProfileService {

    @Override
    public PublicPreferences fetchPublicPreferences(final String userId) {
        return null;
    }

    @Override
    public void saveProfile(final String json) {
        // TODO Auto-generated method stub

    }

}
