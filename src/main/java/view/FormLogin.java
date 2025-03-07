package view;

import util.SessionManager;
import dao.UsuariosDAO;
import model.Usuarios;
import javax.swing.*;
import java.awt.*;

public class FormLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private UsuariosDAO usuariosDAO;

    public FormLogin() {
        usuariosDAO = new UsuariosDAO();
        
        setTitle("TRYPLEP - Sistema de RRHH");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Logo
        JLabel lblLogo = new JLabel("TRYPLEP S.A.C.");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblLogo, gbc);

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Usuario:"), gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Botón ingresar
        JButton btnIngresar = new JButton("Ingresar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        btnIngresar.addActionListener(e -> validarCredenciales());

        add(panel);
    }

    private void validarCredenciales() {
        String usuarioIngresado = txtUsuario.getText().trim();
        String passwordIngresado = new String(txtPassword.getPassword()).trim();

        if (usuarioIngresado.isEmpty() || passwordIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar en la BD
        Usuarios usuario = usuariosDAO.validarUsuario(usuarioIngresado, passwordIngresado);

        if (usuario != null) {
            SessionManager.setUsuarioActual(usuario);
            abrirMenuPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenuPrincipal() {
        FormMenuPrincipal menuPrincipal = new FormMenuPrincipal();
        menuPrincipal.setVisible(true);
        dispose();
    }
}
