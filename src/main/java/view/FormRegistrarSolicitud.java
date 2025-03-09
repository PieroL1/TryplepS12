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
    private JTextField txtIdSolicitante;
    private JTextField txtNombreSolicitante;
    private JTextField txtCargoSolicitante;
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

        // Verificar si el usuario actual es Jefe de Proyecto
        Usuarios usuarioActual = (Usuarios) SessionManager.getUsuarioActual();
        if (!SessionManager.esJefeDeProyecto()) {
            JOptionPane.showMessageDialog(this, "Acceso denegado. Solo el Jefe de Proyecto puede acceder a esta funcionalidad.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Registrar Solicitud de Personal");
        setSize(800, 800);
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

        // PRIMERO: DATOS DEL EVALUADOR (Jefe de Proyecto)
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("DATOS DEL EVALUADOR:"), gbc);

        // Obtener un usuario de tipo Jefe de Proyecto
        Usuarios evaluador = controller.obtenerEvaluador("jefe_proyecto");

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Nro. Registro:"), gbc);

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
        txtCargoEvaluador.setText("Jefe de Proyecto"); // Texto fijo para el cargo
        gbc.gridx = 5;
        panelDatos.add(txtCargoEvaluador, gbc);

        // SEGUNDO: DATOS DEL SOLICITANTE (Jefe de Sistemas)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelDatos.add(new JLabel("DATOS DEL SOLICITANTE:"), gbc);

        // Obtener un usuario de tipo Jefe de Sistemas
        Usuarios solicitante = controller.obtenerEvaluador("jefe_sistemas");

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(new JLabel("Nro. Registro:"), gbc);

        txtIdSolicitante = new JTextField(10);
        txtIdSolicitante.setEditable(false);
        txtIdSolicitante.setText(solicitante != null ? String.valueOf(solicitante.getId()) : "");
        gbc.gridx = 1;
        panelDatos.add(txtIdSolicitante, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Nombre:"), gbc);

        txtNombreSolicitante = new JTextField(20);
        txtNombreSolicitante.setEditable(false);
        txtNombreSolicitante.setText(solicitante != null ? solicitante.getNombre() : "");
        gbc.gridx = 3;
        panelDatos.add(txtNombreSolicitante, gbc);

        gbc.gridx = 4;
        panelDatos.add(new JLabel("Cargo:"), gbc);

        txtCargoSolicitante = new JTextField(15);
        txtCargoSolicitante.setEditable(false);
        txtCargoSolicitante.setText("Jefe de Sistemas"); // Texto fijo para el cargo
        gbc.gridx = 5;
        panelDatos.add(txtCargoSolicitante, gbc);

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

        // Inicializar la solicitud actual al abrir el formulario
        inicializarSolicitud();

        // Eventos
        btnAgregarPerfil.addActionListener(e -> agregarDetalle());
        btnRegistrar.addActionListener(e -> registrarSolicitud());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void inicializarSolicitud() {
        int idSolicitante = Integer.parseInt(txtIdSolicitante.getText().trim());
        solicitudActual = controller.iniciarSolicitud(idSolicitante);
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

            // Agregar el detalle a la solicitud actual
            controller.agregarDetalle(perfilSeleccionado, cantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void registrarSolicitud() {
        if (solicitudActual == null) {
            inicializarSolicitud();
        }

        String numeroSolicitud = controller.registrarSolicitud();
        
        if (numeroSolicitud != null) {
            JOptionPane.showMessageDialog(this, "Nro. de Solicitud " + numeroSolicitud, "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la solicitud", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}