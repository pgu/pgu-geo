package pgu.shared.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Educations implements IsSerializable {

    private ArrayList<Education> values = new ArrayList<Education>();

    public ArrayList<Education> getValues() {
        return values;
    }

    public void setValues(final ArrayList<Education> values) {
        this.values = values;
    }

}
