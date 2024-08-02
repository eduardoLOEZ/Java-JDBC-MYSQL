package org.example.course.main;

import com.mysql.cj.protocol.Resultset;
import org.example.util.DataBaseConnection;

import java.sql.*;

public class Main {




    public void run() throws SQLException{
        // Definir la consulta y los parámetros
        String sqlQuery = "SELECT * FROM employees WHERE age > ?";
        int parameter = 25;

        // Definir la consulta de inserción y los parámetros
        String sqlInsert = "INSERT INTO employees (name, age, email) VALUES (?, ?, ?)";
        String name = "juan";
        int age = 23;
        String email = "juan@gmail.com";

        // Definir la consulta de actualización y los parámetros
        String sqlUpdate = "UPDATE employees SET age = ?, email = ? WHERE name = ?";
        int newAge = 30;
        String newEmail = "juan@example.com";
        String targetName = "juan";

        // Definir la consulta de eliminación y el parámetro
        String sqlDelete = "DELETE FROM employees WHERE id = ?";
        int idToDelete = 1;

        try(
                // Establecer la conexión a la base de datos con Singleton pattern:
                Connection myConn = DataBaseConnection.getInstance();
                // Crear un Statement para el SELECT
                Statement myStateS = myConn.createStatement();
                // Crear un PreparedStatement para el INSERT
                PreparedStatement myStmtInsert = myConn.prepareStatement(sqlInsert);
                // Crear un PreparedStatement para el UPDATE
                PreparedStatement myStmtUpdate = myConn.prepareStatement(sqlUpdate);
                // Crear un PreparedStatement para el DELETE
                PreparedStatement myStmtDelete = myConn.prepareStatement(sqlDelete);
        ) {

            System.out.println("DB connected");

            /*

            CREATE TABLE employees (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                age INT NOT NULL,
                email VARCHAR(100) NOT NULL
                );
             */


            String sqlCreate = "INSERT INTO employees (name, age, email) VALUES (?, ?, ?)";

            //va ser prepareStatement porque lleva parametros esta query
           /*

           myStmt = myConn.prepareStatement(); //creamos el statement para poder ejecutar la consulta

            //colocamos los valores
            myStmt.setString(1, "juan");
            myStmt.setInt(2, 23);
            myStmt.setString(3, "juan@gmail.com");

            //las rows que van a ser afectadas, o sea las que se van a insertar registros nuevos.
            int rowsAffected = myStmt.executeUpdate(); //ejecuta la query



            if(rowsAffected > 0){

                System.out.println("se ha creado el empleado en la DB");
            }

            */


            //UPDATE AN EMPLOYEE
            //WHERE ES EL INDEX, EL CAMPO EN EL QUE SE HACE LA BUSQUEDA
            //String updateSql = "UPDATE employees SET age = ?, email = ? WHERE name = ?";
            /*

            myStmt = myConn.prepareStatement();

            myStmt.setInt(1, 25);
            myStmt.setString(2, "jane@gmail.com");
            myStmt.setString(3, "Jane Doe");
             */

            //Ejecutar la actualización

            myStmtUpdate.setInt(1, newAge);
            myStmtUpdate.setString(2, newEmail);
            myStmtUpdate.setString(3, targetName);

            int rowsAffected = myStmtUpdate.executeUpdate();
            System.out.println("Rows Affected: " + rowsAffected);




            //ELIMINAR UN REGISTRO

            /*
            String sqlDelete = "DELETE FROM employees WHERE ID = ?";
            myStmt = myConn.prepareStatement(sqlDelete);

            myStmt.setInt(1,5);

            int rowsAffectedDelete = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffectedDelete);


             */



            //COLOCAMOS DESPUES EL SELECT PARA PODER VER LOS CAMBIOS DEL UPDATE
            //un select from
            //DEBEMOS DE CREAR EL STATEMENT ANTES DE HACER LA QUERY, DEBEMOS INICIALIZARLO, PARA PODER USARLO.
            // Ejecutar la consulta SELECT

            // Ejecutar la consulta SELECT
            try (ResultSet myRes = myStateS.executeQuery("SELECT * FROM employees")) {
                while (myRes.next()) {
                    int id = myRes.getInt("id");
                    String empName = myRes.getString("name");
                    int empAge = myRes.getInt("age");
                    String empEmail = myRes.getString("email");

                    System.out.println("ID: " + id + ", Name: " + empName + ", Age: " + empAge + ", Email: " + empEmail);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error while connecting to the DB");
        }

    }


    public static void main(String[] args) throws SQLException {

        Main app = new Main();

        try{
            app.run();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
