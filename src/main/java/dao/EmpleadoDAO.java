package dao;

import model.Empleado;

public interface EmpleadoDAO {
    Empleado obtenerEmpleado(String nroRegistro);
    Empleado obtenerEmpleadoPorCargo(String cargo);
}