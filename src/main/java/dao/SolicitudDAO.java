package dao;
import java.util.List;
import model.SolicitudPersonal;

public interface SolicitudDAO {
    String registrarSolicitud(SolicitudPersonal solicitud);
    SolicitudPersonal obtenerSolicitud(String numeroSolicitud);
    List<SolicitudPersonal> listarSolicitudesPorSolicitante(String nroRegistro);
    List<SolicitudPersonal> listarSolicitudesPorEstado(String estado);
    void actualizarEstadoSolicitud(String numeroSolicitud, String nuevoEstado);
}