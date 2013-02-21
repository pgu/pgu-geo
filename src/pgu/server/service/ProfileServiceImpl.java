package pgu.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import pgu.client.service.ProfileService;
import pgu.server.access.DAO;
import pgu.server.app.AppLog;
import pgu.server.utils.AppUtils;
import pgu.shared.model.BaseProfile;
import pgu.shared.model.BasePublicProfile;
import pgu.shared.model.MapPreferences;
import pgu.shared.model.ProfileLocations;
import pgu.shared.model.ProfileUrl;
import pgu.shared.model.PublicLocations;
import pgu.shared.model.PublicMapPreferences;
import pgu.shared.model.PublicPreferences;
import pgu.shared.utils.PublicProfileItemType;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();
    private final DAO           dao             = new DAO();

    //    private static final Index PROFILE_IDX = SearchServiceFactory.getSearchService()
    //            .getIndex(IndexSpec.newBuilder().setName("profile_index"));

    @Override
    public ProfileLocations fetchProfileLocations(final String profileId) {

        final ProfileLocations profileLocations = dao.ofy().find(ProfileLocations.class, profileId);

        if (profileLocations == null) {
            return new ProfileLocations();
        }

        return profileLocations;
    }

    @Override
    public PublicPreferences fetchPublicPreferences(final String profileId) {

        final PublicPreferences publicPreferences = dao.ofy().find(PublicPreferences.class, profileId);

        if (publicPreferences == null) {
            final PublicPreferences initialPublicPreferences = new PublicPreferences();

            initialPublicPreferences.setProfileId(profileId);
            initialPublicPreferences.setValues( //
                    "{\"" + PublicProfileItemType.positions + "\":false" + //
                    ",\"" + PublicProfileItemType.educations + "\":false}");

            dao.ofy().async().put(initialPublicPreferences);

            return initialPublicPreferences;
        }

        return publicPreferences;
    }

    @Override
    public void savePublicPreferences(final String profileId, final String jsonPublicPreferences) {

        final PublicPreferences publicPreferences = new PublicPreferences();
        publicPreferences.setProfileId(profileId);
        publicPreferences.setValues(jsonPublicPreferences);

        dao.ofy().async().put(publicPreferences);
    }

    @Override
    public void saveLocations(final String profileId, final String items2locations, final String referentialLocations) {
        log.info(this, "(\nuser[%s]\n%s\n\n%s\n)", profileId, items2locations, referentialLocations);

        final ProfileLocations profileLocations = new ProfileLocations();
        profileLocations.setProfileId(profileId);
        profileLocations.setItems2locations(items2locations);
        profileLocations.setReferentialLocations(referentialLocations);

        dao.ofy().async().put(profileLocations);

        // TODO PGU Sep 12, 2012 async: update each location with profile

        //
        // public preferences
        // {"wishes":true,"positions":true,"educations":false,"contacts":true}, see PublicProfileItemType
        //
        final PublicPreferences preferences = dao.ofy().find(PublicPreferences.class, profileId);
        if (preferences == null) {
            return;
        }

        final HashMap<String, String> type2isPublic = new Gson().fromJson(preferences.getValues(), HashMap.class);

        //
        // item2locations/cache_items: {"education,1":["Paris","Nantes"],"experience,1":["Madrid"]}
        //
        final HashMap<String, ArrayList<String>> __items2locations = new Gson().fromJson(items2locations, HashMap.class);

        final HashMap<String, ArrayList<String>> publicItems2locations = new HashMap<String, ArrayList<String>>();

        final Boolean educationsArePublic = Boolean.valueOf(type2isPublic.get(PublicProfileItemType.educations));
        if (educationsArePublic) {
            for (final Entry<String, ArrayList<String>> e : __items2locations.entrySet()) {
                final String item = e.getKey();
                if (item.startsWith("education")) {
                    publicItems2locations.put(item, e.getValue());
                }
            }
        }

        final Boolean positionsArePublic = Boolean.valueOf(type2isPublic.get(PublicProfileItemType.positions));
        if (positionsArePublic) {
            for (final Entry<String, ArrayList<String>> e : __items2locations.entrySet()) {
                final String item = e.getKey();
                if (item.startsWith("experience")) {
                    publicItems2locations.put(item, e.getValue());
                }
            }
        }

        final HashSet<String> _publicLocations = new HashSet<String>();
        for (final ArrayList<String> _pLocations : publicItems2locations.values()) {
            _publicLocations.addAll(_pLocations);
        }

        //
        // referential/cache_referential: {"Paris":{"lat":1.2323,"lng":4.5555},"Nantes":{"lat":9.99,"lng":2.22}}
        //
        final HashMap<String, String> publicReferentialLocations = new HashMap<String, String>();

        final HashMap<String, String> __referentialLocations = new Gson().fromJson(referentialLocations, HashMap.class);
        for (final Entry<String, String> e : __referentialLocations.entrySet()) {
            if (_publicLocations.contains(e.getKey())) {
                publicReferentialLocations.put(e.getKey(), e.getValue());
            }
        }

        ///////////////
        final ProfileUrl profileUrl = dao.ofy().find(ProfileUrl.class, profileId);

        final PublicLocations publicLocations = new PublicLocations();
        publicLocations.setProfileUrl(profileUrl.getValue());
        publicLocations.setItems2locations(new Gson().toJson(publicItems2locations));
        publicLocations.setReferentialLocations(new Gson().toJson(publicReferentialLocations));

        dao.ofy().async().put(publicLocations);

    }

    @Override
    public void saveMapPreferences(final String profileId, final String mapPreferences) {

        final MapPreferences mapPref = new MapPreferences();
        mapPref.setProfileId(profileId);
        mapPref.setValues(mapPreferences);

        dao.ofy().async().put(mapPref);

        final ProfileUrl profileUrl = dao.ofy().find(ProfileUrl.class, profileId);

        final PublicMapPreferences publicMapPref = new PublicMapPreferences();
        publicMapPref.setProfileUrl(profileUrl.getValue());
        publicMapPref.setValues(mapPreferences);

        dao.ofy().async().put(publicMapPref);
    }

    @Override
    public void savePublicProfile(final String publicProfileUrl, final String profileId, final String jsonPublicProfile) {

        ProfileUrl profileUrl = dao.ofy().find(ProfileUrl.class, profileId);

        final boolean mustUpdateUrl = profileUrl == null || !publicProfileUrl.equals(profileUrl.getValue());

        if (mustUpdateUrl) {
            profileUrl = new ProfileUrl();
            profileUrl.setProfileId(profileId);
        }
        profileUrl.setValue(publicProfileUrl);
        dao.ofy().async().put(profileUrl);

        final BasePublicProfile basePublicP = new BasePublicProfile();
        basePublicP.setProfileUrl(publicProfileUrl);
        basePublicP.setValue(jsonPublicProfile);
        dao.ofy().async().put(basePublicP);

        if (mustUpdateUrl) {

            final PublicLocations locations = dao.ofy().find(PublicLocations.class, publicProfileUrl);
            if (locations != null) {
                locations.setProfileUrl(publicProfileUrl);
                dao.ofy().async().put(locations);
            }

            // TODO PGU update others entities with the publicProfileUrl
        }

    }

    @Override
    public void saveProfile(final String profileId, final String jsonProfile) {

        final BaseProfile baseP = new BaseProfile();
        baseP.setProfileId(profileId);
        baseP.setValue(jsonProfile);

        dao.ofy().async().put(baseP);

        // TODO PGU Jan 25, 2013 jobs to update positions, educations, wishes, ... documents with geopoints
        // TODO PGU location_idx where each location <-> profile

        //        final Document.Builder docBuilder = Document.newBuilder() //
        //                .addField(Field.newBuilder().setName("profile_id").setText(profileId)) //
        //                .addField(Field.newBuilder().setName("json").setText(jsonProfile)) //
        //                ;
        //
        //        final Document doc = docBuilder.build();
        //        PROFILE_IDX.putAsync(doc);
    }

    @Override
    public MapPreferences fetchMapPreferences(final String profileId) {

        final MapPreferences mapPref = dao.ofy().find(MapPreferences.class, profileId);

        if (mapPref == null) {
            return new MapPreferences();
        }

        return mapPref;
    }

}
