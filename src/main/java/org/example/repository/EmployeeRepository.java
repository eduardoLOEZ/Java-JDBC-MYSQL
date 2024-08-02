package org.example.repository;

import com.mysql.cj.protocol.Resultset;
import org.example.model.Employee;
import org.example.util.DataBaseConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }

    //Recupera los registro de la DB de la tabla employees
    //crea una lista vacia employees para almacenar los objetos Employee
    //Ejecuta la consulta SQL SELECT * FROM employees.
    //Itera sobre el ResultSet, creando un objeto Employee para cada registro y agregándolo a la lista.
    //Devuelve la lista employees que contiene todos los objetos Employee recuperados.

    //como es un metodo sobrescrito, debe de estar igual como en la interface Repository
    //pero va a tener un comportamiento distinto.
    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()){
                employees.add(createEmployee(rs));
            }

        } catch (SQLException e){
            throw new RuntimeException(e);

        }

        return employees;

    }

    //se usa para que los employees de la DB los convierta en un objeto Employee
    //Parámetros: Recibe un ResultSet (rs) como parámetro.
    //Devolución: Devuelve un nuevo objeto Employee con los datos obtenidos del ResultSet.
    private Employee createEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("email")
        );
        return employee;
    }

    @Override
    public Employee getById(Integer id) {
        return null;
    }

    @Override
    public void save(Employee employee) {

    }

    @Override
    public void delete(Integer id) {

    }
}
