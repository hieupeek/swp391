package com.ams.service.impl;

import com.ams.service.*;

import com.ams.dao.RoomDAO;
import com.ams.dao.impl.RoomDAOImpl;
import com.ams.model.Room;
import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {

    private final RoomDAO roomDAO;

    public RoomServiceImpl() {
        this.roomDAO = new RoomDAOImpl();
    }

    public RoomServiceImpl(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Integer id) {
        if (id == null)
            return Optional.empty();
        return roomDAO.findById(id);
    }

    @Override
    public List<Room> searchRooms(String keyword) {
        if (keyword == null)
            return getAllRooms();
        return roomDAO.searchByName(keyword.trim());
    }

    @Override
    public Room createRoom(Room room) {
        if (room == null)
            throw new IllegalArgumentException("Room cannot be null");
        if (room.getRoomName() == null || room.getRoomName().isBlank()) {
            throw new IllegalArgumentException("Room name is required");
        }

        // Check duplicate name?
        Optional<Room> existing = roomDAO.findByName(room.getRoomName().trim());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Room name '" + room.getRoomName() + "' already exists");
        }

        room.setRoomName(room.getRoomName().trim());
        return roomDAO.save(room);
    }

    @Override
    public boolean updateRoom(Room room) {
        if (room == null || room.getRoomId() == null)
            return false;
        if (room.getRoomName() == null || room.getRoomName().isBlank()) {
            throw new IllegalArgumentException("Room name is required");
        }

        Optional<Room> existing = roomDAO.findById(room.getRoomId());
        if (existing.isEmpty())
            return false;

        // Check duplicate if name changed
        if (!existing.get().getRoomName().equalsIgnoreCase(room.getRoomName().trim())) {
            Optional<Room> duplicate = roomDAO.findByName(room.getRoomName().trim());
            if (duplicate.isPresent()) {
                throw new IllegalArgumentException("Room name '" + room.getRoomName() + "' already exists");
            }
        }

        room.setRoomName(room.getRoomName().trim());
        return roomDAO.update(room);
    }

    @Override
    public boolean deleteRoom(Integer id) {
        if (id == null)
            return false;
        return roomDAO.delete(id);
    }
}
