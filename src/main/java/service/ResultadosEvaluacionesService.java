package service;

import dao.ResultadosEvaluacionesDAO;

public class ResultadosEvaluacionesService {
    private ResultadosEvaluacionesDAO resultadosEvaluacionesDAO;

    public ResultadosEvaluacionesService() {
        resultadosEvaluacionesDAO = new ResultadosEvaluacionesDAO();
    }

    public boolean registrarNotas(int idPostulacion, double notaPsicologica, double notaConocimiento, double notaEntrevista) {
        return resultadosEvaluacionesDAO.registrarNotas(idPostulacion, notaPsicologica, notaConocimiento, notaEntrevista);
    }
}