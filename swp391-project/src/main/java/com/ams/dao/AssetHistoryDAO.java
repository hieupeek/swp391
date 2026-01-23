package com.ams.dao;

import com.ams.model.AssetHistory;
import java.util.List;

public interface AssetHistoryDAO {
    List<AssetHistory> findByAssetId(int assetId);

    void save(AssetHistory history);
}
