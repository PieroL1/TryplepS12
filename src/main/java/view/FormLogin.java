
package view;

import util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    
    public FormLogin() {
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
        
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                // En un sistema real validaríamos credenciales
                if (usuario.equals("E001")) {
                    SessionManager.iniciarSesion(usuario);
                    abrirMenuPrincipal();
                } else {
                    JOptionPane.showMessageDialog(FormLogin.this, 
                            "Credenciales incorrectas",
                            "Error de autenticación", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        add(panel);
    }
    
    private void abrirMenuPrincipal() {
        FormMenuPrincipal menuPrincipal = new FormMenuPrincipal();
        menuPrincipal.setVisible(true);
        dispose();
    }
}