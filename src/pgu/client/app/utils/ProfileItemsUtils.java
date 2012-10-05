package pgu.client.app.utils;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsUtils {

    public static native JavaScriptObject getProfileItems() /*-{
        return $wnd.pgu_geo.profile_items;
    }-*/;

    public static native void setProfileItems(final JavaScriptObject profile) /*-{

        $wnd.pgu_geo.profile_items = {};

        var
            profile_items = $wnd.pgu_geo.profile_items
          , experience = @pgu.shared.utils.ItemType::experience
          , education = @pgu.shared.utils.ItemType::education
        ;

        @pgu.client.app.utils.ProfileItemsUtils::toProfileItems(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)( //
        experience, profile.positions);

        @pgu.client.app.utils.ProfileItemsUtils::toProfileItems(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)( //
        education, profile.educations);

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

            profile_items['all'] = all_items; // we sort 'all' items only as the others already have an order from linkedin
            @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDate(Lcom/google/gwt/core/client/JavaScriptObject;)(all_items);
        }

    }-*/;

    // TODO PGU in the public profile, save in the reverse sort

    private static native void sortProfileItemsByDate(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
    }-*/;

    private static native void toProfileItems(String type, JavaScriptObject items) /*-{

        if (!items) {
            return;
        }

        var
            profile_items = @pgu.client.app.utils.ProfileItemsUtils::getProfileItems()()
          , values = items.values || []
        ;

        profile_items[type] = [];

        for ( var i in values) {
            var profile_item = @pgu.client.app.utils.ProfileItemsUtils::toProfileItem(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type, values[i]);
            profile_items[type].push(profile_item);
        }

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
        ;

        for ( var i = 0, len = location_names.length; i < len; i++) {
            var location_name = location_names[i];

            @pgu.client.app.utils.MarkersUtils::createMarkerOnPublicMap(Ljava/lang/String;)(location_name);
        }

    }-*/;

    public static native String getSelectedProfileItemDescription(int token) /*-{
        var
            selected_profile_items = $wnd.pgu_geo.selected_profile_items
          , profile_item = selected_profile_items[token]
        ;

        return profile_item.short_content + "<br/><br/>" + profile_item.long_content;
    }-*/;

}
