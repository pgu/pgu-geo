package pgu.client.profile.ui;

import pgu.client.app.utils.MarkersHelper;
import pgu.client.app.utils.MarkersUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewMarkers {

    private final ProfileViewMap viewMap = new ProfileViewMap();
    private final MarkersHelper markers = new MarkersHelper();

    private JavaScriptObject profileMap() {
        return viewMap.profileMap();
    }

    private JavaScriptObject createMarker(final JavaScriptObject profile_map, final String location_name) {
        return markers.createMarker(profile_map, location_name);
    }

    private JavaScriptObject createMarkerWithGeopoint(final JavaScriptObject profile_map, final String location_name, final String lat, final String lng) {
        return markers.createMarkerWithGeopoint(profile_map, location_name, lat, lng);
    }

    public native JavaScriptObject searchMarkers() /*-{

        if (!$wnd.pgu_geo.search_markers) {
            $wnd.pgu_geo.search_markers = [];
        }

        return $wnd.pgu_geo.search_markers;
    }-*/;

    public native JavaScriptObject createMarkerOnProfileMap(String location_name) /*-{

        var profile_map = this.@pgu.client.profile.ui.ProfileViewMarkers::profileMap()
                               ();

        var marker = this.@pgu.client.profile.ui.ProfileViewMarkers::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)
                          (profile_map,location_name);

        var search_markers = this.@pgu.client.profile.ui.ProfileViewMarkers::searchMarkers()
                                  ();

        search_markers.push(marker);

        return marker;
    }-*/;

    public native JavaScriptObject createMarkerOnProfileMap(String location_name, String lat, String lng) /*-{

        var profile_map = this.@pgu.client.profile.ui.ProfileViewMarkers::profileMap()
                               ();

        var marker = this.@pgu.client.profile.ui.ProfileViewMarkers::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                          (profile_map,location_name,lat,lng);

        var search_markers = this.@pgu.client.profile.ui.ProfileViewMarkers::searchMarkers()
                                  ();

        search_markers.push(marker);

        return marker;
    }-*/;


    public void deleteSearchMarkers() {

        final JavaScriptObject jso = searchMarkers();
        MarkersUtils.deleteMarkers(jso);
    }

}
