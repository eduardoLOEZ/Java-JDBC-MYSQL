package org.example.model;

public class Employee {

    //Encapsulacion

    /*

    Encapsulación se aplica aquí al hacer que los atributos id, name, age y email sean private.
     Esto oculta los detalles internos del objeto Employee, y solo se permite el acceso a través de los métodos públicos (getId, getName, etc.).
     Los datos sensibles están protegidos, y el acceso y la modificación de estos datos están controlados a través de los getters y el constructor.

     */

    //Atributos de la clase
    private Integer id;

    private String name;

    private Integer age;

    private String email;


    public Employee(Integer id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return
                "\n-------------------------------------"+
                        "\nEmployee" +
                        "\nid: " + id +
                        "\nname: " + name +
                        "\nage: " + age +
                        "\nemail: " + email +
                        "\n-------------------------------------"
                ;
    }
}
