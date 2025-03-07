package service;

import dao.EmpleadoDAO;
import dao.EmpleadoDAOImpl;
import dao.PerfilDAO;
import dao.PerfilDAOImpl;
import dao.SolicitudDAO;
import dao.SolicitudDAOImpl;
import java.util.List;
import model.Empleado;
import model.Perfil;
import model.SolicitudPersonal;

public class SolicitudServiceImpl implements SolicitudService {
    private PerfilDAO perfilDAO;
    private EmpleadoDAO empleadoDAO;
    private SolicitudDAO solicitudDAO;
    
    public SolicitudServiceImpl() {
        perfilDAO = new PerfilDAOImpl();
        empleadoDAO = new EmpleadoDAOImpl();
        solicitudDAO = new SolicitudDAOImpl();
    }
    
    @Override
    public List<Perfil> obtenerPerfiles() {
        return perfilDAO.listarPerfiles();
    }
    
    @Override
    public String registrarSolicitud(SolicitudPersonal solicitud) {
        return solicitudDAO.registrarSolicitud(solicitud);
    }
    
    @Override
    public SolicitudPersonal iniciarSolicitud(String nroRegistroSolicitante) {
        SolicitudPersonal solicitud = new SolicitudPersonal();
        
        // Obtener solicitante (Jefe de proyecto)
        Empleado solicitante = empleadoDAO.obtenerEmpleado(nroRegistroSolicitante);
        solicitud.setSolicitante(solicitante);
        
        // Obtener evaluador (Jefe de sistemas)
        Empleado evaluador = empleadoDAO.obtenerEmpleadoPorCargo("Jefe de Sistemas");
        solicitud.setEvaluador(evaluador);
        
        return solicitud;
    }
    
    @Override
    public List<SolicitudPersonal> consultarSolicitudesPorSolicitante(String nroRegistro) {
        return solicitudDAO.listarSolicitudesPorSolicitante(nroRegistro);
    }
}