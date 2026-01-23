package com.ams.dao;

import com.ams.model.Asset;
import java.util.List;
import java.util.Optional;

public interface AssetDAO {
    List<Asset> findAll();

    Optional<Asset> findById(Integer id);

    Optional<Asset> findByCode(String code);

    List<Asset> searchAssets(String keyword);

    Asset save(Asset asset);

    boolean update(Asset asset);

    boolean delete(Integer id);

    // For code generation
    String findLatestCodeByPrefix(String prefixYear); // e.g., input "LAP-2026", returns "LAP-2026-0005"

    // Status filtering
    List<Asset> findByStatus(String status);

    // Find assets eligible for liquidation (Broken or Maintenance status)
    List<Asset> findEligibleForLiquidation();

    // Update status only
    boolean updateStatus(Integer assetId, String newStatus);
}
