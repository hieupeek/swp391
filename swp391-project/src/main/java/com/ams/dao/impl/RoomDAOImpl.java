package com.ams.dao.impl;

import com.ams.dao.*;

import com.ams.model.Department;
import com.ams.model.Room;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDAOImpl implements RoomDAO {

    private static final String SQL_FIND_ALL = """
            SELECT r.room_id, r.room_name, r.dept_id, r.capacity,
                   d.dept_name, d.description AS dept_desc
            FROM rooms r
            LEFT JOIN departments d ON r.dept_id = d.dept_id
            ORDER BY r.room_name
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT r.room_id, r.room_name, r.dept_id, r.capacity,
                   d.dept_name, d.description AS dept_desc
            FROM rooms r
            LEFT JOIN departments d ON r.dept_id = d.dept_id
            WHERE r.room_id = ?
            """;

    private static final String SQL_FIND_BY_NAME = "SELECT * FROM rooms WHERE room_name = ?";

    private static final String SQL_FIND_BY_DEPT = """
            SELECT r.room_id, r.room_name, r.dept_id, r.capacity,
                   d.dept_name, d.description AS dept_desc
            FROM rooms r
            LEFT JOIN departments d ON r.dept_id = d.dept_id
            WHERE r.dept_id = ?
            ORDER BY r.room_name
            """;

    private static final String SQL_SEARCH = """
             SELECT r.room_id, r.room_name, r.dept_id, r.capacity,
                   d.dept_name, d.description AS dept_desc
            FROM rooms r
            LEFT JOIN departments d ON r.dept_id = d.dept_id
            WHERE r.room_name LIKE ?
            ORDER BY r.room_name
            """;

    private static final String SQL_INSERT = "INSERT INTO rooms (room_name, dept_id, capacity) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE rooms SET room_name = ?, dept_id = ?, capacity = ? WHERE room_id = ?";
    private static final String SQL_DELETE = "DELETE FROM rooms WHERE room_id = ?";

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Optional<Room> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRoom(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Room> findByName(String name) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // For simple check, we don't need dept details, or we can fetch if needed.
                    // But SQL_FIND_BY_NAME above is simple SELECT *
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomName(rs.getString("room_name"));
                    room.setDeptId(rs.getInt("dept_id"));
                    room.setCapacity(rs.getInt("capacity"));
                    return Optional.of(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findByDepartment(Integer deptId) {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_DEPT)) {
            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapResultSetToRoom(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<Room> searchByName(String keyword) {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_SEARCH)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapResultSetToRoom(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room save(Room room) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getRoomName());
            if (room.getDeptId() != null) {
                ps.setInt(2, room.getDeptId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            if (room.getCapacity() != null) {
                ps.setInt(3, room.getCapacity());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        room.setRoomId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    @Override
    public boolean update(Room room) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, room.getRoomName());
            if (room.getDeptId() != null) {
                ps.setInt(2, room.getDeptId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            if (room.getCapacity() != null) {
                ps.setInt(3, room.getCapacity());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, room.getRoomId());

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

    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomName(rs.getString("room_name"));
        room.setDeptId(rs.getInt("dept_id"));
        if (rs.wasNull()) {
            room.setDeptId(null);
        }
        room.setCapacity(rs.getInt("capacity"));

        // Map Department info if available (from JOIN)
        try {
            int deptId = rs.getInt("dept_id"); // re-read but it's fine
            String deptName = rs.getString("dept_name");
            if (deptName != null) {
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setDeptName(deptName);
                dept.setDescription(rs.getString("dept_desc"));
                room.setDepartment(dept);
            }
        } catch (SQLException e) {
            // Column might not exist if we used simple query, but here we used JOIN
        }

        return room;
    }
}
