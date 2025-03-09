package model;

import java.util.Date;

public class Postulacion {
    private int id;
    private int idPostulante;
    private int idSolicitud;
    private Date fechaPostulacion;
    private String nombrePostulante;
    private String apellidoPostulante;
    private String experienciaPostulante;

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPostulante() {
        return idPostulante;
    }

    public void setIdPostulante(int idPostulante) {
        this.idPostulante = idPostulante;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(Date fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public String getNombrePostulante() {
        return nombrePostulante;
    }

    public void setNombrePostulante(String nombrePostulante) {
        this.nombrePostulante = nombrePostulante;
    }

    public String getApellidoPostulante() {
        return apellidoPostulante;
    }

    public void setApellidoPostulante(String apellidoPostulante) {
        this.apellidoPostulante = apellidoPostulante;
    }

    public String getExperienciaPostulante() {
        return experienciaPostulante;
    }

    public void setExperienciaPostulante(String experienciaPostulante) {
        this.experienciaPostulante = experienciaPostulante;
    }
}