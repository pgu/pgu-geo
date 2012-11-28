package pgu.client.app.utils;

import pgu.shared.utils.ItemType;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileItemsHelper {

    public boolean isEdu(final String itemType) {
        return ItemType.education.equals(itemType);
    }

    public boolean isXp(final String itemType) {
        return ItemType.experience.equals(itemType);
    }

    public native void sortProfileItemsByDateFromOldToNew(JavaScriptObject profile_items) /*-{

        profile_items.sort(function(a,b) { return a.startD.getTime() - b.startD.getTime() } );
    }-*/;

}
