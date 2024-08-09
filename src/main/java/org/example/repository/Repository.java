package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    //Abstraccion y Generics

    /*

    Abstracción se aplica aquí mediante el uso de una interfaz.
    La interfaz Repository define un conjunto de operaciones que se pueden realizar en un repositorio,
    como findAll, getById, save, update, delete, y findEmployeesPagination.


     */

    List<T> findAll() throws SQLException;

    T getById(Integer id);

    void save(T t);

    void update(Integer id, T t) throws SQLException;

    void delete(Integer id);

    List<T> findEmployeesPagination(int pageNumber, int pageSize) throws SQLException;
}
