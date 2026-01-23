package com.ams.service;

import com.ams.model.Supplier;
import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    Optional<Supplier> getSupplierById(Integer id);

    List<Supplier> searchSuppliers(String keyword);

    Supplier createSupplier(Supplier supplier);

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplier(Integer id);
}
