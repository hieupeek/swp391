package com.ams.service.impl;

import com.ams.service.*;

import com.ams.dao.AssetDAO;
import com.ams.dao.impl.AssetDAOImpl;
import com.ams.dao.AssetHistoryDAO;
import com.ams.dao.impl.AssetHistoryDAOImpl;
import com.ams.dao.CategoryDAO;
import com.ams.dao.impl.CategoryDAOImpl;
import com.ams.model.Asset;
import com.ams.model.AssetHistory;
import com.ams.model.Category;
import com.ams.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AssetServiceImpl implements AssetService {

    private final AssetDAO assetDAO;
    private final CategoryDAO categoryDAO;
    private final AssetHistoryDAO assetHistoryDAO;

    public AssetServiceImpl() {
        this.assetDAO = new AssetDAOImpl();
        this.categoryDAO = new CategoryDAOImpl();
        this.assetHistoryDAO = new AssetHistoryDAOImpl();
    }

    // For testing
    public AssetServiceImpl(AssetDAO assetDAO, CategoryDAO categoryDAO, AssetHistoryDAO assetHistoryDAO) {
        this.assetDAO = assetDAO;
        this.categoryDAO = categoryDAO;
        this.assetHistoryDAO = assetHistoryDAO;
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetDAO.findAll();
    }

    @Override
    public Optional<Asset> getAssetById(Integer id) {
        if (id == null)
            return Optional.empty();
        return assetDAO.findById(id);
    }

    @Override
    public List<Asset> searchAssets(String keyword) {
        if (keyword == null)
            return getAllAssets();
        return assetDAO.searchAssets(keyword.trim());
    }

    @Override
    public Asset createAsset(Asset asset, User creator) {
        if (asset == null)
            throw new IllegalArgumentException("Asset cannot be null");
        if (asset.getCategoryId() == null) {
            throw new IllegalArgumentException("Category is required");
        }
        if (asset.getAssetName() == null || asset.getAssetName().isBlank()) {
            throw new IllegalArgumentException("Asset Name is required");
        }

        // Auto-generate Asset Code
        Optional<Category> categoryOpt = categoryDAO.findById(asset.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Category ID");
        }

        String prefix = categoryOpt.get().getPrefixCode();
        int year = LocalDate.now().getYear();
        String prefixYear = prefix + "-" + year;

        String newCode = generateNextCode(prefixYear);
        asset.setAssetCode(newCode);

        // Set default status if null
        if (asset.getStatus() == null) {
            asset.setStatus("New");
        }

        Asset savedAsset = assetDAO.save(asset);

        // Log history
        if (savedAsset.getAssetId() != null) {
            AssetHistory history = new AssetHistory();
            history.setAssetId(savedAsset.getAssetId());
            history.setAction("Created");
            history.setPerformedBy(creator != null ? creator.getUserId() : null);
            history.setDescription("Tài sản được tạo mới với mã: " + newCode);
            history.setActionDate(LocalDateTime.now());
            assetHistoryDAO.save(history);
        }

        return savedAsset;
    }

    private String generateNextCode(String prefixYear) {
        String latestCode = assetDAO.findLatestCodeByPrefix(prefixYear);
        if (latestCode == null) {
            return prefixYear + "-0001";
        }

        try {
            // Code format: PRE-YEAR-XXXX
            String[] parts = latestCode.split("-");
            // Assuming last part is the sequence
            String seqStr = parts[parts.length - 1];
            int seq = Integer.parseInt(seqStr);
            seq++;
            return prefixYear + String.format("-%04d", seq);
        } catch (Exception e) {
            return prefixYear + "-0001";
        }
    }

    @Override
    public boolean updateAsset(Asset asset, User editor) {
        if (asset == null || asset.getAssetId() == null)
            return false;
        if (asset.getAssetName() == null || asset.getAssetName().isBlank()) {
            throw new IllegalArgumentException("Asset Name is required");
        }

        boolean updated = assetDAO.update(asset);
        if (updated) {
            // Log history
            AssetHistory history = new AssetHistory();
            history.setAssetId(asset.getAssetId());
            history.setAction("Updated");
            history.setPerformedBy(editor != null ? editor.getUserId() : null);
            history.setDescription("Cập nhật thông tin tài sản");
            history.setActionDate(LocalDateTime.now());
            assetHistoryDAO.save(history);
        }
        return updated;
    }

    @Override
    public boolean deleteAsset(Integer id) {
        if (id == null)
            return false;
        // Note: Deleting asset might delete history cascade or restrict.
        // For now just delete.
        return assetDAO.delete(id);
    }

    @Override
    public List<AssetHistory> getAssetHistory(int assetId) {
        return assetHistoryDAO.findByAssetId(assetId);
    }

    @Override
    public List<Asset> getAssetsByStatus(String status) {
        if (status == null || status.isBlank()) {
            return getAllAssets();
        }
        return assetDAO.findByStatus(status);
    }
}
