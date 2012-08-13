package pgu.shared.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Connections implements IsSerializable {

    private String            _total;
    private ArrayList<Person> values = new ArrayList<Person>();

    public String get_total() {
        return _total;
    }

    public void set_total(final String _total) {
        this._total = _total;
    }

    public ArrayList<Person> getValues() {
        return values;
    }

    public void setValues(final ArrayList<Person> values) {
        this.values = values;
    }

}
