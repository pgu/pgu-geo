package pgu.server;

import java.util.ArrayList;

public class Connections {

    private String            total;
    private ArrayList<Person> persons = new ArrayList<Person>();

    public String getTotal() {
        return total;
    }

    public void setTotal(final String total) {
        this.total = total;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(final ArrayList<Person> persons) {
        this.persons = persons;
    }

}
