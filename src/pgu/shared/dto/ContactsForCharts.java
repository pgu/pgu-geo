package pgu.shared.dto;

import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactsForCharts implements IsSerializable {

    private Country2ContactNumber country2ContactNumber = new Country2ContactNumber();
    private String                chartsPreferences;
    private String                fusionUrls;

    @Override
    public String toString() {
        return "ContactsForCharts [country2ContactNumber=" + country2ContactNumber + ", chartsPreferences="
                + chartsPreferences + ", fusionUrls=" + fusionUrls + "]";
    }

    public Country2ContactNumber getCountry2ContactNumber() {
        return country2ContactNumber;
    }

    public void setCountry2ContactNumber(final Country2ContactNumber country2ContactNumber) {
        this.country2ContactNumber = country2ContactNumber;
    }

    public String getChartsPreferences() {
        return chartsPreferences;
    }

    public void setChartsPreferences(final String chartsPreferences) {
        this.chartsPreferences = chartsPreferences;
    }

    public String getFusionUrls() {
        return fusionUrls;
    }

    public void setFusionUrls(final String fusionUrls) {
        this.fusionUrls = fusionUrls;
    }

}
