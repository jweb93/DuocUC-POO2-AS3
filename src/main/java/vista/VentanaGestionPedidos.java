package vista;

import controlador.ControladorPedidos;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Ventana gráfica destinada a la gestión de pedidos.
 * Permite consultar, agregar, editar y eliminar pedidos registrados
 * en la base de datos.
 */
public class VentanaGestionPedidos extends JFrame{
    private JPanel panelPedidos;
    private JTextField txtComuna;
    private JTextField txtNumero;
    private JTextField txtCalle;
    private JComboBox<TipoPedido> cmbTipo;
    private JComboBox<EstadoPedido> cmbEstado;
    private JButton btnEditar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tblPedidos;
    private JLabel lblCalle;
    private JLabel lblNumero;
    private JLabel lblComuna;
    private JLabel lblEstado;
    private JLabel lblSubtitulo;
    private JLabel lblTipo;
    private JComboBox<TipoPedido> cmbFiltroTipo;
    private JComboBox<EstadoPedido> cmbFiltroEstado;
    private JButton btnFiltrar;
    private JLabel lblFiltro;

    private ControladorPedidos controladorPedidos;
    private DefaultTableModel modeloTabla;
    private int registroSeleccionado = -1; // Índice del registro seleccionado en tabla

    public VentanaGestionPedidos(ControladorPedidos controladorPedidos){
        this.controladorPedidos = controladorPedidos;

        // Configuración base de la ventana
        setContentPane(panelPedidos);
        setTitle("Gestión de Pedidos - SpeedFast");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Selectores
        for(TipoPedido tipoPedido: TipoPedido.values()){
            cmbTipo.addItem(tipoPedido);
            cmbFiltroTipo.addItem(tipoPedido);
        }
        for(EstadoPedido estadoPedido: EstadoPedido.values()){
            cmbEstado.addItem(estadoPedido);
            cmbFiltroEstado.addItem(estadoPedido);
        }

        // Configuración de tabla
        String[] columnas = {"ID", "Calle", "Numero", "Comuna", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false; // Las celdas no son editables
            }
        };

        tblPedidos.setModel(modeloTabla);
        tblPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo se puede seleccionar 1 fila

