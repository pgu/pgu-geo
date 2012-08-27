package pgu;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class GsonTest {

    public static void main(final String[] args) {

        final HashMap<Integer, String> id2location = new HashMap<Integer, String>();
        id2location.put(23039762, "Rostock, Germany");
        id2location.put(23039761, "Nantes, France");
        id2location.put(3392191, "Nantes, France");
        id2location.put(23039657, "Nantes, France");

        System.out.println(new Gson().toJson(id2location));

        // /
        final HashMap<Integer, ArrayList<String>> id2locations = new HashMap<Integer, ArrayList<String>>();

        ArrayList<String> locations = new ArrayList<String>();
        locations.add("Rostock, Germany");
        locations.add("Madrid, Spain");

        id2locations.put(23039762, locations);

        locations = new ArrayList<String>();
        locations.add("Nantes, France");
        locations.add("Madrid, Spain");

        id2locations.put(23039761, locations);

        System.out.println(new Gson().toJson(id2locations));

    }

}
