package controller;

import service.PostulacionService;

public class PostulacionController {
    private PostulacionService postulacionService;

    public PostulacionController() {
        postulacionService = new PostulacionService();
    }

    public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        System.out.println("Controlador: Registrando postulación para postulanteId: " + postulanteId + " y solicitudId: " + solicitudId);
        return postulacionService.registrarPostulacion(postulanteId, solicitudId);
    }

    public boolean actualizarPostulante(int id, String nombre, String apellido, String email, String telefono, String direccion, String experiencia) {
        System.out.println("Controlador: Actualizando postulante con id: " + id);
        return postulacionService.actualizarPostulante(id, nombre, apellido, email, telefono, direccion, experiencia);
    }
}