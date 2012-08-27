package pgu.shared.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EducationsWithLocation implements IsSerializable, ItemsWithLocation {

    private ArrayList<EducationWithLocation> values = new ArrayList<EducationWithLocation>();

    @Override
    public ArrayList<EducationWithLocation> getValues() {
        return values;
    }

    public void setValues(final ArrayList<EducationWithLocation> values) {
        this.values = values;
    }

}
