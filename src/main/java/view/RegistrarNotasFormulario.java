package view;

import controller.ResultadosEvaluacionesController;

import javax.swing.*;
import java.awt.*;

public class RegistrarNotasFormulario extends JDialog {
    private JTextField txtNotaPsicologica;
    private JTextField txtNotaConocimiento;
    private JTextField txtNotaEntrevista;
    private JButton btnGuardar;
    private boolean formularioCompleto;
    private int idPostulacion;
    private ResultadosEvaluacionesController resultadosEvaluacionesController;

    public RegistrarNotasFormulario(Frame parent, boolean modal, int idPostulacion) {
        super(parent, modal);
        setTitle("Registrar Notas de Evaluación");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.idPostulacion = idPostulacion;
        resultadosEvaluacionesController = new ResultadosEvaluacionesController();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nota Psicológica:"), gbc);

        txtNotaPsicologica = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNotaPsicologica, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nota de Conocimiento:"), gbc);

        txtNotaConocimiento = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNotaConocimiento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Nota de Entrevista:"), gbc);

        txtNotaEntrevista = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNotaEntrevista, gbc);

        btnGuardar = new JButton("Guardar");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnGuardar, gbc);

        add(panel);

        btnGuardar.addActionListener(e -> guardarNotas());

        formularioCompleto = false;
    }

    private void guardarNotas() {
        try {
            double notaPsicologica = Double.parseDouble(txtNotaPsicologica.getText().trim());
            double notaConocimiento = Double.parseDouble(txtNotaConocimiento.getText().trim());
            double notaEntrevista = Double.parseDouble(txtNotaEntrevista.getText().trim());

            boolean exito = resultadosEvaluacionesController.registrarNotas(idPostulacion, notaPsicologica, notaConocimiento, notaEntrevista);

            if (exito) {
                formularioCompleto = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar las notas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para las notas.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isFormularioCompleto() {
        return formularioCompleto;
    }
}