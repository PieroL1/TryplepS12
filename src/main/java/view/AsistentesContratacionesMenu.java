package view;

import model.Postulacion;
import service.PostulacionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AsistentesContratacionesMenu extends JFrame {
    private JTable tblPostulaciones;
    private DefaultTableModel modeloTablaPostulaciones;
    private JButton btnRegistrarNotas;
    private PostulacionService postulacionService;

    public AsistentesContratacionesMenu() {
        postulacionService = new PostulacionService();

        setTitle("Asistente de Contrataciones - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de postulaciones
        JPanel panelPostulaciones = new JPanel(new BorderLayout());
        panelPostulaciones.setBorder(BorderFactory.createTitledBorder("Postulaciones"));

        String[] columnasPostulaciones = {"ID", "Postulante", "Fecha Postulación", "Solicitud ID", "Experiencia"};
        modeloTablaPostulaciones = new DefaultTableModel(columnasPostulaciones, 0);
        tblPostulaciones = new JTable(modeloTablaPostulaciones);
        JScrollPane scrollPostulaciones = new JScrollPane(tblPostulaciones);
        panelPostulaciones.add(scrollPostulaciones, BorderLayout.CENTER);

        panel.add(panelPostulaciones, BorderLayout.CENTER);

        btnRegistrarNotas = new JButton("Registrar Notas de Evaluación");
        panel.add(btnRegistrarNotas, BorderLayout.SOUTH);

        add(panel);

        // Cargar las postulaciones disponibles al iniciar
        cargarPostulacionesDisponibles();

        // Eventos
        btnRegistrarNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarNotas();
            }
        });
    }

    private void cargarPostulacionesDisponibles() {
        List<Postulacion> postulaciones = postulacionService.obtenerPostulaciones();
        actualizarTablaPostulaciones(postulaciones);
    }

    private void actualizarTablaPostulaciones(List<Postulacion> postulaciones) {
        modeloTablaPostulaciones.setRowCount(0);
        for (Postulacion postulacion : postulaciones) {
            Object[] fila = {
                postulacion.getId(),
                postulacion.getNombrePostulante() + " " + postulacion.getApellidoPostulante(),
                postulacion.getFechaPostulacion(),
                postulacion.getIdSolicitud(),
                postulacion.getExperienciaPostulante()
            };
            modeloTablaPostulaciones.addRow(fila);
        }
    }

    private void registrarNotas() {
        int filaSeleccionada = tblPostulaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una postulación.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPostulacion = (int) modeloTablaPostulaciones.getValueAt(filaSeleccionada, 0);
        RegistrarNotasFormulario registrarNotasFormulario = new RegistrarNotasFormulario(this, true, idPostulacion);
        registrarNotasFormulario.setVisible(true);

        if (registrarNotasFormulario.isFormularioCompleto()) {
            cargarPostulacionesDisponibles();
        }
    }
}