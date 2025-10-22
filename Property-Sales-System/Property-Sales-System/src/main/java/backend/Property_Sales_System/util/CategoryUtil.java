package backend.Property_Sales_System.util;

import PropertyManagment.propertyease.backend.model.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CategoryUtil {

    private static final String FILE_PATH = "categories.json";

    public static List<Category> loadCategories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Category>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveCategories(List<Category> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
