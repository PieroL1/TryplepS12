package dao;

import java.util.HashMap;
import java.util.Map;
import model.Empleado;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private static Map<String, Empleado> empleadosDb = new HashMap<>();
    
    static {
        // Inicializar con datos de ejemplo
        empleadosDb.put("E001", new Empleado("E001", "Juan Pérez", "Jefe de Proyecto"));
        empleadosDb.put("E002", new Empleado("E002", "María Rodríguez", "Jefe de Sistemas"));
    }
    
    @Override
    public Empleado obtenerEmpleado(String nroRegistro) {
        return empleadosDb.get(nroRegistro);
    }
    
    @Override
    public Empleado obtenerEmpleadoPorCargo(String cargo) {
        for (Empleado empleado : empleadosDb.values()) {
            if (empleado.getCargo().equals(cargo)) {
                return empleado;
            }
        }
        return null;
    }
}
