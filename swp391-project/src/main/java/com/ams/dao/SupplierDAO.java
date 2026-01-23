package com.ams.dao;

import com.ams.model.Supplier;
import java.util.List;
import java.util.Optional;

public interface SupplierDAO {
    List<Supplier> findAll();

    Optional<Supplier> findById(Integer id);

    List<Supplier> searchByName(String keyword);

    Supplier save(Supplier supplier);

    boolean update(Supplier supplier);

    boolean delete(Integer id);
}
