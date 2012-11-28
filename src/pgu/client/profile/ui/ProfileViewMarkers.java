package pgu.client.profile.ui;

import pgu.client.app.utils.MarkersUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewMarkers {

    // TODO PGU rapatrier les search markers ici

    public void createMarkerOnProfileMap(final String location_name) {
        MarkersUtils.createMarkerOnProfileMap(location_name);
    }

    public void createMarkerOnProfileMap(final String location_name, final String lat, final String lng) {
        MarkersUtils.createMarkerOnProfileMap(location_name, lat, lng);
    }

    public void deleteSearchMarkers() {

        final JavaScriptObject jso = MarkersUtils.searchMarkers();
        MarkersUtils.deleteMarkers(jso);
    }

}
