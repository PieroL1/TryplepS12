package view;

import controller.ResultadosEvaluacionesController;
import controller.ContratosController;
import model.ResultadosEvaluaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultarResultadosFormulario extends JDialog {
    private JTable tblResultados;
    private DefaultTableModel modeloTablaResultados;
    private JButton btnGenerarContrato;
    private ResultadosEvaluacionesController resultadosEvaluacionesController;
    private ContratosController contratosController;

    public ConsultarResultadosFormulario(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Consultar Resultados y Generar Contratos");
        setSize(800, 600);
        setLocationRelativeTo(parent);

        resultadosEvaluacionesController = new ResultadosEvaluacionesController();
        contratosController = new ContratosController();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de resultados
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados Obtenidos"));

        String[] columnasResultados = {"ID Postulación", "Nombre", "Apellido", "Experiencia", "Nota Psicológica", "Nota Conocimiento", "Nota Entrevista", "Perfil"};
        modeloTablaResultados = new DefaultTableModel(columnasResultados, 0);
        tblResultados = new JTable(modeloTablaResultados);
        JScrollPane scrollResultados = new JScrollPane(tblResultados);
        panelResultados.add(scrollResultados, BorderLayout.CENTER);

        panel.add(panelResultados, BorderLayout.CENTER);

        btnGenerarContrato = new JButton("Generar Contrato");
        panel.add(btnGenerarContrato, BorderLayout.SOUTH);

        add(panel);

        // Cargar los resultados obtenidos al iniciar
        cargarResultadosObtenidos();

        // Eventos
        btnGenerarContrato.addActionListener(e -> generarContrato());
    }

    private void cargarResultadosObtenidos() {
        List<ResultadosEvaluaciones> resultados = resultadosEvaluacionesController.obtenerResultadosEvaluaciones();
        actualizarTablaResultados(resultados);
    }

    private void actualizarTablaResultados(List<ResultadosEvaluaciones> resultados) {
        modeloTablaResultados.setRowCount(0);
        for (ResultadosEvaluaciones resultado : resultados) {
            Object[] fila = {
                resultado.getIdPostulacion(),
                resultado.getNombrePostulante(),
                resultado.getApellidoPostulante(),
                resultado.getExperienciaPostulante(),
                resultado.getNotaPsicologica(),
                resultado.getNotaConocimiento(),
                resultado.getNotaEntrevista(),
                resultado.getNombrePerfil()
            };
            modeloTablaResultados.addRow(fila);
        }
    }

    private void generarContrato() {
        int filaSeleccionada = tblResultados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un postulante para generar el contrato.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPostulacion = (int) modeloTablaResultados.getValueAt(filaSeleccionada, 0);
        String fechaInicio = JOptionPane.showInputDialog(this, "Ingrese la fecha de inicio del contrato (YYYY-MM-DD):");
        String fechaFin = JOptionPane.showInputDialog(this, "Ingrese la fecha de fin del contrato (YYYY-MM-DD):");
        String salario = JOptionPane.showInputDialog(this, "Ingrese el salario:");
        String tipoContrato = JOptionPane.showInputDialog(this, "Ingrese el tipo de contrato:");

        if (fechaInicio.isEmpty() || fechaFin.isEmpty() || salario.isEmpty() || tipoContrato.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar toda la información para generar el contrato.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = contratosController.generarContrato(idPostulacion, fechaInicio, fechaFin, Double.parseDouble(salario), tipoContrato);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Contrato generado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarResultadosObtenidos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al generar el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}