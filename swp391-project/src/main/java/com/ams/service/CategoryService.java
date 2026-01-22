package com.ams.service;

import com.ams.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Integer id);

    List<Category> searchCategories(String keyword);

    Category createCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(Integer id);

    boolean isPrefixExists(String prefix);

    boolean isNameExists(String name);
}
