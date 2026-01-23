package com.ams.dao;

import com.ams.model.Room;
import java.util.List;
import java.util.Optional;

public interface RoomDAO {
    List<Room> findAll();

    Optional<Room> findById(Integer id);

    Optional<Room> findByName(String name);

    List<Room> findByDepartment(Integer deptId);

    List<Room> searchByName(String keyword);

    Room save(Room room);

    boolean update(Room room);

    boolean delete(Integer id);
}
