package com.ams.dao;

import com.ams.model.Category;
import java.util.List;
import java.util.Optional;

/**
 * Interface cho việc truy cập dữ liệu bảng Categories
 */
public interface CategoryDAO {

    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Optional<Category> findByPrefix(String prefix);

    List<Category> searchByName(String keyword);

    Category save(Category category);

    boolean update(Category category);

    boolean delete(Integer id);

    boolean existsByPrefix(String prefix);

    boolean existsByName(String name);
}
