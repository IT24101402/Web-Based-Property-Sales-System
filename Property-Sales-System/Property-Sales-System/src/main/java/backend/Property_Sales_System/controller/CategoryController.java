package backend.Property_Sales_System.controller;

import PropertyManagment.propertyease.backend.model.Category;
import PropertyManagment.util.CategoryUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {

    @GetMapping
    public List<Category> getAll() {
        return CategoryUtil.loadCategories();
    }

    @PostMapping
    public String add(@RequestBody Category category) {
        List<Category> list = CategoryUtil.loadCategories();
        int newId = list.isEmpty() ? 1 : list.get(list.size()-1).getId() + 1;
        category.setId(newId);
        list.add(category);
        CategoryUtil.saveCategories(list);
        return "Category added";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable int id, @RequestBody Category updated) {
        List<Category> list = CategoryUtil.loadCategories();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                updated.setId(id);
                list.set(i, updated);
                CategoryUtil.saveCategories(list);
                return "Updated";
            }
        }
        return "Category not found";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        List<Category> list = CategoryUtil.loadCategories();
        boolean removed = list.removeIf(c -> c.getId() == id);
        if (removed) {
            CategoryUtil.saveCategories(list);
            return "Deleted";
        }
        return "Category not found";
    }
}
