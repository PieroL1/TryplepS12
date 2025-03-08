package view;

import controller.SolicitudController;
import model.SolicitudPersonal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormConsultarSolicitud extends JFrame {
    private JTextField txtBuscarId;
    private JTable tblSolicitudes;
    private DefaultTableModel modeloTabla;
    private SolicitudController controller;

    public FormConsultarSolicitud() {
        controller = new SolicitudController();

        setTitle("Consultar Solicitudes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Solicitud"));

        JLabel lblBuscarId = new JLabel("ID Solicitud:");
        txtBuscarId = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");

        panelBusqueda.add(lblBuscarId);
        panelBusqueda.add(txtBuscarId);
        panelBusqueda.add(btnBuscar);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla de solicitudes
        String[] columnas = {"ID Solicitud", "Nombre Jefe de Proyecto", "Estado", "Fecha Solicitud"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSolicitudes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tblSolicitudes);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Eventos
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSolicitud();
            }
        });

        // Cargar todas las solicitudes al iniciar
        cargarSolicitudes();
    }

    private void cargarSolicitudes() {
        List<SolicitudPersonal> solicitudes = controller.consultarSolicitudes();
        actualizarTabla(solicitudes);
    }

    private void buscarSolicitud() {
        String idStr = txtBuscarId.getText().trim();
        if (idStr.isEmpty()) {
            cargarSolicitudes();
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            SolicitudPersonal solicitud = controller.consultarSolicitudPorId(id);
            if (solicitud != null) {
                actualizarTabla(List.of(solicitud));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna solicitud con el ID especificado", "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla(List.of());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<SolicitudPersonal> solicitudes) {
        modeloTabla.setRowCount(0);
        for (SolicitudPersonal solicitud : solicitudes) {
            Object[] fila = {
                solicitud.getNumeroSolicitud(),
                solicitud.getNombreJefeProyecto(), // Mostrar el nombre del jefe de proyecto
                solicitud.getEstado(),
                solicitud.getFechaRegistro()
            };
            modeloTabla.addRow(fila);
        }
    }
}