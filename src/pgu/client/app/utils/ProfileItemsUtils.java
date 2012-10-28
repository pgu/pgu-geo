package pgu.client.app.utils;

import pgu.client.pub.ui.PublicViewImpl;
import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class ProfileItemsUtils {

    private static native JavaScriptObject type2profileItems() /*-{
        return $wnd.pgu_geo.type_2_profile_items;
    }-*/;

    private static native JavaScriptObject selectedProfileItems() /*-{
        return $wnd.pgu_geo.selected_profile_items;
    }-*/;

    public static native void setProfileItems(final JavaScriptObject profile) /*-{

        $wnd.pgu_geo.type_2_profile_items = {};

        var
            type_2_profile_items = $wnd.pgu_geo.type_2_profile_items
          , experience = @pgu.shared.utils.ItemType::experience
          , education = @pgu.shared.utils.ItemType::education
        ;

        if (profile.positions) {
            type_2_profile_items[experience] = profile.positions;
        }

        if (profile.educations) {
            type_2_profile_items[education] = profile.educations;
        }

        // check if profile has several sections
        var nb_sections = 0;
        for (var type in type_2_profile_items) {

            if ('__gwt_ObjectId' === type) {
                continue;
            }

            if (type_2_profile_items.hasOwnProperty(type)) {
                nb_sections++;
            }

            if (nb_sections == 2) {
                break;
            }
        }

        if (nb_sections == 2) {

            var all_items = [];
            for (var type in type_2_profile_items) {

                if ('__gwt_ObjectId' === type) {
                    continue;
                }

                if (type_2_profile_items.hasOwnProperty(type)) {
                    all_items = all_items.concat(type_2_profile_items[type]);
                }
            }

            for (var i = 0, len = all_items.length; i < len; i++) {
                var item = all_items[i];
                item.startD = new Date(item.startD);
            }

            type_2_profile_items['all'] = all_items; // we sort 'all' items only as the others already have an order from linkedin
            @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDateFromOldToNew(Lcom/google/gwt/core/client/JavaScriptObject;)(all_items);
        }

    }-*/;

    private static native void sortProfileItemsByDateFromOldToNew(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
    }-*/;

    private static native void sortProfileItemsByDateFromNewToOld(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return b.startD.getTime() - a.startD.getTime() } );
    }-*/;

    private static boolean isEdu(final String itemType) {
        return ItemType.education.equals(itemType);
    }

    private static boolean isXp(final String itemType) {
        return ItemType.experience.equals(itemType);
    }

    private static native String labelXpTitle(JavaScriptObject position) /*-{
        //  SFEIR<br/>Senior Web Java J2EE Engineer Developer
        var title = [];

        if (position.company && position.company.name) {

            title.push(position.company.name);
        }

        if (position.title) {
            title.push(position.title);
        }

        return title.join('<br/>');
    }-*/;

    private static native String labelEduTitle(JavaScriptObject education) /*-{
        // Universität Rostock<br/>International Trade
        var title = [];

        if (education.schoolName) {
            title.push(education.schoolName);
        }

        if (education.fieldOfStudy) {
            title.push(education.fieldOfStudy);
        }

        return title.join('<br/>');
    }-*/;


    public static native JavaScriptObject toProfileItem(String type, JavaScriptObject item) /*-{

        var profile_item = {};
        profile_item.id = type + '_' + item.id;

        profile_item.dates = @pgu.client.profile.ui.ProfileDateUtils::labelDates(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
        profile_item.startD = @pgu.client.profile.ui.ProfileDateUtils::getStartDate(Lcom/google/gwt/core/client/JavaScriptObject;)(item);

        if (@pgu.client.app.utils.ProfileItemsUtils::isEdu(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelEduTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Education";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.notes);

            @pgu.client.profile.ui.PublicProfileUtils::addEducation(Lcom/google/gwt/core/client/JavaScriptObject;)(profile_item);

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Experience";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.summary);

            @pgu.client.profile.ui.PublicProfileUtils::addExperience(Lcom/google/gwt/core/client/JavaScriptObject;)(profile_item);

        } else {

            profile_item.short_content = "";
            profile_item.content_title = "";
            profile_item.long_content = "";

            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown type " + type);
        }

        return profile_item;
    }-*/;

    public static native boolean hasAllOption() /*-{
        var type_2_profile_items = @pgu.client.app.utils.ProfileItemsUtils::type2profileItems()();
        return type_2_profile_items.hasOwnProperty('all');
    }-*/;

    public static native boolean hasExperienceOption() /*-{
        var type_2_profile_items = @pgu.client.app.utils.ProfileItemsUtils::type2profileItems()();
        return type_2_profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::experience);
    }-*/;

    public static native boolean hasEducationOption() /*-{
        var type_2_profile_items = @pgu.client.app.utils.ProfileItemsUtils::type2profileItems()();
        return type_2_profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::education);
    }-*/;

    public static native void setSelectedProfileItems(final String selectedItemType) /*-{
        var type_2_profile_items = @pgu.client.app.utils.ProfileItemsUtils::type2profileItems()();
        $wnd.pgu_geo.selected_profile_items = type_2_profile_items[selectedItemType];
    }-*/;

    public static native int nbSelectedItems() /*-{
        var selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()();

        if (selected_profile_items) {
            return selected_profile_items.length;
        }

        return 0;
    }-*/;

    public static native void showMovieProfileItemLocations(final int token, final JavaScriptObject map) /*-{

        var selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()();
        if (!selected_profile_items) {
            return;
        }

        var
            profile_item = selected_profile_items[token]
          , location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(profile_item.id)
          , info_content = []
          , first_marker = null
        ;

        info_content.push('<div>');

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];

            var marker = @pgu.client.app.utils.MarkersUtils::createMovieMarkerOnPublicMap(Ljava/lang/String;)(location_name);
            if (i === 0) {
                first_marker = marker;
            }

            info_content.push('<div><b>' + location_name + '</b></div>');
        }

        if ('<div>' !== info_content.join('')) {
            info_content.push('<br/>');
        }

        if (profile_item.dates) {
            info_content.push('<div>' + profile_item.dates + '</div>');
            info_content.push('<br/>');
        }

        if (profile_item.short_content) {
            info_content.push('<div>' + profile_item.short_content + '</div>');
        }

        info_content.push('</div>');

        if (!$wnd.pgu_geo.public_profile_info_window) {
            var google = @pgu.client.app.utils.GoogleUtils::google()();
            $wnd.pgu_geo.public_profile_info_window = new google.maps.InfoWindow();
        }

        if (first_marker == null) {
            first_marker = @pgu.client.app.utils.MarkersUtils::createMovieUnknownMarkerOnPublicMap()();
        }

        var info_content_str = info_content.join('');

        var info = $wnd.pgu_geo.public_profile_info_window;
        info.setContent(info_content_str);

        if (info_content_str === '<div></div>') {
            info.close();

        } else {
            info.open(map, first_marker);
        }

    }-*/;

    public static native String getSelectedProfileItemDescription(int token) /*-{
        var
            selected_profile_items = @pgu.client.app.utils.ProfileItemsUtils::selectedProfileItems()()
          , profile_item = selected_profile_items[token]
        ;

        return profile_item.long_content;
    }-*/;

    public static void initCachesLocation2MarkerAndItems(final PublicViewImpl view) {

        new Timer() {

            @Override
            public void run() {
                Scheduler.get().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        initCachesLocation2MarkerAndItemsInternal(view);
                    }
                });
            }

        }.schedule(1000);

    }

    private static native void initCachesLocation2MarkerAndItemsInternal(PublicViewImpl view) /*-{

        var google = @pgu.client.app.utils.GoogleUtils::google()();
        if (!google) {
            @pgu.client.app.utils.ProfileItemsUtils::initCachesLocation2MarkerAndItems(Lpgu/client/pub/ui/PublicViewImpl;)(view);
            return;
        }

        $wnd.pgu_geo.type_2_locations = {};
        $wnd.pgu_geo.location_2_marker = {};
        $wnd.pgu_geo.location_2_items = {};

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        var type_2_profile_items = @pgu.client.app.utils.ProfileItemsUtils::type2profileItems()();

        for (var type in type_2_profile_items) {

            if ('__gwt_ObjectId' === type) {
                continue;
            }

            if ('all' === type) {
                continue;
            }

            if (type_2_profile_items.hasOwnProperty(type)) {

                if (!cache_type.hasOwnProperty(type)) {
                    cache_type[type] = [];
                }

                var
                    profile_items = type_2_profile_items[type]
                ;

                for (var k = 0; k < profile_items.length; k++) {

                    var
                        profile_item = profile_items[k]
                      , location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(profile_item.id)
                    ;

                    for (var i = 0; i < location_names.length; i++) {

                        var
                            location_name = location_names[i]
                          , geopoint_is_available = @pgu.client.app.utils.LocationsUtils::isLocationInReferential(Ljava/lang/String;)(location_name);
                        ;

                        if (geopoint_is_available) {

                            if (!cache_marker.hasOwnProperty(location_name)) {

                                var
                                    geopoint = @pgu.client.app.utils.LocationsUtils::getGeopoint(Ljava/lang/String;)(location_name)
                                  , lat = geopoint.lat
                                  , lng = geopoint.lng
                                ;

                                var marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(null,location_name,lat,lng);
                                google.maps.event.addListener(marker, 'click', //
                                    @pgu.client.app.utils.ProfileItemsUtils::clickOnMarker(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Lpgu/client/pub/ui/PublicViewImpl;)(marker, location_name, view)
                                );

                                // TODO PGU counter on how many items are associated to this marker and
                                // then overrides the marker's title? marker.setTitle(location_name + ': <b>3</b>');

                                cache_marker[location_name] = marker;
                            }

                            if (cache_type[type].indexOf(location_name) == -1) {
                                cache_type[type].push(location_name);
                            }

                            if (!cache_items.hasOwnProperty(location_name)) {
                                cache_items[location_name] = [].concat(profile_item);

                            } else {
                                cache_items[location_name].push(profile_item);
                            }

                        }
                    }
                }

            }
        }

        for (var location_name in cache_items) {

            if ('__gwt_ObjectId' === location_name) {
                continue;
            }

            var items = cache_items[location_name];
            @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDateFromNewToOld(Lcom/google/gwt/core/client/JavaScriptObject;)(items);
        }

    }-*/;

    private static native JavaScriptObject clickOnMarker(JavaScriptObject marker, String location_name, PublicViewImpl view) /*-{
        return function() {
                    view.@pgu.client.pub.ui.PublicViewImpl::showItemsForLocation(Ljava/lang/String;)(location_name);
                }
    }-*/;

    public static native void displayProfileMarkers(final String selectedItemType) /*-{
        var public_map = @pgu.client.pub.ui.PublicUtils::publicProfileMap()();

        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        public_map, selectedItemType);
    }-*/;

    public static native void hideProfileMarkers(String selectedItemType) /*-{
        @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkers(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)( //
        null, selectedItemType);
    }-*/;

    private static native void setMapOnProfileMarkers(JavaScriptObject map, String selectedItemType) /*-{

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        if ('all' === selectedItemType) {

            for (var type in cache_type) {
                if ('__gwt_ObjectId' === type) {
                    continue;
                }

                if (cache_type.hasOwnProperty(type)) {
                    var location_names = cache_type[type];
                    @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
                    map, location_names);
                }
            }

        } else {

            var location_names = cache_type[selectedItemType];
            @pgu.client.app.utils.ProfileItemsUtils::setMapOnProfileMarkersInternal(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)( //
            map, location_names);

        }

    }-*/;

    private static native void setMapOnProfileMarkersInternal(JavaScriptObject map, JavaScriptObject location_names) /*-{

        var
            cache_type = $wnd.pgu_geo.type_2_locations
          , cache_marker = $wnd.pgu_geo.location_2_marker
          , cache_items = $wnd.pgu_geo.location_2_items
        ;

        for (var i = 0; i < location_names.length; i++) {
            var
                location_name = location_names[i]
              , marker = cache_marker[location_name]
            ;

            marker.setMap(map);
        }

    }-*/;

    public static native void fillViewWithProfileItems(final PublicViewImpl view, final String location_name) /*-{

        var
            cache_items = $wnd.pgu_geo.location_2_items
          , items = cache_items[location_name]
        ;

        for ( var i = 0; i < items.length; i++) {
            var item = items[i];

            var
                id = item.id
              , title = []
              , content = item.long_content
            ;

            if (item.dates) {
                title.push(item.dates);
                title.push(' - ');
            }

            if (item.short_content) {
                title.push(item.short_content);
            }

            view.@pgu.client.pub.ui.PublicViewImpl::fillWithProfileItem(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)( //
            id, title.join(''), content);
        }

    }-*/;


}
