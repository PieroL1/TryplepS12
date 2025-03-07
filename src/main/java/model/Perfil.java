package model;

public class Perfil {
    private int id;
    private String nombre;
    
    public Perfil() {
    }
    
    public Perfil(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}