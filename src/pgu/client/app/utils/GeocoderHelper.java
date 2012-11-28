package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class GeocoderHelper {

    public void searchGeopoint(final String location_name, final JavaScriptObject callback) {
        GeocoderUtils.searchGeopoint(location_name,callback);
    }

}
