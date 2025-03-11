package service;

import dao.PostulacionDAO;
import java.util.List;
import model.Postulacion;

public class PostulacionService {
    private PostulacionDAO postulacionDAO;

    public PostulacionService() {
        postulacionDAO = new PostulacionDAO();
    }

    public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        System.out.println("Servicio: Registrando postulación para postulanteId: " + postulanteId + " y solicitudId: " + solicitudId);
        return postulacionDAO.registrarPostulacion(postulanteId, solicitudId);
    }

    public boolean actualizarPostulante(int id, String nombre, String apellido, String email, String telefono, String direccion, String experiencia) {
        System.out.println("Servicio: Actualizando postulante con id: " + id);
        return postulacionDAO.actualizarPostulante(id, nombre, apellido, email, telefono, direccion, experiencia);
    }
    
public List<Postulacion> obtenerPostulaciones() {
        return postulacionDAO.obtenerPostulaciones();
    }
}