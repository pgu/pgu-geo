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

        profile_items['all'] = [];

        for (var key in profile_items) {

            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if ('all' === key) {
                continue;
            }

            if (profile_items.hasOwnProperty(key)) {
                profile_items['all'] = profile_items['all'].concat(profile_items[key]);
            }
        }

        var all_items = profile_items['all']; // we sort 'all' items only as the others already have an order from linkedin
        @pgu.client.app.utils.ProfileItemsUtils::sortProfileItemsByDate(Lcom/google/gwt/core/client/JavaScriptObject;)(all_items);

    }-*/;

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

        } else if (@pgu.client.app.utils.ProfileItemsUtils::isXp(Ljava/lang/String;)(type)) {

            profile_item.short_content = @pgu.client.app.utils.ProfileItemsUtils::labelXpTitle(Lcom/google/gwt/core/client/JavaScriptObject;)(item);
            profile_item.content_title = "Experience";
            profile_item.long_content = @pgu.client.app.utils.MarkdownUtils::markdown(Ljava/lang/String;)(item.summary);

        } else {

            profile_item.short_content = "";
            profile_item.content_title = "";
            profile_item.long_content = "";

            @pgu.client.app.utils.ClientUtils::log(Ljava/lang/String;)("Unknown type " + type);
        }

        return profile_item;
    }-*/;

}
