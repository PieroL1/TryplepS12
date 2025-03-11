package service;

import dao.ContratosDAO;

public class ContratosService {
    private ContratosDAO contratosDAO;

    public ContratosService() {
        contratosDAO = new ContratosDAO();
    }

  public boolean generarContrato(int idPostulacion, String fechaInicio, String fechaFin, double salario, String tipoContrato) {
        return contratosDAO.generarContrato(idPostulacion, fechaInicio, fechaFin, salario, tipoContrato);
    }
}