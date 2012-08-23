package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StartDate implements IsSerializable {

    private Integer month;
    private Integer year;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(final Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

}
