package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolicitudPersonal {
    private String numeroSolicitud;
    private Date fechaRegistro;
    private Empleado solicitante;
    private Empleado evaluador;
    private String estado; // PENDIENTE, APROBADO, RECHAZADO
    private List<DetalleSolicitud> detalles;
    
    public SolicitudPersonal() {
        this.detalles = new ArrayList<>();
        this.fechaRegistro = new Date();
        this.estado = "PENDIENTE";
    }
    
    // Getters y setters
    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }
    
    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Empleado getSolicitante() {
        return solicitante;
    }
    
    public void setSolicitante(Empleado solicitante) {
        this.solicitante = solicitante;
    }
    
    public Empleado getEvaluador() {
        return evaluador;
    }
    
    public void setEvaluador(Empleado evaluador) {
        this.evaluador = evaluador;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<DetalleSolicitud> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleSolicitud> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(DetalleSolicitud detalle) {
        this.detalles.add(detalle);
    }
}
