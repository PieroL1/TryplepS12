package view;

import controller.SolicitudController;
import model.SolicitudPerfil;
import util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormComplementarSolicitud extends JFrame {
    private JTable tblSolicitudes;
    private DefaultTableModel modeloTablaSolicitudes;
    private JTextField txtFechaExamen;
    private JTextField txtFechaEntrevista;
    private JTextField txtSueldo;
    private JTextField txtTipoContrato;
    private JButton btnGuardar;
    private JButton btnConsultarResultados;
    private SolicitudController controller;

    public FormComplementarSolicitud() {
        controller = new SolicitudController();

        // Verificar si el usuario actual es Especialista de Contrataciones
        if (!SessionManager.getUsuarioActual().getCargo().equalsIgnoreCase("especialista_contrataciones")) {
            JOptionPane.showMessageDialog(this, "Acceso denegado. Solo el Especialista de Contrataciones puede acceder a esta funcionalidad.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Complementar Información de Solicitud");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de solicitudes
        JPanel panelSolicitudes = new JPanel(new BorderLayout());
        panelSolicitudes.setBorder(BorderFactory.createTitledBorder("Solicitudes Asignadas"));

        String[] columnasSolicitudes = {"ID", "Fecha Solicitud", "Perfil", "Cantidad"};
        modeloTablaSolicitudes = new DefaultTableModel(columnasSolicitudes, 0);
        tblSolicitudes = new JTable(modeloTablaSolicitudes);
        JScrollPane scrollSolicitudes = new JScrollPane(tblSolicitudes);
        panelSolicitudes.add(scrollSolicitudes, BorderLayout.CENTER);

        panel.add(panelSolicitudes, BorderLayout.CENTER);

        // Panel de información complementaria
        JPanel panelInformacion = new JPanel(new GridBagLayout());
        panelInformacion.setBorder(BorderFactory.createTitledBorder("Información Complementaria"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInformacion.add(new JLabel("Fecha Examen (YYYY-MM-DD):"), gbc);

        txtFechaExamen = new JTextField(20);
        gbc.gridx = 1;
        panelInformacion.add(txtFechaExamen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInformacion.add(new JLabel("Fecha Entrevista (YYYY-MM-DD):"), gbc);

        txtFechaEntrevista = new JTextField(20);
        gbc.gridx = 1;
        panelInformacion.add(txtFechaEntrevista, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInformacion.add(new JLabel("Sueldo:"), gbc);

        txtSueldo = new JTextField(20);
        gbc.gridx = 1;
        panelInformacion.add(txtSueldo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInformacion.add(new JLabel("Tipo de Contrato:"), gbc);

        txtTipoContrato = new JTextField(20);
        gbc.gridx = 1;
        panelInformacion.add(txtTipoContrato, gbc);

        btnGuardar = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelInformacion.add(btnGuardar, gbc);

        btnConsultarResultados = new JButton("Consultar Resultados");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panelInformacion.add(btnConsultarResultados, gbc);

        panel.add(panelInformacion, BorderLayout.SOUTH);

        add(panel);

        // Cargar las solicitudes asignadas al iniciar
        cargarSolicitudesAsignadas();

        // Eventos
        btnGuardar.addActionListener(e -> guardarInformacionComplementaria());
        btnConsultarResultados.addActionListener(e -> consultarResultados());
    }

    private void cargarSolicitudesAsignadas() {
        String especialistaId = SessionManager.getUsuarioActual().getId();
        System.out.println("Especialista ID: " + especialistaId);  // Imprimir el ID del especialista
        List<SolicitudPerfil> solicitudesPerfiles = controller.obtenerSolicitudesAsignadasConPerfiles(especialistaId);
        System.out.println("Solicitudes obtenidas: " + solicitudesPerfiles.size());  // Imprimir el número de solicitudes obtenidas
        actualizarTablaSolicitudes(solicitudesPerfiles);
    }

    private void actualizarTablaSolicitudes(List<SolicitudPerfil> solicitudesPerfiles) {
        modeloTablaSolicitudes.setRowCount(0);
        for (SolicitudPerfil solicitudPerfil : solicitudesPerfiles) {
            System.out.println("Solicitud: " + solicitudPerfil.getId() + ", Perfil: " + solicitudPerfil.getNombrePerfil() + ", Cantidad: " + solicitudPerfil.getCantidad());  // Imprimir los datos de cada solicitud
            Object[] fila = {solicitudPerfil.getId(), solicitudPerfil.getFechaSolicitud(), solicitudPerfil.getNombrePerfil(), solicitudPerfil.getCantidad()};
            modeloTablaSolicitudes.addRow(fila);
        }
    }

    private void guardarInformacionComplementaria() {
        int filaSeleccionada = tblSolicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud para complementar la información.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int solicitudId = (int) modeloTablaSolicitudes.getValueAt(filaSeleccionada, 0);
        String fechaExamen = txtFechaExamen.getText().trim();
        String fechaEntrevista = txtFechaEntrevista.getText().trim();
        String sueldo = txtSueldo.getText().trim();
        String tipoContrato = txtTipoContrato.getText().trim();

        if (fechaExamen.isEmpty() || fechaEntrevista.isEmpty() || sueldo.isEmpty() || tipoContrato.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar toda la información.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = controller.guardarInformacionComplementaria(solicitudId, fechaExamen, fechaEntrevista, sueldo, tipoContrato);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Información complementaria guardada exitosamente.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarSolicitudesAsignadas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la información complementaria.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarResultados() {
        // Esta funcionalidad se implementará más adelante
        JOptionPane.showMessageDialog(this, "Funcionalidad aún no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}