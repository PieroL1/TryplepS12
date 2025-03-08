package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class FormLogin extends JFrame {
    private JTextField txtNombre;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private LoginController loginController;

    public FormLogin() {
        loginController = new LoginController();

        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el formulario
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        add(panel);

        // Evento de login
        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String nombre = txtNombre.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (loginController.iniciarSesion(nombre, password)) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nombre o Password incorrectos", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormLogin formLogin = new FormLogin();
            formLogin.setVisible(true);
        });
    }
}