package com;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO dao;

    @BeforeEach
    void init() throws SQLException {
        dao = new UserDAO();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("""
                CREATE TABLE users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(255) NOT NULL,
                    apellido VARCHAR(255) NOT NULL,
                    rut VARCHAR(255) UNIQUE NOT NULL,
                    apodo VARCHAR(255)
                )
            """);
        }
    }

    @Test
    void createUser_shouldThrowException_whenNombreIsNull() {
        User user = new User(0, null, "López", "11111111-1", "lolo");
        assertThrows(IllegalArgumentException.class, () -> dao.createUser(user));
    }

    @Test
    void createUser_shouldThrowException_whenRutAlreadyExists() {
        User u1 = new User(0, "Ana", "López", "12345678-9", "ana");
        User u2 = new User(0, "Beto", "Pérez", "12345678-9", "beto");
        dao.createUser(u1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> dao.createUser(u2));
        assertTrue(ex.getMessage().contains("RUT ya"));
    }

    @Test
    void createUser_shouldInsertCorrectly_whenValidUser() {
        User user = new User(0, "Carlos", "Reyes", "22222222-2", "carl");
        dao.createUser(user);
        List<User> users = dao.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("Carlos", users.get(0).getNombre());
    }


    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
        List<User> users = dao.getAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    void getAllUsers_shouldReturnAllUsers_whenUsersExist() {
        dao.createUser(new User(0, "Eva", "Ruiz", "44444444-4", "eva"));
        dao.createUser(new User(0, "Fran", "Zamora", "55555555-5", "fran"));
        List<User> users = dao.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void getAllUsers_shouldPreserveUserFieldsCorrectly() {
        dao.createUser(new User(0, "Gina", "Solís", "66666666-6", "gigi"));
        User user = dao.getAllUsers().get(0);
        assertEquals("Gina", user.getNombre());
        assertEquals("Solís", user.getApellido());
    }


    @Test
    void updateUser_shouldModifyFieldsCorrectly() {
        dao.createUser(new User(0, "Hugo", "Diaz", "77777777-7", "hugo"));
        User u = dao.getAllUsers().get(0);
        u.setNombre("Héctor");
        dao.updateUser(u);
        User updated = dao.getAllUsers().get(0);
        assertEquals("Héctor", updated.getNombre());
    }

    @Test
    void updateUser_shouldThrow_whenNombreIsBlank() {
        dao.createUser(new User(0, "Isa", "Ramírez", "88888888-8", "isa"));
        User u = dao.getAllUsers().get(0);
        u.setNombre("");
        assertThrows(IllegalArgumentException.class, () -> dao.updateUser(u));
    }

    @Test
    void updateUser_shouldFail_whenRutDuplicated() {
        dao.createUser(new User(0, "Jorge", "Peña", "99999999-9", "jo"));
        dao.createUser(new User(0, "Karla", "Núñez", "00000000-0", "ka"));
        User karla = dao.getAllUsers().stream().filter(u -> u.getNombre().equals("Karla")).findFirst().get();
        karla.setRut("99999999-9");
        assertThrows(IllegalArgumentException.class, () -> dao.updateUser(karla));
    }


    @Test
    void deleteUser_shouldRemoveUserCorrectly() {
        dao.createUser(new User(0, "Leo", "Torres", "11111111-2", "leo"));
        int id = dao.getAllUsers().get(0).getId();
        dao.deleteUser(id);
        assertTrue(dao.getAllUsers().isEmpty());
    }

    @Test
    void deleteUser_shouldNotThrow_whenIdNotExists() {
        assertDoesNotThrow(() -> dao.deleteUser(999));
    }

    @Test
    void deleteUser_shouldNotAffectOthers_whenDeletingOne() {
        dao.createUser(new User(0, "Mía", "Bustos", "22222222-3", "mia"));
        dao.createUser(new User(0, "Noah", "Ortega", "33333333-4", "noa"));
        int id = dao.getAllUsers().stream().filter(u -> u.getNombre().equals("Mía")).findFirst().get().getId();
        dao.deleteUser(id);
        assertEquals(1, dao.getAllUsers().size());
    }
}
