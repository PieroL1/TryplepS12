package view;

import controller.SolicitudController;
import controller.PostulacionController;
import model.SolicitudPerfil;
import model.Postulante;
import util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class PortalPostulante extends JFrame {
    private JTable tblSolicitudes;
    private DefaultTableModel modeloTablaSolicitudes;
    private JButton btnPostular;
    private SolicitudController solicitudController;
    private PostulacionController postulacionController;
    private List<Integer> solicitudIds;

    public PortalPostulante() {
        solicitudController = new SolicitudController();
        postulacionController = new PostulacionController();
        solicitudIds = new ArrayList<>();

        setTitle("Portal Postulante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de solicitudes
        JPanel panelSolicitudes = new JPanel(new BorderLayout());
        panelSolicitudes.setBorder(BorderFactory.createTitledBorder("Solicitudes Disponibles"));

        String[] columnasSolicitudes = {"Fecha Solicitud", "Perfil", "Cantidad", "Fecha Examen", "Fecha Entrevista"};
        modeloTablaSolicitudes = new DefaultTableModel(columnasSolicitudes, 0);
        tblSolicitudes = new JTable(modeloTablaSolicitudes);
        JScrollPane scrollSolicitudes = new JScrollPane(tblSolicitudes);
        panelSolicitudes.add(scrollSolicitudes, BorderLayout.CENTER);

        panel.add(panelSolicitudes, BorderLayout.CENTER);

        btnPostular = new JButton("Postular");
        panel.add(btnPostular, BorderLayout.SOUTH);

        add(panel);

        // Cargar las solicitudes disponibles al iniciar
        cargarSolicitudesDisponibles();

        // Eventos
        btnPostular.addActionListener(e -> postular());
    }

    private void cargarSolicitudesDisponibles() {
        List<SolicitudPerfil> solicitudesPerfiles = solicitudController.obtenerSolicitudesConInformacionComplementaria();
        actualizarTablaSolicitudes(solicitudesPerfiles);
    }

    private void actualizarTablaSolicitudes(List<SolicitudPerfil> solicitudesPerfiles) {
        modeloTablaSolicitudes.setRowCount(0);
        solicitudIds.clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (SolicitudPerfil solicitudPerfil : solicitudesPerfiles) {
            String fechaSolicitud = dateFormat.format(solicitudPerfil.getFechaSolicitud());
            String fechaExamen = solicitudPerfil.getFechaExamen() != null ? dateFormat.format(solicitudPerfil.getFechaExamen()) : "";
            String fechaEntrevista = solicitudPerfil.getFechaEntrevista() != null ? dateFormat.format(solicitudPerfil.getFechaEntrevista()) : "";
            Object[] fila = {fechaSolicitud, solicitudPerfil.getNombrePerfil(), solicitudPerfil.getCantidad(), fechaExamen, fechaEntrevista};
            modeloTablaSolicitudes.addRow(fila);
            solicitudIds.add(solicitudPerfil.getId());
        }
    }

    private void postular() {
        int filaSeleccionada = tblSolicitudes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud para postular.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int solicitudId = solicitudIds.get(filaSeleccionada);

        // Mostrar formulario de inscripción
        Postulante postulante = (Postulante) SessionManager.getUsuarioActual();
        FichaInscripcionFormulario fichaInscripcionFormulario = new FichaInscripcionFormulario(this, true, solicitudId, postulante);
        fichaInscripcionFormulario.setVisible(true);

        // Si se completó el formulario correctamente, registrar la postulación
        if (fichaInscripcionFormulario.isFormularioCompleto()) {
            System.out.println("Formulario de inscripción completado. Registrando postulación...");
            boolean exito = postulacionController.registrarPostulacion(postulante.getId(), solicitudId);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Postulación registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarSolicitudesDisponibles();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la postulación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}