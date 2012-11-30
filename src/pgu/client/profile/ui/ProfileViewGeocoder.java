package pgu.client.profile.ui;

import pgu.client.app.utils.GeocoderUtils;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewGeocoder {

    public native JavaScriptObject geocoder() /*-{
        return $wnd.pgu_geo.geocoder;
    }-*/;

    public void searchGeopoint(final String location_name, final JavaScriptObject callback) {
        GeocoderUtils.searchGeopoint(location_name,callback);
    }

}
