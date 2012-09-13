package pgu.client.app.utils;

public class MarkersUtils {

    static {
        initMarkersArray();
    }

    private static native void initMarkersArray() /*-{
        $wnd.pgu_geo.markersArray = [];
    }-*/;

    // https://developers.google.com/maps/documentation/javascript/overlays#RemovingOverlays
    //Removes the overlays from the map, but keeps them in the array
    public static native void clearOverlays() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
          markers[i].setMap(null);
        }
    }-*/;

    // Shows any overlays currently in the array
    public static native void showOverlays() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
            markers[i].setMap(map);
        }
    }-*/;

    // Deletes all markers in the array by removing references to them
    public static native void deleteMarkers() /*-{
        var markers = $wnd.pgu_geo.markersArray;
        for (i in markers) {
              markers[i].setMap(null);
        }
        markers.length = 0;
    }-*/;


}
