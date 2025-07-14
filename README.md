# CRUD_Java
Este proyecto es una aplicación Java que implementa operaciones CRUD usando JDBC, MySQL y pruebas unitarias con JUnit 5, H2 y cobertura de código con JaCoCo.

---

## ⚙️ Requisitos

- Java 17 (JDK)
- Maven 3.6+ (usando Java 17)
- MySQL (solo en producción)
- IntelliJ IDEA (recomendado)

---

## 🚀 Configuración del proyecto

### 1. Clona el repositorio

git clone https://github.com/Fabuardo/CRUD_Java.git
cd UserCRUDApp

##  Ejecutar pruebas unitarias
Usa Maven para correr los tests:

mvn clean test
Esto ejecutará todos los tests definidos en UserDAOTest.java usando H2 como base de datos en memoria.


##  Generar reporte jococo
Después de ejecutar los tests, genera el informe HTML con:

mvn jacoco:report


##  Ver reporte
Abre el siguiente archivo en tu navegador:

target/site/jacoco/index.html
Verás un desglose de la cobertura de clases, líneas y métodos.


##  BD MySql
Para conexión real con MySQL, configura el archivo DatabaseConnection.java con tus credenciales y asegúrate de tener creada la tabla users.



