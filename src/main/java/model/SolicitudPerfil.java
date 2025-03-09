package model;

import java.util.Date;

public class SolicitudPerfil {
    private int id;
    private Date fechaSolicitud;
    private String nombrePerfil;
    private int cantidad;
    private Date fechaExamen;
    private Date fechaEntrevista;

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public Date getFechaEntrevista() {
        return fechaEntrevista;
    }

    public void setFechaEntrevista(Date fechaEntrevista) {
        this.fechaEntrevista = fechaEntrevista;
    }
}