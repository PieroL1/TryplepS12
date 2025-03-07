package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.SolicitudPersonal;

public class SolicitudDAOImpl implements SolicitudDAO {
    private static Map<String, SolicitudPersonal> solicitudesDb = new HashMap<>();
    private static int secuencia = 1;
    
    @Override
    public String registrarSolicitud(SolicitudPersonal solicitud) {
        String numeroSolicitud = generarNumeroSolicitud();
        solicitud.setNumeroSolicitud(numeroSolicitud);
        solicitudesDb.put(numeroSolicitud, solicitud);
        return numeroSolicitud;
    }
    
    @Override
    public SolicitudPersonal obtenerSolicitud(String numeroSolicitud) {
        return solicitudesDb.get(numeroSolicitud);
    }
    
    @Override
    public List<SolicitudPersonal> listarSolicitudesPorSolicitante(String nroRegistro) {
        List<SolicitudPersonal> resultado = new ArrayList<>();
        for (SolicitudPersonal solicitud : solicitudesDb.values()) {
            if (solicitud.getSolicitante().getNroRegistro().equals(nroRegistro)) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }
    
    @Override
    public List<SolicitudPersonal> listarSolicitudesPorEstado(String estado) {
        List<SolicitudPersonal> resultado = new ArrayList<>();
        for (SolicitudPersonal solicitud : solicitudesDb.values()) {
            if (solicitud.getEstado().equals(estado)) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }
    
    @Override
    public void actualizarEstadoSolicitud(String numeroSolicitud, String nuevoEstado) {
        SolicitudPersonal solicitud = solicitudesDb.get(numeroSolicitud);
        if (solicitud != null) {
            solicitud.setEstado(nuevoEstado);
            solicitudesDb.put(numeroSolicitud, solicitud);
        }
    }
    
    private String generarNumeroSolicitud() {
        return String.format("SOL%05d", secuencia++);
    }
}