package com.ams.service;

import com.ams.model.Room;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllRooms();

    Optional<Room> getRoomById(Integer id);

    List<Room> searchRooms(String keyword);

    Room createRoom(Room room);

    boolean updateRoom(Room room);

    boolean deleteRoom(Integer id);
}