        tblPedidos.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int fila = tblPedidos.getSelectedRow();
                if (fila >= 0){
                    registroSeleccionado = fila;

                    txtCalle.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtNumero.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtComuna.setText(modeloTabla.getValueAt(fila, 3).toString());
                    cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 4));
                    cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 5));
                }
            }
        });

        // Configuración de los botones
        btnEditar.addActionListener(e -> editarPedido());
        btnAgregar.addActionListener(e -> agregarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
        btnLimpiar.addActionListener(e -> limpiar());
        btnFiltrar.addActionListener(e -> filtrarTabla());

        // Poblamiento de la tabla
        actualizarTabla();
        limpiar();
    }

    /**
     * Valida los datos ingresados y actualiza el pedido actualmente
     * seleccionado en la tabla.
     */
    public void editarPedido(){
        // Validar que exista selección de registro
        if(registroSeleccionado == -1){
            alertarInconsistencia("Selecciona un registro para editar.");
            return;
        }

        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        int id = Integer.parseInt(modeloTabla.getValueAt(registroSeleccionado, 0).toString());
        String calle = txtCalle.getText().trim();
        int numero = Integer.parseInt(txtNumero.getText().trim());
        String comuna = txtComuna.getText().trim();
        Direccion direccion = new Direccion(calle, numero, comuna);
        TipoPedido tipoPedido = (TipoPedido) cmbTipo.getSelectedItem();
        EstadoPedido estadoPedido = (EstadoPedido) cmbEstado.getSelectedItem();

        // Actualizar e informar resultado
        String resultado = controladorPedidos.actualizarPedido(new Pedido(id, direccion, tipoPedido, estadoPedido));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Valida los datos del formulario y registra un nuevo pedido.
     */
    public void agregarPedido(){
        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        String calle = txtCalle.getText().trim();
        int numero = Integer.parseInt(txtNumero.getText().trim());
        String comuna = txtComuna.getText().trim();
        Direccion direccion = new Direccion(calle, numero, comuna);
        TipoPedido tipoPedido = (TipoPedido) cmbTipo.getSelectedItem();
        EstadoPedido estadoPedido = (EstadoPedido) cmbEstado.getSelectedItem();

        String resultado = controladorPedidos.agregarNuevoPedido(new Pedido(direccion, tipoPedido, estadoPedido));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Solicita confirmación al usuario y elimina el pedido seleccionado.
     */
    public void eliminarPedido(){
        int fila = tblPedidos.getSelectedRow();

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
            String resultado = controladorPedidos.eliminaPedido(new Pedido(id));
            informarResultado(resultado);

            limpiar();
            actualizarTabla();
        }
    }

    /**
     * Actualiza el contenido de la tabla utilizando los pedidos actualmente
     * almacenados en la base de datos.
     */
    public void actualizarTabla(){
        // Primero borramos los registros cargados
        modeloTabla.setRowCount(0);

        // Luego se cargan los registros
        for(Pedido pedido : controladorPedidos.getPedidos()){
            modeloTabla.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getDireccionEntrega().getCalle(),
                    pedido.getDireccionEntrega().getNumero(),
                    pedido.getDireccionEntrega().getComuna(),
                    pedido.getTipoPedido(),
                    pedido.getEstado()
            });

        }
    }

    /**
     * Verifica que los campos necesarios para registrar o modificar un pedido
     * contengan valores válidos.
     *
     * @return true si todos los campos son válidos; false en caso contrario
     */
    public boolean validarCampos(){
        // Validación de calle no vacía
        if (txtCalle.getText().trim().isEmpty()){
            alertarInconsistencia("Debe completar la calle del pedido");
            return false;
        }

        // Validación de numero no vacío, entero y positivo
        if (txtNumero.getText().trim().isEmpty()){
            alertarInconsistencia("Debe completar el numero de la dirección del pedido");
            return false;
        } else {
            try{
                int valor = Integer.parseInt(txtNumero.getText().trim());
                if (valor <= 0){
                    alertarInconsistencia("El numero de la dirección del pedido debe ser un número entero positivo.");
                    return false;
                }
            }catch (NumberFormatException e){
                alertarInconsistencia("El numero de la dirección del pedido debe ser un número entero positivo.");
                return false;
            }
        }

        // Validación de comuna no vacía
        if (txtComuna.getText().trim().isEmpty()){
            alertarInconsistencia("Debe completar la comuna de la dirección del pedido");
            return false;
        }

        // Validación de tipo seleccionado
        if (cmbTipo.getSelectedIndex() == -1){
            alertarInconsistencia("Debe completar el tipo del pedido");
            return false;
        }

        // Validación de estado seleccionado
        if (cmbEstado.getSelectedIndex() == -1){
            alertarInconsistencia("Debe completar el estado del pedido");
            return false;
        }

        return true;
    }

    /**
     * Restablece los campos del formulario y elimina la selección actual
     * de la tabla.
     */
    public void limpiar(){
        txtCalle.setText("");
        txtNumero.setText("");
        txtComuna.setText("");
        cmbTipo.setSelectedIndex(-1);
        cmbEstado.setSelectedIndex(-1);
        cmbFiltroTipo.setSelectedIndex(-1);
        cmbFiltroEstado.setSelectedIndex(-1);
        registroSeleccionado = -1;
        tblPedidos.clearSelection();
        actualizarTabla();
    }

    /**
     * Método que ofrece la posibilidad de filtrar la tabla por Tipo Pedido o Estado (se solicita en la pauta 😞)
     */
    public void filtrarTabla(){
        TipoPedido filtroTipo = (TipoPedido) cmbFiltroTipo.getSelectedItem();

        EstadoPedido filtroEstado = (EstadoPedido) cmbFiltroEstado.getSelectedItem();

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controladorPedidos.getPedidos()) {

            boolean cumpleTipo = filtroTipo == null || pedido.getTipoPedido() == filtroTipo;

            boolean cumpleEstado = filtroEstado == null || pedido.getEstado() == filtroEstado;

            if (cumpleTipo && cumpleEstado) {

                modeloTabla.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getDireccionEntrega().getCalle(),
                        pedido.getDireccionEntrega().getNumero(),
                        pedido.getDireccionEntrega().getComuna(),
                        pedido.getTipoPedido(),
                        pedido.getEstado()
                });
            }
        }
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
