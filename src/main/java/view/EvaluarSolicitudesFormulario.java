package view;

import controller.SolicitudController;
import model.Solicitud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EvaluarSolicitudesFormulario extends JDialog {
    private JTable tblSolicitudes;
    private DefaultTableModel modeloTablaSolicitudes;
    private JButton btnAceptar;
    private JButton btnRechazar;
    private SolicitudController solicitudesController;

    public EvaluarSolicitudesFormulario(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Evaluar Solicitudes");
        setSize(800, 600);
        setLocationRelativeTo(parent);

        solicitudesController = new SolicitudController();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de solicitudes
        JPanel panelSolicitudes = new JPanel(new BorderLayout());
        panelSolicitudes.setBorder(BorderFactory.createTitledBorder("Solicitudes por Revisar"));

        String[] columnasSolicitudes = {"ID", "Fecha Solicitud", "Perfil", "Estado"};
        modeloTablaSolicitudes = new DefaultTableModel(columnasSolicitudes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSolicitudes = new JTable(modeloTablaSolicitudes);
        JScrollPane scrollSolicitudes = new JScrollPane(tblSolicitudes);
        panelSolicitudes.add(scrollSolicitudes, BorderLayout.CENTER);

        panel.add(panelSolicitudes, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAceptar = new JButton("Aceptar");
        btnRechazar = new JButton("Rechazar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnRechazar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // Cargar las solicitudes al iniciar
        cargarSolicitudesPorRevisar();

        // Eventos
        btnAceptar.addActionListener(e -> aceptarSolicitud());
        btnRechazar.addActionListener(e -> rechazarSolicitud());
    }

    private void cargarSolicitudesPorRevisar() {
        List<Solicitud> solicitudes = solicitudesController.obtenerSolicitudesPorRevisar();
        actualizarTablaSolicitudes(solicitudes);
    }

    private void actualizarTablaSolicitudes(List<Solicitud> solicitudes) {
        modeloTablaSolicitudes.setRowCount(0);
        for (Solicitud solicitud : solicitudes) {
            Object[] fila = {solicitud.getId(), solicitud.getFechaSolicitud(), solicitud.getPerfil(), solicitud.getEstado()};
            modeloTablaSolicitudes.addRow(fila);
        }
    }

    private void aceptarSolicitud() {
        int filaSeleccionada = tblSolicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud para aceptar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idSolicitud = (int) modeloTablaSolicitudes.getValueAt(filaSeleccionada, 0);
        boolean exito = solicitudesController.cambiarEstadoSolicitud(idSolicitud, "pendiente");
        if (exito) {
            JOptionPane.showMessageDialog(this, "Solicitud aceptada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarSolicitudesPorRevisar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al aceptar la solicitud.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechazarSolicitud() {
        int filaSeleccionada = tblSolicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud para rechazar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idSolicitud = (int) modeloTablaSolicitudes.getValueAt(filaSeleccionada, 0);
        boolean exito = solicitudesController.cambiarEstadoSolicitud(idSolicitud, "rechazado");
        if (exito) {
            JOptionPane.showMessageDialog(this, "Solicitud rechazada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarSolicitudesPorRevisar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al rechazar la solicitud.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}