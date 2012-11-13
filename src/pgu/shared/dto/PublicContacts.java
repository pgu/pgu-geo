package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class PublicContacts implements IsSerializable {

    private String contactsNumberByCountry;
    private String fusionUrls;

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

}
