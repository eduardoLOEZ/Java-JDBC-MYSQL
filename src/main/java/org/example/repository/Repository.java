package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    List<T> findAll() throws SQLException;

    T getById(Integer id);

    void save(T t);

    void update(Integer id, T t) throws SQLException;

    void delete(Integer id);

    List<T> findEmployeesPagination(int pageNumber, int pageSize) throws SQLException;
}
