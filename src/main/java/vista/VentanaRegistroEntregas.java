package vista;

import controlador.ControladorEntregas;
import controlador.ControladorPedidos;
import controlador.ControladorRepartidores;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Ventana gráfica destinada a la gestión de entregas.
 * Permite consultar, registrar, editar y eliminar entregas, asociando
 * pedidos y repartidores con una fecha y hora programadas.
 */
public class VentanaRegistroEntregas extends JFrame {
    private JPanel panelEntregas;
    private JButton btnEditar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tblEntregas;
    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;
    private JComboBox<Integer> cmbDia;
    private JComboBox<Mes> cmbMes;
    private JComboBox<Integer> cmbAnio;
    private JComboBox<Integer> cmbHora;
    private JComboBox<Integer> cmbMinutos;
    private JLabel lblSeparadorHora;
    private JLabel lblPedido;
    private JLabel lblRepartidor;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JLabel lblSubtitulo;

    private ControladorEntregas controladorEntregas;
    private ControladorPedidos controladorPedidos;
    private ControladorRepartidores controladorRepartidores;

    private DefaultTableModel modeloTabla;
    private int registroSeleccionado = -1; // Índice del registro seleccionado en tabla

    public VentanaRegistroEntregas(ControladorEntregas controladorEntregas, ControladorPedidos controladorPedidos, ControladorRepartidores controladorRepartidores){
        this.controladorEntregas = controladorEntregas;
        this.controladorPedidos = controladorPedidos;
        this.controladorRepartidores = controladorRepartidores;

        // Configuración base de la ventana
        setContentPane(panelEntregas);
        setTitle("Gestión de Entregas - SpeedFast");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Selectores
        completaSelectoresFijos();
        completaSelectoresVariables();

        // Configuración de tabla
        String[] columnas = {"ID", "Pedido", "Repartidor", "Fecha", "Hora"};
        modeloTabla = new DefaultTableModel(columnas,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false; // Las celdas no son editables
            }
        };

        tblEntregas.setModel(modeloTabla);
        tblEntregas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEntregas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo se puede seleccionar 1 fila

        tblEntregas.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        tblEntregas.getColumnModel().getColumn(1).setPreferredWidth(350); // Pedido
        tblEntregas.getColumnModel().getColumn(2).setPreferredWidth(200); // Repartidor
        tblEntregas.getColumnModel().getColumn(3).setPreferredWidth(100);  // Fecha
        tblEntregas.getColumnModel().getColumn(4).setPreferredWidth(60);  // Hora

