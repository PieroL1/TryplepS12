package model;

public class Usuarios {
    private String id;
    private String nombre;
    private String cargo;
    private String password;

    // Constructor sin parámetros
    public Usuarios() {
    }

    // Constructor con ID y Password
    public Usuarios(String id, String nombre, String cargo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.password = password;
    }

    // Métodos Getter y Setter para id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Métodos Getter y Setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos Getter y Setter para cargo
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Métodos Getter y Setter para password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return nombre;
    }
}