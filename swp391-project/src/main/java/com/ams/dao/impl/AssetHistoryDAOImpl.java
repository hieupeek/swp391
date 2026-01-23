package com.ams.dao.impl;

import com.ams.dao.*;

import com.ams.model.AssetHistory;
import com.ams.model.User;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetHistoryDAOImpl implements AssetHistoryDAO {

    private static final String SQL_FIND_BY_ASSET_ID = """
            SELECT h.*, u.full_name, u.user_id as u_id
            FROM asset_history h
            LEFT JOIN users u ON h.performed_by = u.user_id
            WHERE h.asset_id = ?
            ORDER BY h.action_date DESC
            """;

    private static final String SQL_INSERT = """
            INSERT INTO asset_history (asset_id, action, performed_by, description, action_date)
            VALUES (?, ?, ?, ?, ?)
            """;

    @Override
    public List<AssetHistory> findByAssetId(int assetId) {
        List<AssetHistory> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ASSET_ID)) {
            ps.setInt(1, assetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AssetHistory history = new AssetHistory();
                    history.setHistoryId(rs.getInt("history_id"));
                    history.setAssetId(rs.getInt("asset_id"));
                    history.setAction(rs.getString("action"));

                    int performedBy = rs.getInt("performed_by");
                    if (!rs.wasNull()) {
                        history.setPerformedBy(performedBy);
                    }

                    history.setDescription(rs.getString("description"));
                    Timestamp ts = rs.getTimestamp("action_date");
                    if (ts != null) {
                        history.setActionDate(ts.toLocalDateTime());
                    }

                    // Map user
                    String fullName = rs.getString("full_name");
                    if (fullName != null) {
                        User u = new User();
                        u.setUserId(rs.getInt("u_id"));
                        u.setFullName(fullName);
                        history.setPerformer(u);
                    }

                    list.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(AssetHistory history) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, history.getAssetId());
            ps.setString(2, history.getAction());

            if (history.getPerformedBy() != null) {
                ps.setInt(3, history.getPerformedBy());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, history.getDescription());

            if (history.getActionDate() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(history.getActionDate()));
            } else {
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
