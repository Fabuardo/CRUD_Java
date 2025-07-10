package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Método para crear un usuario
    public void createUser(User user) {
        String query = "INSERT INTO users (nombre, apellido, rut, apodo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getRut());
            ps.setString(4, user.getApodo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para leer todos los usuarios
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String rut = rs.getString("rut");
                String apodo = rs.getString("apodo");
                users.add(new User(id, nombre, apellido, rut, apodo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Método para actualizar un usuario
    public void updateUser(User user) {
        String query = "UPDATE users SET nombre = ?, apellido = ?, rut = ?, apodo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getRut());
            ps.setString(4, user.getApodo());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un usuario
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
