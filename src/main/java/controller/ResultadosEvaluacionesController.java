package controller;

import model.ResultadosEvaluaciones;
import service.ResultadosEvaluacionesService;

import java.util.List;

public class ResultadosEvaluacionesController {
    private ResultadosEvaluacionesService resultadosEvaluacionesService;

    public ResultadosEvaluacionesController() {
        resultadosEvaluacionesService = new ResultadosEvaluacionesService();
    }
    
     public boolean registrarNotas(int idPostulacion, double notaPsicologica, double notaConocimiento, double notaEntrevista) {
        return resultadosEvaluacionesService.registrarNotas(idPostulacion, notaPsicologica, notaConocimiento, notaEntrevista);
    }
    
   public List<ResultadosEvaluaciones> obtenerResultadosEvaluaciones() {
        return resultadosEvaluacionesService.obtenerResultadosEvaluaciones();
    }
}