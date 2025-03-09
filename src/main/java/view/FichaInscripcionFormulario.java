package view;

import javax.swing.*;
import java.awt.*;
import controller.PostulacionController;
import model.Postulante;

public class FichaInscripcionFormulario extends JDialog {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextArea txtDireccion;
    private JTextArea txtExperiencia;
    private JButton btnGuardar;
    private boolean formularioCompleto;
    private int solicitudId;
    private PostulacionController postulacionController;
    private Postulante postulante;

    public FichaInscripcionFormulario(Frame parent, boolean modal, int solicitudId, Postulante postulante) {
        super(parent, modal);
        setTitle("Ficha de Inscripción");
        setSize(500, 600);
        setLocationRelativeTo(parent);

        this.solicitudId = solicitudId;
        this.postulante = postulante;
        postulacionController = new PostulacionController();

        // Crear el formulario
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        txtNombre.setText(postulante.getNombre());
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);

        txtApellido = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField(20);
        txtEmail.setText(postulante.getEmail());
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Teléfono:"), gbc);

        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtTelefono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Dirección:"), gbc);

        txtDireccion = new JTextArea(3, 20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setWrapStyleWord(true);
        JScrollPane scrollDireccion = new JScrollPane(txtDireccion);
        gbc.gridx = 1;
        panel.add(scrollDireccion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Experiencia:"), gbc);

        txtExperiencia = new JTextArea(5, 20);
        txtExperiencia.setLineWrap(true);
        txtExperiencia.setWrapStyleWord(true);
        JScrollPane scrollExperiencia = new JScrollPane(txtExperiencia);
        gbc.gridx = 1;
        panel.add(scrollExperiencia, gbc);

        btnGuardar = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnGuardar, gbc);

        add(panel);

        // Evento de guardar
        btnGuardar.addActionListener(e -> guardarFichaInscripcion());

        formularioCompleto = false;
    }

    private void guardarFichaInscripcion() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String experiencia = txtExperiencia.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() ||
                direccion.isEmpty() || experiencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar toda la información.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Actualizar la información del postulante en la base de datos
        boolean exito = postulacionController.actualizarPostulante(postulante.getId(), nombre, apellido, email, telefono, direccion, experiencia);

        if (exito) {
            formularioCompleto = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la información del postulante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isFormularioCompleto() {
        return formularioCompleto;
    }

    public int getSolicitudId() {
        return solicitudId;
    }
}