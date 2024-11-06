package com.example.hotel_booking.repository;

import com.example.hotel_booking.models.User;
import com.example.hotel_booking.util.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final Connection connection;

    public UserRepository() {
        this.connection = DatabaseConnectionManager.getInstance().getConnection();
    }

    // Add a new user to the database
    public void addUser(User user) {
        String query = "INSERT INTO users_table (username, password_hash, email, phone_number, created_at, role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
            pstmt.setString(6, user.getRole());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding user to database", e);
        }
    }

    // Get a user by username
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users_table WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users_table";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Helper method to map a ResultSet row to a User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("password_hash");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone_number");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        String role = rs.getString("role");

        return new User(userId, username, passwordHash, email, phoneNumber, createdAt, role);
    }
}
