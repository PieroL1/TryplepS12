package view;

import util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormMenuPrincipal extends JFrame {
    
    public FormMenuPrincipal() {
        setTitle("TRYPLEP - Sistema de RRHH");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Menú principal
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Solicitudes
        JMenu menuSolicitudes = new JMenu("Solicitudes");
        JMenuItem itemRegistrarSolicitud = new JMenuItem("Registrar Solicitud");
        JMenuItem itemConsultarSolicitud = new JMenuItem("Consultar Solicitudes");
        
        menuSolicitudes.add(itemRegistrarSolicitud);
        menuSolicitudes.add(itemConsultarSolicitud);
        
        // Menú Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSalir = new JMenuItem("Salir");
        
        menuSistema.add(itemSalir);
        
        menuBar.add(menuSolicitudes);
        menuBar.add(menuSistema);
        
        setJMenuBar(menuBar);
        
        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de RRHH de TRYPLEP S.A.C.");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenida.setHorizontalAlignment(JLabel.CENTER);
        
        panelCentral.add(lblBienvenida, BorderLayout.CENTER);
        
        add(panelCentral);
        
        // Action Listeners
        itemRegistrarSolicitud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormRegistrarSolicitud formSolicitud = new FormRegistrarSolicitud();
                formSolicitud.setVisible(true);
            }
        });
        
        itemConsultarSolicitud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormConsultarSolicitud formConsultarSolicitud = new FormConsultarSolicitud();
                formConsultarSolicitud.setVisible(true);
            }
        });
        
        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.cerrarSesion();
                System.exit(0);
            }
        });
    }
}