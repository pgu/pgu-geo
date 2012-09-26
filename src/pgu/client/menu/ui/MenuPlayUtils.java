package pgu.client.menu.ui;

public class MenuPlayUtils {

    static {
        initProfileMovieIndex();
    }

    private static native void initProfileMovieIndex() /*-{
        $wnd.pgu_geo.profile_current_idx = -1

        $wnd.console.log('initProfileMovieIndex');

        var google = @pgu.client.app.utils.GoogleUtils::google()();
        $wnd.pgu_geo.profile_info_window = new google.maps.InfoWindow();
    }-*/;

    public static native void initIndex(boolean isPastToPresent) /*-{
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        $wnd.pgu_geo.profile_current_idx = isPastToPresent ? -1 : nb_items;
    }-*/;

    public static native void decrementIndex(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            if (idx < 0) {
                return;
            }
            idx--;
        } else {
            if (idx >= nb_items) {
                return;
            }
            idx++;
        }

        $wnd.pgu_geo.profile_current_idx = idx;
    }-*/;

    public static native void incrementIndex(boolean isPastToPresent) /*-{

        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            if (idx >= nb_items) {
                return;
            }
            idx++;
        } else {
            if (idx < 0) {
                return;
            }
            idx--;
        }
        $wnd.pgu_geo.profile_current_idx = idx;

    }-*/;

    public static native boolean showFwdBtn(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            return idx < nb_items -1;
        } else {
            return idx > 0;
        }

    }-*/;


    public static native boolean showBwdBtn(boolean isPastToPresent) /*-{
        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (isPastToPresent) {
            return idx > 0;
        } else {
            return idx < nb_items -1;
        }

    }-*/;

    public static native boolean showProfileItemOnMap(boolean isPastToPresent) /*-{

        var idx = $wnd.pgu_geo.profile_current_idx;
        var nb_items = @pgu.client.profile.ui.ProfileViewUtils::nbItems()();

        if (idx < 0 || idx >= nb_items) { // out of bounds
            return true; // is done
        }

        var item_config = @pgu.client.profile.ui.ProfileViewUtils::getItemConfig(I)(idx);
        var location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(item_config.id);

        $wnd.console.log('showProfileItemOnMap');

        var
            google = @pgu.client.app.utils.GoogleUtils::google()()
          , map = @pgu.client.profile.ui.ProfileUtils::profileMap()()
        ;

        var marker;
        if (location_names.length === 0) {
            marker = @pgu.client.app.utils.MarkersUtils::createMarkerOnProfileMap(Ljava/lang/String;)('0, 0 (Unknown)');

        } else {
            var location_name = location_names[0];
            marker = @pgu.client.app.utils.MarkersUtils::createMarkerOnProfileMap(Ljava/lang/String;)(location_name);

        }

        var info = $wnd.pgu_geo.profile_info_window;
        info.setContent(item_config.short_content + "<br/><br/>" + item_config.long_content);
        info.open(map, marker);

        for ( var i = 1, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];
            @pgu.client.app.utils.MarkersUtils::createMarkerOnProfileMap(Ljava/lang/String;)(location_name);
        }

        return false; // is not done
    }-*/;


}
