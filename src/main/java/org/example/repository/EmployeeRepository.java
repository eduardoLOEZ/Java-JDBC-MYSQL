package org.example.repository;

import com.mysql.cj.protocol.Resultset;
import org.example.model.Employee;
import org.example.util.DataBaseConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {

    //este metodo llama a DataBaseConnection.getInstance, el cual retorna la conexion
    //e instancia de la conexion, la cual ya existe, por lo tanto es Singleton y retorna la instacnia.
    /*private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
     */

    //como es una sola transaccion en la que vamos a trabajar
    //hagamos que tenga una sola conexión gestionada desde fuera de los métodos
    //en lugar de crear una nueva conexión para cada operación.
    private Connection myConn;

    public EmployeeRepository(Connection myConn){
        this.myConn = myConn;
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


        //con esa conexion, creamos el statement para la query y el resultset para ejecutar la query y
        //traer los resultados
        try(
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {


            //mientras haya registros en el resultset
            //vamos a agregar dentro de la List cada uno con el metodo createEmployee()
            //el cual convierte los registros a objeto Employee
            //y los retorna y muestra por console en el formato personalizado de tostring();
            while(rs.next()){
                employees.add(createEmployee(rs));
            }

        } catch (SQLException e){
            throw new RuntimeException(e);

        }

        //retornamos la List con los registros ya guardados ahi.
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
    public Employee getById(Integer id){

        String sql = "SELECT * FROM employees WHERE id = ?";

        try(PreparedStatement psmt = myConn.prepareStatement(sql)){

            psmt.setInt(1,id);

            try( ResultSet rs = psmt.executeQuery()){

                if(rs.next()){
                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void save(Employee employee) {
        String sql = "INSERT INTO employees (name, age, email) VALUES(?, ?, ?)";

        try(
            PreparedStatement pstmt = myConn.prepareStatement(sql)){

            pstmt.setString(1, employee.getName());
            pstmt.setInt(2, employee.getAge());
            pstmt.setString(3, employee.getEmail());
            pstmt.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(Integer id) {

        String sql = "DELETE FROM employees WHERE id =  ?";

        try(
            PreparedStatement pstmt = myConn.prepareStatement(sql)){

            // Establece el valor del ID en la consulta preparada
            //el 1 indica la posición del parametro en la consulta.
            pstmt.setInt(1, id);
            // Ejecuta la consulta SQL
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Employee> findEmployeesPagination(int pageNumber, int pageSize) throws SQLException {
        List<Employee> employeesListPagination = new ArrayList<>();

        String sql = "SELECT * FROM employees LIMIT ? OFFSET ?";

        try(PreparedStatement psmt = myConn.prepareStatement(sql)){

            int offset = (pageNumber -1)* pageSize;

            psmt.setInt(1, pageSize);
            psmt.setInt(2, offset);

            try(ResultSet rs = psmt.executeQuery()){

                while(rs.next()){

                    Employee employee = new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("email")
                    );

                    employeesListPagination.add(employee);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return employeesListPagination;
    }

    @Override
    public void update(Integer id, Employee employee) throws SQLException{

        if(employee.getId() != null){
            String sql = "UPDATE employees SET name = ?, age = ?, email = ? WHERE id = ?";

            try(
                PreparedStatement pstmt = myConn.prepareStatement(sql)){


                pstmt.setString(1, employee.getName());
                pstmt.setInt(2, employee.getAge());
                pstmt.setString(3, employee.getEmail());
                pstmt.setInt(4, id);
                pstmt.executeUpdate();
            }
            System.out.println("Empleado existe, actualizacion exitosa");
        }else {
            System.out.println("Empleado no existe.");
        }

    }
}
