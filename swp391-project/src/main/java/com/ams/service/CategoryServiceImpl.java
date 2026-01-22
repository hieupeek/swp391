package com.ams.service;

import com.ams.dao.CategoryDAO;
import com.ams.dao.CategoryDAOImpl;
import com.ams.model.Category;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    // Constructor injection for testing
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> searchCategories(String keyword) {
        if (keyword == null) {
            return getAllCategories();
        }
        return categoryDAO.searchByName(keyword.trim());
    }

    @Override
    public Category createCategory(Category category) {
        // Validation
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new IllegalArgumentException("Category Name is required");
        }

        if (category.getPrefixCode() == null || category.getPrefixCode().isBlank()) {
            throw new IllegalArgumentException("Prefix Code is required");
        }

        // Normalize Prefix (Uppercase)
        String prefix = category.getPrefixCode().trim().toUpperCase();
        category.setPrefixCode(prefix);

        // Check uniqueness
        if (categoryDAO.existsByPrefix(prefix)) {
            throw new IllegalArgumentException("Prefix Code '" + prefix + "' already exists");
        }

        if (categoryDAO.existsByName(category.getCategoryName().trim())) {
            throw new IllegalArgumentException(
                    "Category Name '" + category.getCategoryName().trim() + "' already exists");
        }

        return categoryDAO.save(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        // Validation
        if (category == null || category.getCategoryId() == null) {
            return false;
        }

        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new IllegalArgumentException("Category Name is required");
        }

        // Check existence
        Optional<Category> existingOpt = categoryDAO.findById(category.getCategoryId());
        if (existingOpt.isEmpty()) {
            return false;
        }

        Category existing = existingOpt.get();

        // Ensure Prefix is NOT changed (Business Rule)
        // Even if the input object has a different prefix, we don't update it in DAO
        // But to be safe, we can enforce it here or just let DAO handle it (DAO SQL
        // update doesn't include prefix)
        // DAO SQL: UPDATE categories SET category_name = ?, description = ? WHERE
        // category_id = ?
        // So prefix change is impossible via update() method in DAO.

        // Check name uniqueness if changed
        if (!existing.getCategoryName().equalsIgnoreCase(category.getCategoryName().trim())) {
            if (categoryDAO.existsByName(category.getCategoryName().trim())) {
                throw new IllegalArgumentException(
                        "Category Name '" + category.getCategoryName().trim() + "' already exists");
            }
        }

        return categoryDAO.update(category);
    }

    @Override
    public boolean deleteCategory(Integer id) {
        if (id == null) {
            return false;
        }
        // TODO: Check if category is used by any assets before deleting?
        // Current requirement doesn't specify, but DB integrity might prevent it if
        // foreign keys exist.
        return categoryDAO.delete(id);
    }

    @Override
    public boolean isPrefixExists(String prefix) {
        if (prefix == null)
            return false;
        return categoryDAO.existsByPrefix(prefix.trim().toUpperCase());
    }

    @Override
    public boolean isNameExists(String name) {
        if (name == null)
            return false;
        return categoryDAO.existsByName(name.trim());
    }
}
