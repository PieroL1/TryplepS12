package service;

import java.util.List;
import model.Perfil;
import model.SolicitudPersonal;

public interface SolicitudService {
    List<Perfil> obtenerPerfiles();
    String registrarSolicitud(SolicitudPersonal solicitud);
    SolicitudPersonal iniciarSolicitud(String nroRegistroSolicitante);
    List<SolicitudPersonal> consultarSolicitudesPorSolicitante(String nroRegistro);
}