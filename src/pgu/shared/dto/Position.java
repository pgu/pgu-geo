package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Position implements IsSerializable {

    private Company   company;
    private boolean   isCurrent;
    private Location  location;
    private StartDate startDate;
    private String    summary;
    private String    title;

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(final boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final StartDate startDate) {
        this.startDate = startDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

}
