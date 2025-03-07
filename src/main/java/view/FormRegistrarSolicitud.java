package view;

import controller.SolicitudController;
import model.Perfil;
import model.SolicitudPersonal;
import model.Usuarios;
import util.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormRegistrarSolicitud extends JFrame {
    private JComboBox<Usuarios> cmbSolicitantes;
    private JTextField txtIdEvaluador;
    private JTextField txtNombreEvaluador;
    private JTextField txtCargoEvaluador;
    private JComboBox<Perfil> cmbPerfiles;
    private JTextField txtCantidad;
    private JTable tblDetalles;
    private DefaultTableModel modeloTabla;
    
    private SolicitudController controller;
    private SolicitudPersonal solicitudActual;
    
    public FormRegistrarSolicitud() {
        controller = new SolicitudController();

        setTitle("Registrar Solicitud de Personal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de datos del solicitante y evaluador
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Solicitud"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Datos del Solicitante (Jefe de Proyecto)
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("DATOS DEL SOLICITANTE:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Seleccionar Jefe de Proyecto:"), gbc);

        cmbSolicitantes = new JComboBox<>();
        List<Usuarios> jefesProyecto = controller.obtenerJefesDeProyecto();
        for (Usuarios jefe : jefesProyecto) {
            cmbSolicitantes.addItem(jefe);
        }
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(cmbSolicitantes, gbc);

        // Datos del Evaluador (Jefe de Sistemas)
        Usuarios evaluador = controller.obtenerEvaluador("jefe_sistemas");

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(new JLabel("DATOS DEL EVALUADOR:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(new JLabel("ID:"), gbc);

        txtIdEvaluador = new JTextField(10);
        txtIdEvaluador.setEditable(false);
        txtIdEvaluador.setText(evaluador != null ? String.valueOf(evaluador.getId()) : "");
        gbc.gridx = 1;
        panelDatos.add(txtIdEvaluador, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Nombre:"), gbc);

        txtNombreEvaluador = new JTextField(20);
        txtNombreEvaluador.setEditable(false);
        txtNombreEvaluador.setText(evaluador != null ? evaluador.getNombre() : "");
        gbc.gridx = 3;
        panelDatos.add(txtNombreEvaluador, gbc);

        gbc.gridx = 4;
        panelDatos.add(new JLabel("Cargo:"), gbc);

        txtCargoEvaluador = new JTextField(15);
        txtCargoEvaluador.setEditable(false);
        txtCargoEvaluador.setText(evaluador != null ? evaluador.getCargo() : "");
        gbc.gridx = 5;
        panelDatos.add(txtCargoEvaluador, gbc);

        // Panel de selección de perfiles
        JPanel panelPerfiles = new JPanel(new GridBagLayout());
        panelPerfiles.setBorder(BorderFactory.createTitledBorder("Datos de los Perfiles"));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPerfiles.add(new JLabel("Perfil:"), gbc);

        // Combo de perfiles
        cmbPerfiles = new JComboBox<>();
        List<Perfil> perfiles = controller.obtenerPerfiles();
        for (Perfil perfil : perfiles) {
            cmbPerfiles.addItem(perfil);
        }
        gbc.gridx = 1;
        panelPerfiles.add(cmbPerfiles, gbc);

        gbc.gridx = 2;
        panelPerfiles.add(new JLabel("Cantidad:"), gbc);

        txtCantidad = new JTextField(5);
        gbc.gridx = 3;
        panelPerfiles.add(txtCantidad, gbc);

        JButton btnAgregarPerfil = new JButton("Agregar Perfil");
        gbc.gridx = 4;
        panelPerfiles.add(btnAgregarPerfil, gbc);

        // Tabla de detalles
        String[] columnas = {"Perfil", "Cantidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDetalles = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tblDetalles);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelPerfiles.add(scrollPane, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCerrar);

        // Añadir los paneles al contenedor principal
        panel.add(panelDatos, BorderLayout.NORTH);
        panel.add(panelPerfiles, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        btnAgregarPerfil.addActionListener(e -> agregarDetalle());
        btnRegistrar.addActionListener(e -> registrarSolicitud());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void agregarDetalle() {
        Perfil perfilSeleccionado = (Perfil) cmbPerfiles.getSelectedItem();
        String cantidadStr = txtCantidad.getText().trim();

        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object[] fila = {perfilSeleccionado.getNombre(), cantidad};
            modeloTabla.addRow(fila);

            txtCantidad.setText("");
            cmbPerfiles.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void registrarSolicitud() {
        Usuarios solicitanteSeleccionado = (Usuarios) cmbSolicitantes.getSelectedItem();

        if (solicitanteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Jefe de Proyecto", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        solicitudActual = controller.iniciarSolicitud(solicitanteSeleccionado.getId());
        String numeroSolicitud = controller.registrarSolicitud();

        if (numeroSolicitud != null) {
            JOptionPane.showMessageDialog(this, "Nro. de Solicitud " + numeroSolicitud, "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la solicitud", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
