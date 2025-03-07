
package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;
import model.Perfil;

public class PerfilDAOImpl implements PerfilDAO {
    private static Map<Integer, Perfil> perfilesDb = new HashMap<>();
    private static int sequence = 1;
    
    static {
        // Inicializar con datos de ejemplo
        perfilesDb.put(sequence, new Perfil(sequence++, "Ingeniero"));
        perfilesDb.put(sequence, new Perfil(sequence++, "Analista"));
        perfilesDb.put(sequence, new Perfil(sequence++, "Programador"));
        perfilesDb.put(sequence, new Perfil(sequence++, "Analista de Calidad"));
        perfilesDb.put(sequence, new Perfil(sequence++, "Analista Funcional"));
        perfilesDb.put(sequence, new Perfil(sequence++, "DBA"));
        perfilesDb.put(sequence, new Perfil(sequence++, "Diseñador Software"));
    }
    
    @Override
    public List<Perfil> listarPerfiles() {
        return new ArrayList<>(perfilesDb.values());
    }
    
    @Override
    public Perfil obtenerPerfil(int id) {
        return perfilesDb.get(id);
    }
    
    @Override
    public void agregarPerfil(Perfil perfil) {
        perfil.setId(sequence++);
        perfilesDb.put(perfil.getId(), perfil);
    }
    
    @Override
    public void modificarPerfil(Perfil perfil) {
        perfilesDb.put(perfil.getId(), perfil);
    }
    
    @Override
    public void eliminarPerfil(int id) {
        perfilesDb.remove(id);
    }
}