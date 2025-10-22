package backend.Property_Sales_System.util;

import java.util.ArrayList;
import java.util.List;

public class PropertyUtil {
    private static List<Property> properties = new ArrayList<>();

    public static List<Property> loadProperties() {
        return properties;
    }

    public static void saveProperties(List<Property> list) {
        properties = list;
    }

    private static class Property {
    }
}
