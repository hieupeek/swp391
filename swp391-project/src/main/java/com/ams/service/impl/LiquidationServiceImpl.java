package com.ams.service.impl;

import com.ams.service.*;

import com.ams.dao.*;
import com.ams.dao.impl.*;
import com.ams.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LiquidationServiceImpl implements LiquidationService {

    private final LiquidationMinuteDAO minuteDAO;
    private final LiquidationDetailDAO detailDAO;
    private final AssetDAO assetDAO;
    private final AssetHistoryDAO historyDAO;

    public LiquidationServiceImpl() {
        this.minuteDAO = new LiquidationMinuteDAOImpl();
        this.detailDAO = new LiquidationDetailDAOImpl();
        this.assetDAO = new AssetDAOImpl();
        this.historyDAO = new AssetHistoryDAOImpl();
    }

    @Override
    public List<LiquidationMinute> getAllMinutes() {
        return minuteDAO.findAll();
    }

    @Override
    public Optional<LiquidationMinute> getMinuteById(int id) {
        return minuteDAO.findById(id);
    }

    @Override
    public List<LiquidationMinute> getPendingMinutes() {
        return minuteDAO.findByStatus("Pending");
    }

    @Override
    public LiquidationMinute createMinute(String note, List<Integer> assetIds, List<String> reasons, User creator) {
        if (assetIds == null || assetIds.isEmpty()) {
            throw new IllegalArgumentException("Phải chọn ít nhất một tài sản để thanh lý");
        }

        // Create master record
        LiquidationMinute minute = new LiquidationMinute();
        minute.setCreatedBy(creator.getUserId());
        minute.setNote(note);
        minute.setStatus("Pending");
        minute.setCreatedDate(LocalDateTime.now());

        LiquidationMinute saved = minuteDAO.save(minute);

        // Create detail records
        for (int i = 0; i < assetIds.size(); i++) {
            LiquidationDetail detail = new LiquidationDetail();
            detail.setMinuteId(saved.getMinuteId());
            detail.setAssetId(assetIds.get(i));
            detail.setReason(reasons != null && i < reasons.size() ? reasons.get(i) : "");
            detail.setSalvageValue(BigDecimal.ZERO);
            detailDAO.save(detail);

            // Log history
            AssetHistory history = new AssetHistory();
            history.setAssetId(assetIds.get(i));
            history.setAction("Liquidation_Requested");
            history.setPerformedBy(creator.getUserId());
            history.setDescription("Thêm vào biên bản thanh lý #" + saved.getMinuteId());
            history.setActionDate(LocalDateTime.now());
            historyDAO.save(history);
        }

        return saved;
    }

    @Override
    public boolean approveMinute(int minuteId, User approver) {
        Optional<LiquidationMinute> opt = minuteDAO.findById(minuteId);
        if (opt.isEmpty())
            return false;

        LiquidationMinute minute = opt.get();
        if (!"Pending".equals(minute.getStatus())) {
            return false; // Already processed
        }

        // Update minute status
        boolean updated = minuteDAO.updateStatus(minuteId, "Approved", approver.getUserId());
        if (!updated)
            return false;

        // Update all assets to "Liquidated"
        List<LiquidationDetail> details = detailDAO.findByMinuteId(minuteId);
        for (LiquidationDetail detail : details) {
            assetDAO.updateStatus(detail.getAssetId(), "Liquidated");

            // Log history
            AssetHistory history = new AssetHistory();
            history.setAssetId(detail.getAssetId());
            history.setAction("Liquidated");
            history.setPerformedBy(approver.getUserId());
            history.setDescription("Thanh lý được duyệt (Biên bản #" + minuteId + ")");
            history.setActionDate(LocalDateTime.now());
            historyDAO.save(history);
        }

        return true;
    }

    @Override
    public boolean rejectMinute(int minuteId, User approver) {
        Optional<LiquidationMinute> opt = minuteDAO.findById(minuteId);
        if (opt.isEmpty())
            return false;

        LiquidationMinute minute = opt.get();
        if (!"Pending".equals(minute.getStatus())) {
            return false;
        }

        return minuteDAO.updateStatus(minuteId, "Rejected", approver.getUserId());
    }

    @Override
    public List<LiquidationDetail> getDetailsByMinuteId(int minuteId) {
        return detailDAO.findByMinuteId(minuteId);
    }

    @Override
    public List<Asset> getAssetsEligibleForLiquidation() {
        return assetDAO.findEligibleForLiquidation();
    }
}
