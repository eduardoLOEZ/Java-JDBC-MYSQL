package org.example.course.main;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //definimos los myConn para hacer la conexión con el DriverManager
        //el statement con preparedStatement para las consultas complejas con parametros
        //y myRes para mostrar la respuesta de un SELCT
        Connection myConn = null;
        PreparedStatement myStmt = null;
        Statement myStateS = null;
        ResultSet myRes = null; // Usar java.sql.ResultSet

        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "123456";

        try {
            myConn = DriverManager.getConnection(url, user, password);
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
            String updateSql = "UPDATE employees SET age = ?, email = ? WHERE name = ?";
            /*

            myStmt = myConn.prepareStatement();

            myStmt.setInt(1, 25);
            myStmt.setString(2, "jane@gmail.com");
            myStmt.setString(3, "Jane Doe");
             */

            /*Ejecutar la actualización
            int rowsAffected = myStmt.executeUpdate();
            System.out.println("Rows Affected: " + rowsAffected);


             */

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
            myStateS = myConn.createStatement();
            myRes = myStateS.executeQuery("SELECT * FROM employees");

            while(myRes.next()){

                int id = myRes.getInt("id");
                String name = myRes.getString("name");
                int age = myRes.getInt("age");
                String email = myRes.getString("email");
                //System.out.println(myRes.getString("name"));
                //solo va hacer get de la columna "name"

                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Email: " + email);



            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error while connecting to the DB");
        }
    }
}
