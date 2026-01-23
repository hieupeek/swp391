package com.ams.service;

import com.ams.dao.SupplierDAO;
import com.ams.dao.SupplierDAOImpl;
import com.ams.model.Supplier;
import java.util.List;
import java.util.Optional;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO supplierDAO;

    public SupplierServiceImpl() {
        this.supplierDAO = new SupplierDAOImpl();
    }

    public SupplierServiceImpl(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.findAll();
    }

    @Override
    public Optional<Supplier> getSupplierById(Integer id) {
        if (id == null)
            return Optional.empty();
        return supplierDAO.findById(id);
    }

    @Override
    public List<Supplier> searchSuppliers(String keyword) {
        if (keyword == null)
            return getAllSuppliers();
        return supplierDAO.searchByName(keyword.trim());
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        if (supplier == null)
            throw new IllegalArgumentException("Supplier cannot be null");
        if (supplier.getSupplierName() == null || supplier.getSupplierName().isBlank()) {
            throw new IllegalArgumentException("Supplier name is required");
        }

        supplier.setSupplierName(supplier.getSupplierName().trim());
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        if (supplier == null || supplier.getSupplierId() == null)
            return false;
        if (supplier.getSupplierName() == null || supplier.getSupplierName().isBlank()) {
            throw new IllegalArgumentException("Supplier name is required");
        }

        supplier.setSupplierName(supplier.getSupplierName().trim());
        return supplierDAO.update(supplier);
    }

    @Override
    public boolean deleteSupplier(Integer id) {
        if (id == null)
            return false;
        return supplierDAO.delete(id);
    }
}
