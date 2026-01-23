package com.ams.service;

import com.ams.model.Asset;
import com.ams.model.AssetHistory;
import com.ams.model.User;

import java.util.List;
import java.util.Optional;

public interface AssetService {
    List<Asset> getAllAssets();

    Optional<Asset> getAssetById(Integer id);

    List<Asset> searchAssets(String keyword);

    Asset createAsset(Asset asset, User creator); // Will auto-generate code

    boolean updateAsset(Asset asset, User editor);

    boolean deleteAsset(Integer id);

    List<AssetHistory> getAssetHistory(int assetId);

    List<Asset> getAssetsByStatus(String status);
}
