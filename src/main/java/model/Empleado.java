package model;
public class Empleado {
    private String nroRegistro;
    private String nombre;
    private String cargo;
    
    public Empleado() {
    }
    
    public Empleado(String nroRegistro, String nombre, String cargo) {
        this.nroRegistro = nroRegistro;
        this.nombre = nombre;
        this.cargo = cargo;
    }
    
    // Getters y setters
    public String getNroRegistro() {
        return nroRegistro;
    }
    
    public void setNroRegistro(String nroRegistro) {
        this.nroRegistro = nroRegistro;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
