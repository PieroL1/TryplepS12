package controller;

import java.util.List;
import model.DetalleSolicitud;
import model.Perfil;
import model.SolicitudPersonal;
import model.Usuarios;
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
    
    public SolicitudPersonal iniciarSolicitud(int idSolicitante) {
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

    
    public List<SolicitudPersonal> consultarSolicitudes(int idSolicitante) {
        return solicitudService.consultarSolicitudesPorSolicitante(idSolicitante);
    }
    
    public Usuarios obtenerEvaluador(String cargo) {
        return solicitudService.obtenerUsuarioPorCargo(cargo);
    }

    
    public List<Usuarios> obtenerJefesDeProyecto() {
        return solicitudService.obtenerUsuariosPorCargo("jefe_proyecto");
    }

    
}