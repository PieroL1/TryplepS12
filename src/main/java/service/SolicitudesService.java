package service;

import dao.UsuariosDAO;
import dao.PerfilDAO;
import dao.PerfilDAOImpl;
import dao.SolicitudesDAO;
import java.util.List;
import model.Usuarios;
import model.Perfil;
import model.SolicitudPerfil;
import model.SolicitudPersonal;

public class SolicitudesService {
    private PerfilDAO perfilDAO;
    private UsuariosDAO usuariosDAO;
    private SolicitudesDAO solicitudesDAO;
    
    public SolicitudesService() {
        perfilDAO = new PerfilDAOImpl();
        usuariosDAO = new UsuariosDAO();
        solicitudesDAO = new SolicitudesDAO();
    }
    
    // Obtener la lista de perfiles disponibles
    public List<Perfil> obtenerPerfiles() {
        return perfilDAO.listarPerfiles();
    }

    // Registrar una nueva solicitud en la BD
    public String registrarSolicitud(SolicitudPersonal solicitud) {
        return solicitudesDAO.registrarSolicitud(solicitud);
    }

    // Inicializar una solicitud con el solicitante (Jefe de Sistemas) y el evaluador (Jefe de Proyecto)
    public SolicitudPersonal iniciarSolicitud(int idSolicitante) {
        SolicitudPersonal solicitud = new SolicitudPersonal();

        // Obtener solicitante (Jefe de Sistemas)
        Usuarios solicitante = usuariosDAO.obtenerUsuario(String.valueOf(idSolicitante));
        if (solicitante != null) {
            solicitud.setIdSolicitante(Integer.parseInt(solicitante.getId())); // CORREGIDO
        }

        // Obtener evaluador (Jefe de Proyecto)
        Usuarios evaluador = usuariosDAO.obtenerUsuarioPorCargo("jefe_proyecto");
        if (evaluador != null) {
            solicitud.setIdEvaluador(Integer.parseInt(evaluador.getId())); // CORREGIDO
        }

        return solicitud;
    }

    // Consultar todas las solicitudes
    public List<SolicitudPersonal> consultarSolicitudes() {
        return solicitudesDAO.listarSolicitudes();
    }

    // Consultar una solicitud por su ID
    public SolicitudPersonal consultarSolicitudPorId(int id) {
        return solicitudesDAO.obtenerSolicitudPorId(id);
    }

    // Consultar las solicitudes realizadas por un solicitante
    public List<SolicitudPersonal> consultarSolicitudesPorSolicitante(int idSolicitante) {
        return solicitudesDAO.listarSolicitudesPorSolicitante(idSolicitante);
    }

    public Usuarios obtenerUsuarioPorCargo(String cargo) {
        return usuariosDAO.obtenerUsuarioPorCargo(cargo);
    }

    public List<Usuarios> obtenerUsuariosPorCargo(String cargo) {
        return usuariosDAO.obtenerUsuariosPorCargo(cargo);
    }
    
    public List<SolicitudPersonal> obtenerSolicitudesPendientes() {
        return solicitudesDAO.obtenerSolicitudesPendientes();
    }

    public boolean asignarEspecialista(int solicitudId, String especialistaId) {
        return solicitudesDAO.asignarEspecialista(solicitudId, especialistaId);
    }
    
     public List<SolicitudPerfil> obtenerSolicitudesAsignadasConPerfiles(String especialistaId) {
        System.out.println("Servicio: obteniendo solicitudes asignadas para el especialista ID: " + especialistaId);  // Imprimir el ID del especialista
        List<SolicitudPerfil> solicitudesPerfiles = solicitudesDAO.obtenerSolicitudesAsignadasConPerfiles(especialistaId);
        System.out.println("Servicio: número de solicitudes obtenidas: " + solicitudesPerfiles.size());  // Imprimir el número de solicitudes obtenidas
        return solicitudesPerfiles;
    }
     
    public boolean guardarInformacionComplementaria(int solicitudId, String fechaExamen, String fechaEntrevista, String sueldo, String tipoContrato) {
        return solicitudesDAO.guardarInformacionComplementaria(solicitudId, fechaExamen, fechaEntrevista, sueldo, tipoContrato);
    }
    
    public List<SolicitudPerfil> obtenerSolicitudesConInformacionComplementaria() {
        return solicitudesDAO.obtenerSolicitudesConInformacionComplementaria();
    }

   

    public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        return solicitudesDAO.registrarPostulacion(postulanteId, solicitudId);
    }
}