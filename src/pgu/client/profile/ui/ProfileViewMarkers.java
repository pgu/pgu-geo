package pgu.client.profile.ui;

import pgu.client.app.utils.GoogleHelper;
import pgu.client.app.utils.MarkersHelper;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewMarkers {

    private final MarkersHelper markers = new MarkersHelper();
    private final GoogleHelper google = new GoogleHelper();

    private JavaScriptObject google() {
        return google.google();
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

    public native JavaScriptObject createMarkerOnProfileMap(String location_name, ProfileViewImpl view) /*-{

        var profile_map = view.@pgu.client.profile.ui.ProfileViewImpl::profileMap()
                               ();

        var marker = this.@pgu.client.profile.ui.ProfileViewMarkers::createMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)
                          (profile_map,location_name);

        var search_markers = this.@pgu.client.profile.ui.ProfileViewMarkers::searchMarkers()
                                  ();

        search_markers.push(marker);

        return marker;
    }-*/;

    public native JavaScriptObject createMarkerOnProfileMap(String location_name, String lat, String lng, ProfileViewImpl view) /*-{

        var profile_map = view.@pgu.client.profile.ui.ProfileViewImpl::profileMap()
                               ();

        var marker = this.@pgu.client.profile.ui.ProfileViewMarkers::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                          (profile_map,location_name,lat,lng);

        var search_markers = this.@pgu.client.profile.ui.ProfileViewMarkers::searchMarkers()
                                  ();

        search_markers.push(marker);

        return marker;
    }-*/;


    public void deleteSearchMarkers() {
        final JavaScriptObject searchMarkers = searchMarkers();
        deleteMarkers(searchMarkers);
    }

    // Deletes all markers in the array by removing references to them
    private native void deleteMarkers(JavaScriptObject markers) /*-{

        for (var i = 0; i < markers.length; i++) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;

    public native void searchLocationAndAddMarker(ProfileViewImpl view, String location_name) /*-{

        $wnd.console.log('search location: ' + location_name);

        var geocoder = view.@pgu.client.profile.ui.ProfileViewImpl::geocoder()
                            ();

        var google = this.@pgu.client.profile.ui.ProfileViewMarkers::google()
                          ();

        var map = view.@pgu.client.profile.ui.ProfileViewImpl::profileMap()
                       ();

        var that = this;

        geocoder
                .geocode(
                        {
                            'address' : location_name
                        },
                        function(results, status) {

                            if (status != google.maps.GeocoderStatus.OK) {
                                var msg = "Geocode was not successful for the following reason: " + status;
                                view.@pgu.client.profile.ui.ProfileViewImpl::showNotificationWarning(Ljava/lang/String;)
                                     (msg);
                                return;
                            }

                            var
                                loc = results[0].geometry.location
                              , lat = '' + loc.lat()
                              , lng = '' + loc.lng()
                            ;

                            that.@pgu.client.profile.ui.ProfileViewMarkers::createMarkerOnProfileMap(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lpgu/client/profile/ui/ProfileViewImpl;)
                                 (location_name, lat, lng, view);

                            view.@pgu.client.profile.ui.ProfileViewImpl::cacheLastSearchedLocation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)
                                 (location_name, lat, lng);
                        });
    }-*/;

}