        tblEntregas.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int fila = tblEntregas.getSelectedRow();
                if (fila >= 0){
                    registroSeleccionado = fila;

                    Pedido pedido = (Pedido) modeloTabla.getValueAt(fila, 1);
                    for (int i = 0; i < cmbPedido.getItemCount(); i++){
                        Pedido item = cmbPedido.getItemAt(i);
                        if(item.getId() == pedido.getId()){
                            cmbPedido.setSelectedIndex(i);
                            break;
                        }
                    }

                    Repartidor repartidor = (Repartidor) modeloTabla.getValueAt(fila, 2);
                    for (int i = 0; i < cmbRepartidor.getItemCount(); i++){
                        Repartidor item = cmbRepartidor.getItemAt(i);
                        if(item.getId() == repartidor.getId()){
                            cmbRepartidor.setSelectedIndex(i);
                            break;
                        }
                    }

                    LocalDate fecha = (LocalDate) modeloTabla.getValueAt(fila, 3);
                    cmbDia.setSelectedItem(fecha.getDayOfMonth());
                    for (int i = 0; i < cmbMes.getItemCount(); i++){
                        Mes mes = cmbMes.getItemAt(i);
                        if(mes.getNumero() == fecha.getMonthValue()){
                            cmbMes.setSelectedIndex(i);
                            break;
                        }
                    }
                    cmbAnio.setSelectedItem(fecha.getYear());

                    LocalTime horaEntrega = (LocalTime) modeloTabla.getValueAt(fila, 4);
                    cmbHora.setSelectedItem(horaEntrega.getHour());
                    cmbMinutos.setSelectedItem(horaEntrega.getMinute());
                }
            }
        });

        // Configuración de los botones
        btnEditar.addActionListener(e -> editarEntrega());
        btnAgregar.addActionListener(e -> agregarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnLimpiar.addActionListener(e -> limpiar());

        // Poblamiento de la tabla
        actualizarTabla();
        limpiar();
    }

    /**
     * Valida el formulario y actualiza la entrega seleccionada utilizando
     * el pedido, repartidor, fecha y hora indicados.
     */
    public void editarEntrega(){
        // Validar que exista selección de registro
        if(registroSeleccionado == -1){
            alertarInconsistencia("Selecciona un registro para editar.");
            return;
        }

        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        int id = Integer.parseInt(modeloTabla.getValueAt(registroSeleccionado, 0).toString());
        Pedido pedido = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidor.getSelectedItem();

        int dia = (Integer) cmbDia.getSelectedItem();
        Mes mes = (Mes) cmbMes.getSelectedItem();
        int anio = (Integer) cmbAnio.getSelectedItem();

        int hora = (Integer) cmbHora.getSelectedItem();
        int minuto = (Integer) cmbMinutos.getSelectedItem();

        LocalDate fecha = LocalDate.of(
                anio,
                mes.getNumero(),
                dia
        );

        LocalTime horaEntrega = LocalTime.of(
                hora,
                minuto
        );

        // Actualizar e informar resultado
        String resultado = controladorEntregas.actualizarEntrega(new Entrega(id, pedido, repartidor, fecha, horaEntrega));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Valida los datos del formulario y registra una nueva entrega.
     */
    public void agregarEntrega(){
        // Validar formulario
        if (!validarCampos()) return;

        // Obtener valores
        Pedido pedido = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidor.getSelectedItem();

        int dia = (Integer) cmbDia.getSelectedItem();
        Mes mes = (Mes) cmbMes.getSelectedItem();
        int anio = (Integer) cmbAnio.getSelectedItem();

        int hora = (Integer) cmbHora.getSelectedItem();
        int minuto = (Integer) cmbMinutos.getSelectedItem();

        LocalDate fecha = LocalDate.of(
                anio,
                mes.getNumero(),
                dia
        );

        LocalTime horaEntrega = LocalTime.of(
                hora,
                minuto
        );

        String resultado = controladorEntregas.agregarNuevaEntrega(new Entrega(pedido, repartidor, fecha, horaEntrega));
        informarResultado(resultado);

        limpiar();
        actualizarTabla();
    }

    /**
     * Solicita confirmación al usuario y elimina la entrega seleccionada.
     */
    public void eliminarEntrega(){
        int fila = tblEntregas.getSelectedRow();

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
            String resultado = controladorEntregas.eliminaEntrega(new Entrega(id));

            informarResultado(resultado);

            limpiar();
            actualizarTabla();

        }
    }

    /**
     * Actualiza la tabla de entregas utilizando los registros obtenidos
     * desde la base de datos.
     */
    public void actualizarTabla(){
        // Primero borramos los registros cargados
        modeloTabla.setRowCount(0);

        // Luego se cargan los registros
        for(Entrega entrega : controladorEntregas.getEntregas()){
            modeloTabla.addRow(new Object[]{
                    entrega.getId(),
                    entrega.getPedido(),
                    entrega.getRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()
            });

        }
    }

    /**
     * Valida que se hayan seleccionado todos los datos necesarios para una
     * entrega y comprueba que la combinación de día, mes y año corresponda
     * a una fecha válida.
     *
     * @return true si todos los datos son válidos; false en caso contrario
     */
    public boolean validarCampos(){
        // Validación del pedido seleccionado
        if (cmbPedido.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar un pedido");
            return false;
        }

        // Validación del repartidor seleccionado
        if (cmbRepartidor.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar un repartidor");
            return false;
        }

        // Validación del día seleccionado
        if (cmbDia.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar un día");
            return false;
        }

        // Validación del mes seleccionado
        if (cmbMes.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar un mes");
            return false;
        }

        // Validación del año seleccionado
        if (cmbAnio.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar un Año");
            return false;
        }

        // Validación de la hora seleccionado
        if (cmbHora.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar una hora de entrega");
            return false;
        }

        // Validación del minuto seleccionado
        if (cmbMinutos.getSelectedIndex() == -1){
            alertarInconsistencia("Debe seleccionar los minutos de la hora de entrega");
            return false;
        }

        // Consistencia de la fecha (evitar por ej 31 de febrero)
        int dia = (Integer) cmbDia.getSelectedItem();
        Mes mes = (Mes) cmbMes.getSelectedItem();
        int anio = (Integer) cmbAnio.getSelectedItem();

        try {
            LocalDate fecha = LocalDate.of(anio, mes.getNumero(), dia);

        } catch (DateTimeException e) {
            alertarInconsistencia("La fecha de entrega no es valida no es válida.");
            return false;
        }
        return true;
    }

    /**
     * Completa los selectores cuyos valores son independientes de la base
     * de datos, incluyendo día, mes, año, hora y minutos.
     */
    public void completaSelectoresFijos(){
        for(int i = 1; i <= 31; i++){
            cmbDia.addItem(i);
        }

        for(Mes mes : Mes.values()){
            cmbMes.addItem(mes);
        }

        for (int i = 2026; i <= 2030; i++) {
            cmbAnio.addItem(i);
        }

        for (int i = 0; i <= 23; i++) {
            cmbHora.addItem(i);
        }

        for (int i = 0; i <= 59; i++) {
            cmbMinutos.addItem(i);
        }
    }

    /**
     * Método para definir los valores de los comboBox de Pedido y Repartidores.
     * Este método se llamará luego de botón accionado en la vista. La idea es estar actualizados en caso
     * de que se editen los pedidos o repartidores desde sus respectivos mantenedores mientras esta ventana
     * esta abierta.
     */
    public void completaSelectoresVariables(){
        // Eliminar elementos anteriores
        cmbPedido.removeAllItems();
        cmbRepartidor.removeAllItems();

        // Consultar y poblar los pedidos
        for(Pedido pedido : controladorPedidos.getPedidos()){
            cmbPedido.addItem(pedido);
        }

        // Consultar y poblar los repartidores
        for(Repartidor repartidor : controladorRepartidores.getRepartidores()){
            cmbRepartidor.addItem(repartidor);
        }
    }

    /**
     * Restablece todos los selectores del formulario, elimina la selección
     * actual de la tabla y actualiza la información mostrada.
     */
    public void limpiar(){
        cmbPedido.setSelectedIndex(-1);
        cmbRepartidor.setSelectedIndex(-1);
        cmbDia.setSelectedIndex(-1);
        cmbMes.setSelectedIndex(-1);
        cmbAnio.setSelectedIndex(-1);
        cmbHora.setSelectedIndex(-1);
        cmbMinutos.setSelectedIndex(-1);
        registroSeleccionado = -1;
        tblEntregas.clearSelection();
        actualizarTabla();
        completaSelectoresVariables();
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
