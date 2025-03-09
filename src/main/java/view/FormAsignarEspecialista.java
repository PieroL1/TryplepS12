package view;

import controller.SolicitudController;
import model.SolicitudPersonal;
import model.Usuarios;
import util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormAsignarEspecialista extends JFrame {
    private JTable tblSolicitudes;
    private DefaultTableModel modeloTablaSolicitudes;
    private JComboBox<Usuarios> cmbEspecialistas;
    private JButton btnAsignar;
    private SolicitudController controller;

    public FormAsignarEspecialista() {
        controller = new SolicitudController();

        // Verificar si el usuario actual es Jefe de Contrataciones
        Usuarios usuarioActual = (Usuarios) SessionManager.getUsuarioActual();
        if (!"jefe_contrataciones".equalsIgnoreCase(usuarioActual.getCargo())) {
            JOptionPane.showMessageDialog(this, "Acceso denegado. Solo el Jefe de Contrataciones puede acceder a esta funcionalidad.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Asignar Especialista a Solicitud");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de solicitudes
        JPanel panelSolicitudes = new JPanel(new BorderLayout());
        panelSolicitudes.setBorder(BorderFactory.createTitledBorder("Solicitudes Pendientes"));

        String[] columnasSolicitudes = {"ID", "ID Jefe Proyecto", "Estado", "Fecha Solicitud"};
        modeloTablaSolicitudes = new DefaultTableModel(columnasSolicitudes, 0);
        tblSolicitudes = new JTable(modeloTablaSolicitudes);
        JScrollPane scrollSolicitudes = new JScrollPane(tblSolicitudes);
        panelSolicitudes.add(scrollSolicitudes, BorderLayout.CENTER);

        panel.add(panelSolicitudes, BorderLayout.CENTER);

        // Panel de asignación
        JPanel panelAsignacion = new JPanel(new GridBagLayout());
        panelAsignacion.setBorder(BorderFactory.createTitledBorder("Asignar Especialista"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelAsignacion.add(new JLabel("Especialista:"), gbc);

        cmbEspecialistas = new JComboBox<>();
        List<Usuarios> especialistas = controller.obtenerEspecialistas();
        for (Usuarios especialista : especialistas) {
            cmbEspecialistas.addItem(especialista);
        }
        gbc.gridx = 1;
        panelAsignacion.add(cmbEspecialistas, gbc);

        btnAsignar = new JButton("Asignar Especialista");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelAsignacion.add(btnAsignar, gbc);

        panel.add(panelAsignacion, BorderLayout.SOUTH);

        add(panel);

        // Cargar las solicitudes pendientes al iniciar
        cargarSolicitudesPendientes();

        // Eventos
        btnAsignar.addActionListener(e -> asignarEspecialista());
    }

    private void cargarSolicitudesPendientes() {
        List<SolicitudPersonal> solicitudes = controller.obtenerSolicitudesPendientes();
        actualizarTablaSolicitudes(solicitudes);
    }

    private void actualizarTablaSolicitudes(List<SolicitudPersonal> solicitudes) {
        modeloTablaSolicitudes.setRowCount(0);
        for (SolicitudPersonal solicitud : solicitudes) {
            Object[] fila = {solicitud.getId(), solicitud.getIdJefeProyecto(), solicitud.getEstado(), solicitud.getFechaSolicitud()};
            modeloTablaSolicitudes.addRow(fila);
        }
    }

    private void asignarEspecialista() {
        int filaSeleccionada = tblSolicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud para asignar un especialista.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int solicitudId = (int) modeloTablaSolicitudes.getValueAt(filaSeleccionada, 0);
        Usuarios especialistaSeleccionado = (Usuarios) cmbEspecialistas.getSelectedItem();

        if (especialistaSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un especialista.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = controller.asignarEspecialista(solicitudId, especialistaSeleccionado.getId());
        if (exito) {
            JOptionPane.showMessageDialog(this, "Especialista asignado exitosamente.", "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarSolicitudesPendientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al asignar el especialista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}