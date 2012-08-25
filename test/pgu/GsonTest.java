package pgu;

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

    }

}
