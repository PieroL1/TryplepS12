package controller;

import service.ResultadosEvaluacionesService;

public class ResultadosEvaluacionesController {
    private ResultadosEvaluacionesService resultadosEvaluacionesService;

    public ResultadosEvaluacionesController() {
        resultadosEvaluacionesService = new ResultadosEvaluacionesService();
    }

    public boolean registrarNotas(int idPostulacion, double notaPsicologica, double notaConocimiento, double notaEntrevista) {
        return resultadosEvaluacionesService.registrarNotas(idPostulacion, notaPsicologica, notaConocimiento, notaEntrevista);
    }
}