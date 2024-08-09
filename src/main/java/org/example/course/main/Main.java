package org.example.course.main;

import com.mysql.cj.protocol.Resultset;
import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DataBaseConnection;

import java.sql.*;
import java.util.List;

public class Main {


    public void run() throws SQLException{


        try(
                // Establecer la conexión a la base de datos con Singleton pattern:
                Connection myConn = DataBaseConnection.getInstance();) {
            Employee newEmployee = new Employee(null, "Alice Johnson", 28, "alice.johnson@example.com");
            Repository<Employee> repository = new EmployeeRepository(myConn);


            //mostrar la tabla de employees
            System.out.println("------Lista de employees-------");
                repository.findAll().forEach(System.out::println);

            //System.out.println("------Registrando un employee-------");
            //repository.save(newEmployee);


            //System.out.println("-------------Nuevo Empleado insertado--------------");
            //repository.findAll().forEach(System.out::println);


            Employee updatedEmployee = new Employee(10, "Alice Johnson", 30, "alice.example@updated.com");
            //System.out.println("-------------Empleado Actualizado--------------");
            //repository.update(6,updatedEmployee);

            repository.findAll().forEach(System.out::println);

            System.out.println("------ Eliminando el employee con ID 6 -------");
            //repository.delete(8);
            repository.findAll().forEach(System.out::println);


            System.out.println("getting user by id");
            System.out.println( repository.getById(1));

            int pageNumber = 1;
            int pageSize = 2;

            List<Employee> employeeListPage1 = repository.findEmployeesPagination(pageNumber, pageSize);
            System.out.println("-----Page: "+ pageNumber+ "----------");
            employeeListPage1.forEach(System.out::println);



            System.out.println("DB connected");

            /*

            CREATE TABLE employees (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                age INT NOT NULL,
                email VARCHAR(100) NOT NULL
                );
             */





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
