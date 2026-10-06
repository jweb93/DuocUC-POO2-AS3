package vista;

import controlador.ControladorEntregas;
import controlador.ControladorPedidos;
import controlador.ControladorRepartidores;

import javax.swing.*;
import java.awt.*;
/**
 * Ventana principal de SpeedFast.
 * Proporciona acceso a las interfaces de gestión de pedidos,
 * repartidores y entregas.
 */
public class VentanaPrincipal extends JFrame {
    private JButton btnGestionarRepartidores;
    private JButton btnGestionarPedidos;
    private JButton btnGestionarEntregas;
    private ControladorRepartidores controladorRepartidores;
    private ControladorPedidos controladorPedidos;
    private ControladorEntregas controladorEntregas;

    public VentanaPrincipal(ControladorPedidos controladorPedidos, ControladorRepartidores controladorRepartidores, ControladorEntregas controladorEntregas){
        this.controladorPedidos = controladorPedidos;
        this.controladorRepartidores = controladorRepartidores;
        this.controladorEntregas = controladorEntregas;

        // Configuración base de la ventana
        setTitle("SpeedFast");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // En su interior tendrá un panel central con 3 botones
        setLayout(new BorderLayout(10, 10));

        // El panel de botones tendrá una estructura de 3 filas y 1 columna
        JPanel panelBotones = new JPanel(new GridLayout(3, 1));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        btnGestionarRepartidores = new JButton("Gestión de Repartidores");
        btnGestionarPedidos = new JButton("Gestión de Pedidos");
        btnGestionarEntregas = new JButton("Gestión de Entregas");

        panelBotones.add(btnGestionarRepartidores);
        panelBotones.add(btnGestionarPedidos);
        panelBotones.add(btnGestionarEntregas);

        add(panelBotones, BorderLayout.CENTER);

        // Acciones de los botones

        btnGestionarRepartidores.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                VentanaGestionRepartidores ventanaGestionRepartidores = new VentanaGestionRepartidores(controladorRepartidores);
                ventanaGestionRepartidores.setLocationRelativeTo(this);
                ventanaGestionRepartidores.setVisible(true);
            });
        });

        btnGestionarPedidos.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                VentanaGestionPedidos ventanaGestionPedidos = new VentanaGestionPedidos(controladorPedidos);
                ventanaGestionPedidos.setLocationRelativeTo(this);
                ventanaGestionPedidos.setVisible(true);
            });
        });

        btnGestionarEntregas.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                VentanaGestionEntregas ventanaGestionEntregas = new VentanaGestionEntregas(controladorEntregas, controladorPedidos, controladorRepartidores);
                ventanaGestionEntregas.setLocationRelativeTo(this);
                ventanaGestionEntregas.setVisible(true);
            });
        });

    }

}

