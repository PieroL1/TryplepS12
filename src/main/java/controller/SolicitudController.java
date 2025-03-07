package controller;

import java.util.List;
import model.DetalleSolicitud;
import model.Perfil;
import model.SolicitudPersonal;
import service.SolicitudesService;

public class SolicitudController {
    private SolicitudesService solicitudService;
    private SolicitudPersonal solicitudActual;
    
    public SolicitudController() {
        solicitudService = new SolicitudesService();
    }
    
    public List<Perfil> obtenerPerfiles() {
        return solicitudService.obtenerPerfiles();
    }
    
    public SolicitudPersonal iniciarSolicitud(String nroRegistroSolicitante) {
        int idSolicitante = Integer.parseInt(nroRegistroSolicitante); // CORREGIDO
        solicitudActual = solicitudService.iniciarSolicitud(idSolicitante);
        return solicitudActual;
    }

    
    public void agregarDetalle(Perfil perfil, int cantidad) {
        DetalleSolicitud detalle = new DetalleSolicitud(perfil, cantidad);
        solicitudActual.agregarDetalle(detalle);
    }
    
    public String registrarSolicitud() {
        return solicitudService.registrarSolicitud(solicitudActual);
    }
    
    public List<SolicitudPersonal> consultarSolicitudes(String nroRegistro) {
        int idSolicitante = Integer.parseInt(nroRegistro); // CORREGIDO
        return solicitudService.consultarSolicitudesPorSolicitante(idSolicitante);
    }
}
