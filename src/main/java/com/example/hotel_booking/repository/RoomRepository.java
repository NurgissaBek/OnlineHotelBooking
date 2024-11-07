package com.example.hotel_booking.repository;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.rooms.RoomFactory;
import com.example.hotel_booking.util.DatabaseConnectionManager;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
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
                    room.setRoomId(rs.getInt("rooms_id"));
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

    public void save(Room room) {
        String query = "INSERT INTO rooms_table (room_type, price_per_night, is_available, capacity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, room.getRoomType());
            pstmt.setDouble(2, room.getPricePerNight());
            pstmt.setBoolean(3, room.isAvailable());
            pstmt.setInt(4, room.getCapacity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving room to database", e);
        }
    }

    public boolean deleteRoomById(int roomId) {
        String query = "DELETE FROM rooms_table WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, roomId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting room from database", e);
        }
    }

}
