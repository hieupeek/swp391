package com.ams.dao;

import com.ams.model.Asset;
import com.ams.model.LiquidationDetail;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LiquidationDetailDAOImpl implements LiquidationDetailDAO {

    private static final String SQL_FIND_BY_MINUTE = """
            SELECT d.*, a.asset_id, a.asset_code, a.asset_name, a.status as asset_status
            FROM liquidation_details d
            JOIN assets a ON d.asset_id = a.asset_id
            WHERE d.minute_id = ?
            """;

    private static final String SQL_INSERT = """
            INSERT INTO liquidation_details (minute_id, asset_id, reason, salvage_value)
            VALUES (?, ?, ?, ?)
            """;

    private static final String SQL_DELETE_BY_MINUTE = """
            DELETE FROM liquidation_details WHERE minute_id = ?
            """;

    @Override
    public List<LiquidationDetail> findByMinuteId(int minuteId) {
        List<LiquidationDetail> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_MINUTE)) {
            ps.setInt(1, minuteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LiquidationDetail d = new LiquidationDetail();
                    d.setDetailId(rs.getInt("detail_id"));
                    d.setMinuteId(rs.getInt("minute_id"));
                    d.setAssetId(rs.getInt("asset_id"));
                    d.setReason(rs.getString("reason"));
                    d.setSalvageValue(rs.getBigDecimal("salvage_value"));

                    // Asset info
                    Asset asset = new Asset();
                    asset.setAssetId(rs.getInt("asset_id"));
                    asset.setAssetCode(rs.getString("asset_code"));
                    asset.setAssetName(rs.getString("asset_name"));
                    asset.setStatus(rs.getString("asset_status"));
                    d.setAsset(asset);

                    list.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(LiquidationDetail detail) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, detail.getMinuteId());
            ps.setInt(2, detail.getAssetId());
            ps.setString(3, detail.getReason());
            ps.setBigDecimal(4,
                    detail.getSalvageValue() != null ? detail.getSalvageValue() : java.math.BigDecimal.ZERO);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByMinuteId(int minuteId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_MINUTE)) {
            ps.setInt(1, minuteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
