package pgu.client.app.utils;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsUtils {

    public static native JavaScriptObject profileItems() /*-{
        return $wnd.pgu_geo.profile_items;
    }-*/;

    public static native void setProfileItems(final JavaScriptObject profile) /*-{

        $wnd.pgu_geo.profile_items = {};

        var
            profile_items = $wnd.pgu_geo.profile_items
          , experience = @pgu.shared.utils.ItemType::experience
          , education = @pgu.shared.utils.ItemType::education
        ;

        if (profile.positions) {
            profile_items[experience] = profile.positions;
        }

        if (profile.educations) {
            profile_items[education] = profile.educations;
        }

        // check if profile has several sections
        var nb_sections = 0;
        for (var key in profile_items) {

            if ('__gwt_ObjectId' === key) {
                continue;
            }

            if (profile_items.hasOwnProperty(key)) {
                nb_sections++;
            }

            if (nb_sections == 2) {
                break;
            }
        }

        if (nb_sections == 2) {

            var all_items = [];
            for (var key in profile_items) {

                if ('__gwt_ObjectId' === key) {
                    continue;
                }

                if (profile_items.hasOwnProperty(key)) {
                    all_items = all_items.concat(profile_items[key]);
                }
            }

            for (var i = 0, len = all_items.length; i < len; i++) {
                var item = all_items[i];
                item.startD = new Date(item.startD);
            }

            profile_items['all'] = all_items; // we sort 'all' items only as the others already have an order from linkedin
            @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDate(Lcom/google/gwt/core/client/JavaScriptObject;)(all_items);
        }

    }-*/;

    private static native void sortProfileItemsByDate(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
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
        return $wnd.pgu_geo.profile_items.hasOwnProperty('all');
    }-*/;

    public static native boolean hasExperienceOption() /*-{
        return $wnd.pgu_geo.profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::experience);
    }-*/;

    public static native boolean hasEducationOption() /*-{
        return $wnd.pgu_geo.profile_items.hasOwnProperty(@pgu.shared.utils.ItemType::education);
    }-*/;

    public static native void setSelectedProfileItems(final String selectedItemType) /*-{
        $wnd.pgu_geo.selected_profile_items = $wnd.pgu_geo.profile_items[selectedItemType];
    }-*/;

    public static native int nbSelectedItems() /*-{

        if ($wnd.pgu_geo.selected_profile_items) {
            return $wnd.pgu_geo.selected_profile_items.length;
        }

        return 0;
    }-*/;

    public static native void showProfileItemLocations(final int token, final JavaScriptObject map) /*-{

        if (!$wnd.pgu_geo.selected_profile_items) {
            return;
        }

        var
            selected_profile_items = $wnd.pgu_geo.selected_profile_items
          , profile_item = selected_profile_items[token]
          , location_names = @pgu.client.app.utils.LocationsUtils::getLocationNames(Ljava/lang/String;)(profile_item.id)
          , info_content = []
          , first_marker = null
        ;

        info_content.push('<div>');

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];

            var marker = @pgu.client.app.utils.MarkersUtils::createMarkerOnPublicMap(Ljava/lang/String;)(location_name);
            if (i === 0) {
                first_marker = marker;
            }

            info_content.push('<div>' + location_name + '</div>');
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
            var google = @pgu.client.app.utils.GoogleUtils::google()();
            first_marker = @pgu.client.app.utils.MarkersUtils::createMarkerWithGeopoint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(map,'Unknown','0','0');
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
            selected_profile_items = $wnd.pgu_geo.selected_profile_items
          , profile_item = selected_profile_items[token]
        ;

        return profile_item.long_content;
    }-*/;

}
