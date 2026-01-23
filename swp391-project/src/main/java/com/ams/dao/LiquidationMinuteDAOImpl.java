package com.ams.dao;

import com.ams.model.LiquidationMinute;
import com.ams.model.User;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LiquidationMinuteDAOImpl implements LiquidationMinuteDAO {

    private static final String SQL_FIND_ALL = """
            SELECT m.*,
                   c.full_name as creator_name, c.user_id as creator_id,
                   a.full_name as approver_name, a.user_id as approver_id
            FROM liquidation_minutes m
            LEFT JOIN users c ON m.created_by = c.user_id
            LEFT JOIN users a ON m.approved_by = a.user_id
            ORDER BY m.created_date DESC
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT m.*,
                   c.full_name as creator_name, c.user_id as creator_id,
                   a.full_name as approver_name, a.user_id as approver_id
            FROM liquidation_minutes m
            LEFT JOIN users c ON m.created_by = c.user_id
            LEFT JOIN users a ON m.approved_by = a.user_id
            WHERE m.minute_id = ?
            """;

    private static final String SQL_FIND_BY_STATUS = """
            SELECT m.*,
                   c.full_name as creator_name, c.user_id as creator_id,
                   a.full_name as approver_name, a.user_id as approver_id
            FROM liquidation_minutes m
            LEFT JOIN users c ON m.created_by = c.user_id
            LEFT JOIN users a ON m.approved_by = a.user_id
            WHERE m.status = ?
            ORDER BY m.created_date DESC
            """;

    private static final String SQL_INSERT = """
            INSERT INTO liquidation_minutes (created_by, note, status)
            VALUES (?, ?, 'Pending')
            """;

    private static final String SQL_UPDATE = """
            UPDATE liquidation_minutes SET note = ? WHERE minute_id = ?
            """;

    private static final String SQL_UPDATE_STATUS = """
            UPDATE liquidation_minutes
            SET status = ?, approved_by = ?, approved_date = CURRENT_TIMESTAMP
            WHERE minute_id = ?
            """;

    @Override
    public List<LiquidationMinute> findAll() {
        List<LiquidationMinute> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<LiquidationMinute> findById(int id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<LiquidationMinute> findByStatus(String status) {
        List<LiquidationMinute> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_STATUS)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public LiquidationMinute save(LiquidationMinute minute) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, minute.getCreatedBy());
            ps.setString(2, minute.getNote());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        minute.setMinuteId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minute;
    }

    @Override
    public boolean update(LiquidationMinute minute) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, minute.getNote());
            ps.setInt(2, minute.getMinuteId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStatus(int minuteId, String status, Integer approvedBy) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {
            ps.setString(1, status);
            if (approvedBy != null) {
                ps.setInt(2, approvedBy);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, minuteId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private LiquidationMinute mapResultSet(ResultSet rs) throws SQLException {
        LiquidationMinute m = new LiquidationMinute();
        m.setMinuteId(rs.getInt("minute_id"));
        m.setCreatedBy(rs.getInt("created_by"));

        Timestamp ts = rs.getTimestamp("created_date");
        if (ts != null)
            m.setCreatedDate(ts.toLocalDateTime());

        int approvedBy = rs.getInt("approved_by");
        if (!rs.wasNull())
            m.setApprovedBy(approvedBy);

        Timestamp ats = rs.getTimestamp("approved_date");
        if (ats != null)
            m.setApprovedDate(ats.toLocalDateTime());

        m.setStatus(rs.getString("status"));
        m.setNote(rs.getString("note"));

        // Creator
        String creatorName = rs.getString("creator_name");
        if (creatorName != null) {
            User creator = new User();
            creator.setUserId(rs.getInt("creator_id"));
            creator.setFullName(creatorName);
            m.setCreator(creator);
        }

        // Approver
        String approverName = rs.getString("approver_name");
        if (approverName != null) {
            User approver = new User();
            approver.setUserId(rs.getInt("approver_id"));
            approver.setFullName(approverName);
            m.setApprover(approver);
        }

        return m;
    }
}
