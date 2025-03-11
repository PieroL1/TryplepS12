package model;

public class ResultadosEvaluaciones {
    private int id;
    private int idPostulacion;
    private double notaPsicologica;
    private double notaConocimiento;
    private double notaEntrevista;
    private String nombrePostulante;
    private String apellidoPostulante;
    private String experienciaPostulante;
    private String nombrePerfil;

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPostulacion() {
        return idPostulacion;
    }

    public void setIdPostulacion(int idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public double getNotaPsicologica() {
        return notaPsicologica;
    }

    public void setNotaPsicologica(double notaPsicologica) {
        this.notaPsicologica = notaPsicologica;
    }

    public double getNotaConocimiento() {
        return notaConocimiento;
    }

    public void setNotaConocimiento(double notaConocimiento) {
        this.notaConocimiento = notaConocimiento;
    }

    public double getNotaEntrevista() {
        return notaEntrevista;
    }

    public void setNotaEntrevista(double notaEntrevista) {
        this.notaEntrevista = notaEntrevista;
    }

    public String getNombrePostulante() {
        return nombrePostulante;
    }

    public void setNombrePostulante(String nombrePostulante) {
        this.nombrePostulante = nombrePostulante;
    }

    public String getApellidoPostulante() {
        return apellidoPostulante;
    }

    public void setApellidoPostulante(String apellidoPostulante) {
        this.apellidoPostulante = apellidoPostulante;
    }

    public String getExperienciaPostulante() {
        return experienciaPostulante;
    }

    public void setExperienciaPostulante(String experienciaPostulante) {
        this.experienciaPostulante = experienciaPostulante;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
}