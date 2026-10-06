package vista;

import controlador.ControladorRepartidores;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Ventana gráfica destinada a la gestión de repartidores.
 * Permite consultar, agregar, editar y eliminar repartidores.
 */
public class VentanaGestionRepartidores extends JFrame{
    private JPanel panelRepartidores;
    private JTextField txtNombre;
    private JButton btnEditar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tblRepartidores;
    private JLabel lblNombre;
    private JScrollPane jspTabla;
    private JLabel lblSubtitulo;

    private ControladorRepartidores controladorRepartidores;
    private DefaultTableModel modeloTabla;
    private int registroSeleccionado = -1; // Índice del registro seleccionado en tabla

    public VentanaGestionRepartidores(ControladorRepartidores controladorRepartidores){
        this.controladorRepartidores = controladorRepartidores;

        // Configuración base de la ventana
        setContentPane(panelRepartidores);
        setTitle("Gestión de Repartidores - SpeedFast");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración de tabla
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas,0){
                @Override
                public boolean isCellEditable(int row, int column){
                    return false; // Las celdas no son editables
                }
        };

        tblRepartidores.setModel(modeloTabla);
        tblRepartidores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblRepartidores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo se puede seleccionar 1 fila

        tblRepartidores.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int fila = tblRepartidores.getSelectedRow();
                if (fila >= 0){
                    registroSeleccionado = fila;
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                }
            }
        });

        // Configuración de los botones
        btnEditar.addActionListener(e -> editarRepartidor());
        btnAgregar.addActionListener(e -> agregarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
        btnLimpiar.addActionListener(e -> limpiar());

        // Poblamiento de la tabla
        actualizarTabla();
    }

    /**
     * Valida los datos ingresados y actualiza el repartidor seleccionado.
     */
    public void editarRepartidor(){
        // Validar que exista selección de registro
        if(registroSeleccionado == -1){
            alertarInconsistencia("Selecciona un registro para editar.");
            return;
        }

        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        int id = Integer.parseInt(modeloTabla.getValueAt(registroSeleccionado, 0).toString());
        String nombre = txtNombre.getText().trim();

//        // Validar cambios
//        String nombreOriginal = modeloTabla.getValueAt(registroSeleccionado, 1).toString();
//        if (nombreOriginal.equals(nombre)) {
//            alertarInconsistencia("No se han realizado cambios en el registro.");
//            return;
//        }

        // Actualizar e informar resultado
        String resultado = controladorRepartidores.actualizarRepartidor(new Repartidor(id, nombre));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Valida los datos ingresados y registra un nuevo repartidor.
     */
    public void agregarRepartidor(){
        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        String nombre = txtNombre.getText().trim();

        String resultado = controladorRepartidores.agregarNuevoRepartidor(new Repartidor(nombre));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Solicita confirmación y elimina el repartidor seleccionado.
     */
    public void eliminarRepartidor(){
        int fila = tblRepartidores.getSelectedRow();

        // Validar que exista selección de registro
        if(registroSeleccionado == -1){
            alertarInconsistencia("Selecciona un registro para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar este registro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            String resultado = controladorRepartidores.eliminaRepartidor(new Repartidor(id));
            informarResultado(resultado);

            limpiar();
            actualizarTabla();
        }
    }

    /**
     * Actualiza la tabla con los repartidores registrados en la base de datos.
     */
    public void actualizarTabla(){
        // Primero borramos los registros cargados
        modeloTabla.setRowCount(0);

        // Luego se cargan los registros
        for(Repartidor repartidor : controladorRepartidores.getRepartidores()){
            modeloTabla.addRow(new Object[]{
                    repartidor.getId(),
                    repartidor.getNombre()
            });

        }
    }

    /**
     * Verifica que los datos ingresados para el repartidor sean válidos.
     *
     * @return true si los datos son válidos; false en caso contrario
     */
    public boolean validarCampos(){
        // Validación de nombre no vacío
        if (txtNombre.getText().trim().isEmpty()){
            alertarInconsistencia("Debe completar el nombre del repartidor");
            return false;
        }
        return true;
    }

    /**
     * Restablece el formulario y elimina la selección actual de la tabla.
     */
    public void limpiar(){
        txtNombre.setText("");
        registroSeleccionado = -1;
        tblRepartidores.clearSelection();
        actualizarTabla();
    }

    /**
     * Muestra al usuario una advertencia asociada a una inconsistencia
     * detectada en los datos o en la operación solicitada.
     *
     * @param alerta mensaje que se desea mostrar
     */
    public void alertarInconsistencia(String alerta) {
        JOptionPane.showMessageDialog(
                this,
                alerta,
                "Alerta",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Muestra al usuario un mensaje con el resultado de una operación.
     *
     * @param mensaje mensaje que se desea mostrar
     */
    private void informarResultado(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
