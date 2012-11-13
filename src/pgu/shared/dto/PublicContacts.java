package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class PublicContacts implements IsSerializable {

    private String fusionUrls;

    public String getFusionUrls() {
        return fusionUrls;
    }

    public void setFusionUrls(final String fusionUrls) {
        this.fusionUrls = fusionUrls;
    }

}
