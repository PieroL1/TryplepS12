package controller;

import java.util.List;
import model.DetalleSolicitud;
import model.Perfil;
import model.SolicitudPerfil;
import model.SolicitudPersonal;
import model.Usuarios;
import service.SolicitudesService;
import service.UsuariosService;

public class SolicitudController {
     private SolicitudesService solicitudService;
    private SolicitudPersonal solicitudActual;
    private UsuariosService usuariosService;
    
    public SolicitudController() {
        solicitudService = new SolicitudesService();
         usuariosService = new UsuariosService();
    }
    
    public List<Perfil> obtenerPerfiles() {
        return solicitudService.obtenerPerfiles();
    }
    
    public SolicitudPersonal iniciarSolicitud(int idSolicitante) {
        solicitudActual = new SolicitudPersonal();
        solicitudActual.setIdSolicitante(idSolicitante);
        return solicitudActual;
    }

    public void agregarDetalle(Perfil perfil, int cantidad) {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudPersonal();
        }
        DetalleSolicitud detalle = new DetalleSolicitud(perfil, cantidad);
        solicitudActual.agregarDetalle(detalle);
    }
    
    public String registrarSolicitud() {
        return solicitudService.registrarSolicitud(solicitudActual);
    }

    public List<SolicitudPersonal> consultarSolicitudes() {
        return solicitudService.consultarSolicitudes();
    }
    
    public SolicitudPersonal consultarSolicitudPorId(int id) {
        return solicitudService.consultarSolicitudPorId(id);
    }

    
    public Usuarios obtenerEvaluador(String cargo) {
        return solicitudService.obtenerUsuarioPorCargo(cargo);
    }

    
    public List<Usuarios> obtenerJefesDeProyecto() {
        return solicitudService.obtenerUsuariosPorCargo("jefe_proyecto");
    }
    
     public List<SolicitudPersonal> obtenerSolicitudesPendientes() {
        return solicitudService.obtenerSolicitudesPendientes();
    }

    public List<Usuarios> obtenerEspecialistas() {
        return usuariosService.obtenerUsuariosPorRol("especialista_contrataciones");
    }

    public boolean asignarEspecialista(int solicitudId, String especialistaId) {
        return solicitudService.asignarEspecialista(solicitudId, especialistaId);
    }
    
    public List<SolicitudPerfil> obtenerSolicitudesAsignadasConPerfiles(String especialistaId) {
        System.out.println("Obteniendo solicitudes asignadas para el especialista ID: " + especialistaId);  // Imprimir el ID del especialista
        List<SolicitudPerfil> solicitudesPerfiles = solicitudService.obtenerSolicitudesAsignadasConPerfiles(especialistaId);
        System.out.println("Número de solicitudes obtenidas: " + solicitudesPerfiles.size());  // Imprimir el número de solicitudes obtenidas
        return solicitudesPerfiles;
    }
    
    public boolean guardarInformacionComplementaria(int solicitudId, String fechaExamen, String fechaEntrevista, String sueldo, String tipoContrato) {
        return solicitudService.guardarInformacionComplementaria(solicitudId, fechaExamen, fechaEntrevista, sueldo, tipoContrato);
    }
    
     public List<SolicitudPerfil> obtenerSolicitudesConInformacionComplementaria() {
        return solicitudService.obtenerSolicitudesConInformacionComplementaria();
    }

    public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        return solicitudService.registrarPostulacion(postulanteId, solicitudId);
    }

    
}