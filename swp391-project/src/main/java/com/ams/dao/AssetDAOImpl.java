package com.ams.dao;

import com.ams.model.Asset;
import com.ams.model.Category;
import com.ams.model.Room;
import com.ams.model.Supplier;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssetDAOImpl implements AssetDAO {

    private static final String SQL_FIND_ALL = """
            SELECT a.*,
                   c.category_name, c.prefix_code,
                   s.supplier_name,
                   r.room_name
            FROM assets a
            LEFT JOIN categories c ON a.category_id = c.category_id
            LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
            LEFT JOIN rooms r ON a.room_id = r.room_id
            ORDER BY a.created_at DESC
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT a.*,
                   c.category_name, c.prefix_code,
                   s.supplier_name,
                   r.room_name
            FROM assets a
            LEFT JOIN categories c ON a.category_id = c.category_id
            LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
            LEFT JOIN rooms r ON a.room_id = r.room_id
            WHERE a.asset_id = ?
            """;

    private static final String SQL_FIND_BY_CODE = """
            SELECT a.*,
                   c.category_name, c.prefix_code,
                   s.supplier_name,
                   r.room_name
            FROM assets a
            LEFT JOIN categories c ON a.category_id = c.category_id
            LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
            LEFT JOIN rooms r ON a.room_id = r.room_id
            WHERE a.asset_code = ?
            """;

    // Simple search by name or code
    private static final String SQL_SEARCH = """
            SELECT a.*,
                   c.category_name, c.prefix_code,
                   s.supplier_name,
                   r.room_name
            FROM assets a
            LEFT JOIN categories c ON a.category_id = c.category_id
            LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
            LEFT JOIN rooms r ON a.room_id = r.room_id
            WHERE a.asset_name LIKE ? OR a.asset_code LIKE ?
            ORDER BY a.created_at DESC
            """;

    private static final String SQL_INSERT = """
            INSERT INTO assets (asset_code, asset_name, category_id, supplier_id, room_id,
                                price, purchase_date, warranty_expiry_date, status, description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE assets SET asset_name = ?, category_id = ?, supplier_id = ?, room_id = ?,
                              price = ?, purchase_date = ?, warranty_expiry_date = ?, status = ?, description = ?
            WHERE asset_id = ?
            """;

    private static final String SQL_DELETE = "DELETE FROM assets WHERE asset_id = ?";

    private static final String SQL_LATEST_CODE = "SELECT asset_code FROM assets WHERE asset_code LIKE ? ORDER BY asset_code DESC LIMIT 1";

    @Override
    public List<Asset> findAll() {
        List<Asset> assets = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public Optional<Asset> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAsset(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Asset> findByCode(String code) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CODE)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAsset(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Asset> searchAssets(String keyword) {
        List<Asset> assets = new ArrayList<>();
        String pattern = "%" + keyword + "%";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_SEARCH)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assets.add(mapResultSetToAsset(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public Asset save(Asset asset) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, asset.getAssetCode());
            ps.setString(2, asset.getAssetName());
            ps.setInt(3, asset.getCategoryId());

            setNullableInt(ps, 4, asset.getSupplierId());
            setNullableInt(ps, 5, asset.getRoomId());

            if (asset.getPrice() != null) {
                ps.setBigDecimal(6, asset.getPrice());
            } else {
                ps.setBigDecimal(6, java.math.BigDecimal.ZERO);
            }

            ps.setDate(7, asset.getPurchaseDate() != null ? Date.valueOf(asset.getPurchaseDate()) : null);
            ps.setDate(8, asset.getWarrantyExpiryDate() != null ? Date.valueOf(asset.getWarrantyExpiryDate()) : null);

            ps.setString(9, asset.getStatus() != null ? asset.getStatus() : "New");
            ps.setString(10, asset.getDescription());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        asset.setAssetId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asset;
    }

    @Override
    public boolean update(Asset asset) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, asset.getAssetName());
            ps.setInt(2, asset.getCategoryId());
            setNullableInt(ps, 3, asset.getSupplierId());
            setNullableInt(ps, 4, asset.getRoomId());
            if (asset.getPrice() != null) {
                ps.setBigDecimal(5, asset.getPrice());
            } else {
                ps.setBigDecimal(5, java.math.BigDecimal.ZERO);
            }
            ps.setDate(6, asset.getPurchaseDate() != null ? Date.valueOf(asset.getPurchaseDate()) : null);
            ps.setDate(7, asset.getWarrantyExpiryDate() != null ? Date.valueOf(asset.getWarrantyExpiryDate()) : null);
            ps.setString(8, asset.getStatus());
            ps.setString(9, asset.getDescription());
            ps.setInt(10, asset.getAssetId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String findLatestCodeByPrefix(String prefixYear) {
        // prefixYear e.g., "LAP-2026"
        String pattern = prefixYear + "-%";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_LATEST_CODE)) {
            ps.setString(1, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("asset_code");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found
    }

    private void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(index, value);
        } else {
            ps.setNull(index, Types.INTEGER);
        }
    }

    private Asset mapResultSetToAsset(ResultSet rs) throws SQLException {
        Asset asset = new Asset();
        asset.setAssetId(rs.getInt("asset_id"));
        asset.setAssetCode(rs.getString("asset_code"));
        asset.setAssetName(rs.getString("asset_name"));
        asset.setCategoryId(rs.getInt("category_id"));

        int supId = rs.getInt("supplier_id");
        if (!rs.wasNull())
            asset.setSupplierId(supId);

        int roomId = rs.getInt("room_id");
        if (!rs.wasNull())
            asset.setRoomId(roomId);

        asset.setPrice(rs.getBigDecimal("price"));

        Date purDate = rs.getDate("purchase_date");
        if (purDate != null)
            asset.setPurchaseDate(purDate.toLocalDate());

        Date warDate = rs.getDate("warranty_expiry_date");
        if (warDate != null)
            asset.setWarrantyExpiryDate(warDate.toLocalDate());

        asset.setStatus(rs.getString("status"));
        asset.setDescription(rs.getString("description"));

        // Joined data
        try {
            Category cat = new Category();
            cat.setCategoryId(rs.getInt("category_id"));
            cat.setCategoryName(rs.getString("category_name"));
            cat.setPrefixCode(rs.getString("prefix_code"));
            asset.setCategory(cat);

            String supName = rs.getString("supplier_name");
            if (supName != null) {
                Supplier s = new Supplier();
                s.setSupplierId(asset.getSupplierId());
                s.setSupplierName(supName);
                asset.setSupplier(s);
            }

            String roomName = rs.getString("room_name");
            if (roomName != null) {
                Room r = new Room();
                r.setRoomId(asset.getRoomId());
                r.setRoomName(roomName);
                asset.setRoom(r);
            }
        } catch (SQLException e) {
            // Ignore missing columns
        }

        return asset;
    }

    @Override
    public List<Asset> findByStatus(String status) {
        List<Asset> assets = new ArrayList<>();
        String sql = """
                SELECT a.*,
                       c.category_name, c.prefix_code,
                       s.supplier_name,
                       r.room_name
                FROM assets a
                LEFT JOIN categories c ON a.category_id = c.category_id
                LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
                LEFT JOIN rooms r ON a.room_id = r.room_id
                WHERE a.status = ?
                ORDER BY a.created_at DESC
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assets.add(mapResultSetToAsset(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public List<Asset> findEligibleForLiquidation() {
        List<Asset> assets = new ArrayList<>();
        String sql = """
                SELECT a.*,
                       c.category_name, c.prefix_code,
                       s.supplier_name,
                       r.room_name
                FROM assets a
                LEFT JOIN categories c ON a.category_id = c.category_id
                LEFT JOIN suppliers s ON a.supplier_id = s.supplier_id
                LEFT JOIN rooms r ON a.room_id = r.room_id
                WHERE a.status IN ('Broken', 'Maintenance', 'Lost')
                  AND a.asset_id NOT IN (SELECT asset_id FROM liquidation_details)
                ORDER BY a.created_at DESC
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public boolean updateStatus(Integer assetId, String newStatus) {
        String sql = "UPDATE assets SET status = ? WHERE asset_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, assetId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
