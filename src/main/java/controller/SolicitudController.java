package controller;

import java.util.List;
import model.DetalleSolicitud;
import model.Perfil;
import model.SolicitudPersonal;
import service.SolicitudService;
import service.SolicitudServiceImpl;

public class SolicitudController {
    private SolicitudService solicitudService;
    private SolicitudPersonal solicitudActual;
    
    public SolicitudController() {
        solicitudService = new SolicitudServiceImpl();
    }
    
    public List<Perfil> obtenerPerfiles() {
        return solicitudService.obtenerPerfiles();
    }
    
    public SolicitudPersonal iniciarSolicitud(String nroRegistroSolicitante) {
        solicitudActual = solicitudService.iniciarSolicitud(nroRegistroSolicitante);
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
        return solicitudService.consultarSolicitudesPorSolicitante(nroRegistro);
    }
}
