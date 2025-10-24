package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Category;
import backend.Property_Sales_System.util.CategoryUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing Category entities.
 * Stores and retrieves category data from a local JSON file.
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    /**
     * Get all categories.
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return CategoryUtil.loadCategories();
    }

    /**
     * Add a new category.
     */
    @PostMapping
    public List<Category> addCategory(@RequestBody Category newCategory) {
        List<Category> categories = CategoryUtil.loadCategories();
        if (categories == null) categories = new ArrayList<>();

        // Auto-generate ID (simple local implementation)
        int nextId = categories.stream()
                .mapToInt(Category::getId)
                .max()
                .orElse(0) + 1;
        newCategory.setId(nextId);

        categories.add(newCategory);
        CategoryUtil.saveCategories(categories);
        return categories;
    }

    /**
     * Update an existing category by ID.
     */
    @PutMapping("/{id}")
    public List<Category> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory) {
        List<Category> categories = CategoryUtil.loadCategories();
        for (Category c : categories) {
            if (c.getId() == id) {
                c.setName(updatedCategory.getName());
                break;
            }
        }
        CategoryUtil.saveCategories(categories);
        return categories;
    }

    /**
     * Delete a category by ID.
     */
    @DeleteMapping("/{id}")
    public List<Category> deleteCategory(@PathVariable int id) {
        List<Category> categories = CategoryUtil.loadCategories();
        categories.removeIf(c -> c.getId() == id);
        CategoryUtil.saveCategories(categories);
        return categories;
    }
}
