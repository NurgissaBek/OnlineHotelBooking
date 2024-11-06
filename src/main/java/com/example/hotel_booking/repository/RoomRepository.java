package com.example.hotel_booking.repository;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.rooms.RoomFactory;
import com.example.hotel_booking.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private Connection connection = DatabaseConnectionManager.getInstance().getConnection();

    public List<Room> findAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms_table";
        RoomFactory roomFactory = new RoomFactory();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Room room = roomFactory.createRoom(rs.getString("room_type"));
                if (room != null) {
                    room.setRoomId(rs.getInt("room_id"));
                    room.setPricePerNight(rs.getDouble("price_per_night"));
                    room.setAvailable(rs.getBoolean("is_available"));
                    room.setCapacity(rs.getInt("capacity"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void updateRoomAvailability(int roomId, boolean availability) {
        String query = "UPDATE rooms_table SET is_available = ? WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, availability);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
