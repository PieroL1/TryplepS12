package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SolicitudPersonal {
    private int numeroSolicitud;
    private Timestamp fechaRegistro;
    private int idSolicitante; // Almacena el ID del solicitante (Usuario)
    private int idEvaluador; // Almacena el ID del evaluador (Usuario)
    private String estado; // PENDIENTE, APROBADO, RECHAZADO
    private List<DetalleSolicitud> detalles;

    public SolicitudPersonal() {
        this.detalles = new ArrayList<>();
        this.fechaRegistro = new Timestamp(System.currentTimeMillis());
        this.estado = "PENDIENTE";
    }

    // Constructor con parámetros
    public SolicitudPersonal(int numeroSolicitud, Timestamp fechaRegistro, int idSolicitante, int idEvaluador, String estado) {
        this.numeroSolicitud = numeroSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.idSolicitante = idSolicitante;
        this.idEvaluador = idEvaluador;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }

    // Getters y Setters
    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public int getIdEvaluador() {
        return idEvaluador;
    }

    public void setIdEvaluador(int idEvaluador) {
        this.idEvaluador = idEvaluador;
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
