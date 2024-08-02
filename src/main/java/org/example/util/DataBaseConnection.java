package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/project";

    private static String user = "root";

    private static String password = "123456";

    //myCoon es una variable static de tipo Connection utilizada para mantener la única instancia
    //de la conexión a la DB.

    private static Connection myCoon;


    //Este metodo static signifca que puede ser llamado sin crear la instancia
    //Patrón Singleton:  El método asegura que solo se cree una instancia de la conexión a la base de datos.


    public static Connection getInstance() throws SQLException {

        //verifica si no se ha creado una instancia, sino, la crea con DriverManager.getConnection

        if(myCoon ==null || myCoon.isClosed()){
            myCoon = DriverManager.getConnection(url, user, password);

        }
        //si no es null, o sea que ya hay una instancia, solo la retorna.
        return myCoon;
    }

}
