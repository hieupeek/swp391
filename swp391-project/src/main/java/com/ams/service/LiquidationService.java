package com.ams.service;

import com.ams.model.Asset;
import com.ams.model.LiquidationDetail;
import com.ams.model.LiquidationMinute;
import com.ams.model.User;

import java.util.List;
import java.util.Optional;

public interface LiquidationService {

    // Liquidation Minutes CRUD
    List<LiquidationMinute> getAllMinutes();

    Optional<LiquidationMinute> getMinuteById(int id);

    List<LiquidationMinute> getPendingMinutes();

    LiquidationMinute createMinute(String note, List<Integer> assetIds, List<String> reasons, User creator);

    // Approval workflow
    boolean approveMinute(int minuteId, User approver);

    boolean rejectMinute(int minuteId, User approver);

    // Details
    List<LiquidationDetail> getDetailsByMinuteId(int minuteId);

    // Asset status helpers
    List<Asset> getAssetsEligibleForLiquidation();
}
