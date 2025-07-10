package com;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear usuario");
            System.out.println("2. Leer todos los usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    // Crear usuario
                    System.out.print("Ingrese nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese RUT: ");
                    String rut = scanner.nextLine();
                    System.out.print("Ingrese apodo: ");
                    String apodo = scanner.nextLine();
                    User newUser = new User(0, nombre, apellido, rut, apodo);
                    userDAO.createUser(newUser);
                    System.out.println("Usuario creado con éxito.");
                    break;

                case 2:
                    // Leer todos los usuarios
                    System.out.println("Lista de usuarios:");
                    List<User> users = userDAO.getAllUsers();
                    for (User user : users) {
                        System.out.println("ID: " + user.getId() + ", Nombre: " + user.getNombre() + ", Apellido: " + user.getApellido() + ", RUT: " + user.getRut() + ", Apodo: " + user.getApodo());
                    }
                    break;

                case 3:
                    // Actualizar usuario
                    System.out.print("Ingrese ID del usuario a actualizar: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();  // Consumir la nueva línea
                    System.out.print("Nuevo nombre: ");
                    String newNombre = scanner.nextLine();
                    System.out.print("Nuevo apellido: ");
                    String newApellido = scanner.nextLine();
                    System.out.print("Nuevo RUT: ");
                    String newRut = scanner.nextLine();
                    System.out.print("Nuevo apodo: ");
                    String newApodo = scanner.nextLine();
                    User updatedUser = new User(idToUpdate, newNombre, newApellido, newRut, newApodo);
                    userDAO.updateUser(updatedUser);
                    System.out.println("Usuario actualizado con éxito.");
                    break;

                case 4:
                    // Eliminar usuario
                    System.out.print("Ingrese ID del usuario a eliminar: ");
                    int idToDelete = scanner.nextInt();
                    userDAO.deleteUser(idToDelete);
                    System.out.println("Usuario eliminado con éxito.");
                    break;

                case 5:
                    // Salir
                    System.out.println("Gracias por usar el sistema.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
