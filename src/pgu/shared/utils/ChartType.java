package pgu.shared.utils;

public class ChartType {

    public static final String PIE = "pie";
    public static final String BAR = "bar";
    public static final String WORLD = "world";
    public static final String AMERICAS = "americas";
    public static final String EUROPE = "europe";
    public static final String ASIA = "asia";
    public static final String OCEANIA = "oceania";
    public static final String AFRICA = "africa";

    public static String regionCode(final String chartType) {

        if (WORLD.equals(chartType)) {
            return "";

        } else if (AMERICAS.equals(chartType)) {
            return "019";

        } else if (EUROPE.equals(chartType)) {
            return "150";

        } else if (ASIA.equals(chartType)) {
            return "142";

        } else if (OCEANIA.equals(chartType)) {
            return "009";

        } else if (AFRICA.equals(chartType)) {
            return "002";

        }

        throw new IllegalArgumentException("This chart type does not have a region code.");
    }

}
