package pgu.client.profile.ui;

public class ProfileViewMap {

    public native void initProfileMap() /*-{

        var div = $wnd.document.getElementById('pgu_geo_profile_map');

        $wnd.console.log('initProfileMap');

        var
            google = @pgu.client.app.utils.GoogleUtils::google()()
          , mapOptions = {
              zoom: 2,
              center: new google.maps.LatLng(0, 0),
              mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        ;

        $wnd.pgu_geo.profile_map = new google.maps.Map( //
            div //
            , mapOptions);

    }-*/;


}
