package service;

import dao.PostulanteDAO;
import model.Postulante;

public class PostulanteService {
    private PostulanteDAO postulanteDAO;

    public PostulanteService() {
        postulanteDAO = new PostulanteDAO();
    }

    public Postulante login(String email, String password) {
        return postulanteDAO.login(email, password);
    }
}