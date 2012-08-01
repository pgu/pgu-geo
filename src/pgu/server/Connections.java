package pgu.server;

import java.util.ArrayList;

public class Connections {

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
