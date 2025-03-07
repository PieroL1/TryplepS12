package service;

import dao.UsuariosDAO;
import dao.PerfilDAO;
import dao.PerfilDAOImpl;
import dao.SolicitudesDAO;
import java.util.List;
import model.Usuarios;
import model.Perfil;
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

    // Inicializar una solicitud con el solicitante (Jefe de Proyecto) y el evaluador (Jefe de Sistemas)
    public SolicitudPersonal iniciarSolicitud(int idSolicitante) {
        SolicitudPersonal solicitud = new SolicitudPersonal();

        // Obtener solicitante (Jefe de Proyecto)
        Usuarios solicitante = usuariosDAO.obtenerUsuario(String.valueOf(idSolicitante));
        if (solicitante != null) {
            solicitud.setIdSolicitante(Integer.parseInt(solicitante.getId())); // CORREGIDO
        }

        // Obtener evaluador (Jefe de Sistemas)
        Usuarios evaluador = usuariosDAO.obtenerUsuarioPorCargo("jefe_sistemas");
        if (evaluador != null) {
            solicitud.setIdEvaluador(Integer.parseInt(evaluador.getId())); // CORREGIDO
        }

        return solicitud;
    }

    // Consultar las solicitudes realizadas por un solicitante
    public List<SolicitudPersonal> consultarSolicitudesPorSolicitante(int idSolicitante) {
        return solicitudesDAO.listarSolicitudesPorSolicitante(idSolicitante);
    }
}
