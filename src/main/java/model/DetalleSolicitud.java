package model;
public class DetalleSolicitud {
    private int id;
    private Perfil perfil;
    private int cantidad;
    
    public DetalleSolicitud() {
    }
    
    public DetalleSolicitud(Perfil perfil, int cantidad) {
        this.perfil = perfil;
        this.cantidad = cantidad;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Perfil getPerfil() {
        return perfil;
    }
    
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}