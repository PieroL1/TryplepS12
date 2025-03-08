package view;

import controller.PerfilController;
import model.Perfil;
import model.Usuarios;
import util.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormGestionarPerfiles extends JFrame {
    private JTextField txtId;
    private JTextField txtNombre;
    private JTable tblPerfiles;
    private DefaultTableModel modeloTabla;
    private PerfilController controller;

    public FormGestionarPerfiles() {
        controller = new PerfilController();

        // Verificar si el usuario actual es Jefe de Sistemas
        Usuarios usuarioActual = SessionManager.getUsuarioActual();
        if (!SessionManager.esJefeDeProyecto()) {
            JOptionPane.showMessageDialog(this, "Acceso denegado. Solo el Jefe de Sistemas puede acceder a esta funcionalidad.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Gestionar Perfiles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de datos del perfil
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Perfil"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("ID:"), gbc);

        txtId = new JTextField(10);
        txtId.setEditable(false);
        gbc.gridx = 1;
        panelDatos.add(txtId, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 3;
        panelDatos.add(txtNombre, gbc);

        panel.add(panelDatos, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // Tabla de perfiles
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPerfiles = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tblPerfiles);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Cargar los perfiles al iniciar
        cargarPerfiles();

        // Eventos
        btnAgregar.addActionListener(e -> agregarPerfil());
        btnModificar.addActionListener(e -> modificarPerfil());
        btnEliminar.addActionListener(e -> eliminarPerfil());
        btnCerrar.addActionListener(e -> dispose());

        tblPerfiles.getSelectionModel().addListSelectionListener(e -> seleccionarPerfil());
    }

    private void cargarPerfiles() {
        List<Perfil> perfiles = controller.listarPerfiles();
        actualizarTabla(perfiles);
    }

    private void actualizarTabla(List<Perfil> perfiles) {
        modeloTabla.setRowCount(0);
        for (Perfil perfil : perfiles) {
            Object[] fila = {perfil.getId(), perfil.getNombre()};
            modeloTabla.addRow(fila);
        }
    }

    private void agregarPerfil() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre para el perfil", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Perfil perfil = new Perfil();
        perfil.setNombre(nombre);
        controller.agregarPerfil(perfil);
        cargarPerfiles();
        limpiarCampos();
    }

    private void modificarPerfil() {
        int filaSeleccionada = tblPerfiles.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un perfil para modificar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre para el perfil", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Perfil perfil = new Perfil();
        perfil.setId(id);
        perfil.setNombre(nombre);
        controller.modificarPerfil(perfil);
        cargarPerfiles();
        limpiarCampos();
    }

    private void eliminarPerfil() {
        int filaSeleccionada = tblPerfiles.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un perfil para eliminar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        controller.eliminarPerfil(id);
        cargarPerfiles();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        tblPerfiles.clearSelection();
    }

    private void seleccionarPerfil() {
        int filaSeleccionada = tblPerfiles.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            txtId.setText(String.valueOf(id));
            txtNombre.setText(nombre);
        }
    }
}