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

    public native String labelEduTitle(JavaScriptObject education) /*-{
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

    public native String labelXpTitle(JavaScriptObject position) /*-{
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


}
