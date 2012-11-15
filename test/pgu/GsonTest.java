package pgu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import pgu.shared.dto.LinkedinProfile;

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

        // ///////
        final String jsonProfile = new GsonTest().profileTest();
        final LinkedinProfile linkedinProfile = new Gson().fromJson(jsonProfile, LinkedinProfile.class);
        System.out.println(new Gson().toJson(linkedinProfile));

    }

    private String profileTest() {
        // final InputStream is = this.getClass().getResourceAsStream("/WEB-INF/pgu/profile.json");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(
                    "/home/pascal/bin/workspace_juno/pgu-geo/war/WEB-INF/pgu/profile.json"));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        final StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
