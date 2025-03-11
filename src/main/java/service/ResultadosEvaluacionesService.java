package service;

import dao.ResultadosEvaluacionesDAO;
import model.ResultadosEvaluaciones;

import java.util.List;

public class ResultadosEvaluacionesService {
    private ResultadosEvaluacionesDAO resultadosEvaluacionesDAO;

    public ResultadosEvaluacionesService() {
        resultadosEvaluacionesDAO = new ResultadosEvaluacionesDAO();
    }
    public boolean registrarNotas(int idPostulacion, double notaPsicologica, double notaConocimiento, double notaEntrevista) {
        return resultadosEvaluacionesDAO.registrarNotas(idPostulacion, notaPsicologica, notaConocimiento, notaEntrevista);
    }

    public List<ResultadosEvaluaciones> obtenerResultadosEvaluaciones() {
        return resultadosEvaluacionesDAO.obtenerResultadosEvaluaciones();
    }
}