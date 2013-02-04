package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class PublicContacts implements IsSerializable {

    private String contactsNumberByCountry;
    private String fusionUrls;
    private String chartsPreferences;
    private String profileUrl = "TODO";

    @Override
    public String toString() {
        return "PublicContacts [contactsNumberByCountry=" + contactsNumberByCountry + ", fusionUrls=" + fusionUrls
                + ", chartsPreferences=" + chartsPreferences + "]";
    }

    public String getFusionUrls() {
        return fusionUrls;
    }

    public void setFusionUrls(final String fusionUrls) {
        this.fusionUrls = fusionUrls;
    }

    public String getContactsNumberByCountry() {
        return contactsNumberByCountry;
    }

    public void setContactsNumberByCountry(final String contactsNumberByCountry) {
        this.contactsNumberByCountry = contactsNumberByCountry;
    }

    public String getChartsPreferences() {
        return chartsPreferences;
    }

    public void setChartsPreferences(final String chartsPreferences) {
        this.chartsPreferences = chartsPreferences;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
