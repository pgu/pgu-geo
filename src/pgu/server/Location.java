package pgu.server;

public class Location {
    private String  name;
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

}
