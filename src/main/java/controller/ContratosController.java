package controller;

import service.ContratosService;

public class ContratosController {
    private ContratosService contratosService;

    public ContratosController() {
        contratosService = new ContratosService();
    }

     public boolean generarContrato(int idPostulacion, String fechaInicio, String fechaFin, double salario, String tipoContrato) {
        return contratosService.generarContrato(idPostulacion, fechaInicio, fechaFin, salario, tipoContrato);
    }
}