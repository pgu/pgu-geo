package pgu.shared.dto;

import pgu.shared.model.Country2ContactNumber;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContactsForCharts implements IsSerializable {

    private Country2ContactNumber country2ContactNumber = new Country2ContactNumber();
    private String                chartsPreferences;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (chartsPreferences == null ? 0 : chartsPreferences.hashCode());
        result = prime * result + (country2ContactNumber == null ? 0 : country2ContactNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContactsForCharts other = (ContactsForCharts) obj;
        if (chartsPreferences == null) {
            if (other.chartsPreferences != null) {
                return false;
            }
        } else if (!chartsPreferences.equals(other.chartsPreferences)) {
            return false;
        }
        if (country2ContactNumber == null) {
            if (other.country2ContactNumber != null) {
                return false;
            }
        } else if (!country2ContactNumber.equals(other.country2ContactNumber)) {
            return false;
        }
        return true;
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

}
