
package Controller.Ventas;

import Controller.Validations.ControllerValidations;
import static Controller.Validations.ControllerValidations.validations;
import Models.Ventas;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import rojerusan.RSNotifyFade;


public class ControllerVentas implements InterfaceVentas{
        
    private static int fila;
    
    public static int año;
    public static int cantidad_ventas;
    public static int año_cuadrado;
    public static int venta_cuadrado;
    public static int añoxventa;
    
    Ventas venta = Models.Ventas.getSingleton();
    
    public static InterfaceVentas ventas = ControllerVentas.getSingleton();
    
    private static ControllerVentas INSTANCE = null;
    
    
    private static synchronized void setSingleton(){
        if (INSTANCE == null){
            
            INSTANCE = new ControllerVentas();
        }
    }
    
    public static ControllerVentas getSingleton(){
        setSingleton();
        return INSTANCE;
    }
    
    @Override
    
    public void registrarAño(JTextField[] txt, examen.Principal frame, JTable tbl){
        
        obtenerObjeto(frame);
        
        if (validations.Validar (txt, frame, venta) == true){
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        ControllerValidations.filas_tbl = new String[5];
        
        ControllerValidations.filas_tbl[0] = String.valueOf(venta.getAño());
        ControllerValidations.filas_tbl[1] = ControllerValidations.formatoMoney.format(venta.getCantidad_ventas());
        ControllerValidations.filas_tbl[2] = String.valueOf(venta.getAño_cuadrado());
        ControllerValidations.filas_tbl[3] = ControllerValidations.formatoMoney.format(venta.getVenta_cuadrado());
        ControllerValidations.filas_tbl[4] = ControllerValidations.formatoMoney.format(venta.getAñoxventa());
        
        Controller.Validations.ControllerValidations.modelo.addRow(ControllerValidations.filas_tbl);
        
        validations.limpiarTxt(txt, 0, 1);
        
        totales(frame,tbl);
        
        new rojerusan.RSNotifyFade("Guadado con exito", "Las ventas del año " + año + "han sido guardadas con exito" , 7, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
        }
        
        
    }
    
    private void obtenerObjeto(examen.Principal frame){
        
        año = Integer.parseInt(frame.txt_cantidad_años.getText());
        cantidad_ventas = Integer.parseInt(validations.quitar_puntos(frame.txt_cantidad_ventas.getText()));
        año_cuadrado = (int) Math.pow(año,2);
        venta_cuadrado = (int) Math.pow(cantidad_ventas,2);
        añoxventa = año * cantidad_ventas;
        
        venta.Ventas(año, cantidad_ventas, año_cuadrado, venta_cuadrado, añoxventa);
    }
    
    public void eliminarAño(examen.Principal frame, JTable tbl){
        
        fila = tbl.getSelectedRow();
        
        String año = tbl.getValueAt(fila, 0).toString();
        
        if (fila >= 0){
            
            Controller.Validations.ControllerValidations.modelo.removeRow(fila);
            
            new rojerusan.RSNotifyFade("Fila eliminada con Exito", "Las Ventas del Año " + año + " han sido eliminadas con Éxito",
            7, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            totales (frame, tbl);
        } else {
            
            new rojerusan.RSNotifyFade("Seleccione una fila", "", 
                    7, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
          
        }
    }
    
    public void eliminarTodo(examen.Principal frame, JTable tbl){
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        fila = tbl.getRowCount();
        
        for (int i = fila - 1; i >= 0; i--){
            
            Controller.Validations.ControllerValidations.modelo.removeRow(i);
        }
        
        frame.lbl_total_años.setText("0");
        frame.lbl_total_ventas.setText("$ 0");
        frame.lbl_total_año_cuadrado.setText("0");
        frame.lbl_total_ventas_cuadrado.setText("$ 0");
        frame.lbl_total_añoxventas.setText("$ 0");
        
        new rojerusan.RSNotifyFade("Tabla limpiada con exito", "", 7,
        RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
        
    }
    
    public void seleccionar (JTable tbl, examen.Principal frame){
        
        fila = tbl.getSelectedRow();
        
        String año = tbl.getValueAt(fila, 0).toString();
        String venta = tbl.getValueAt(fila, 1).toString();
        
        frame.txt_cantidad_ventas.setText(validations.quitar_puntos(venta));
        
        frame.txt_cantidad_años.setText(año);
        
        Controller.Validations.ControllerValidations.fila_modificar = fila;
    }
    
    public void actualizar (JTable tbl, examen.Principal frame){
        
        obtenerObjeto(frame);
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        Controller.Validations.ControllerValidations.modelo.setValueAt(venta.getAño(), Controller.Validations.ControllerValidations.fila_modificar,0);
        Controller.Validations.ControllerValidations.modelo.setValueAt(Controller.Validations.ControllerValidations.formatoMoney.format(venta.getCantidad_ventas()), Controller.Validations.ControllerValidations.fila_modificar, 1);
        Controller.Validations.ControllerValidations.modelo.setValueAt(venta.getAño_cuadrado(), Controller.Validations.ControllerValidations.fila_modificar,2);
        Controller.Validations.ControllerValidations.modelo.setValueAt(Controller.Validations.ControllerValidations.formatoMoney.format(venta.getVenta_cuadrado()), Controller.Validations.ControllerValidations.fila_modificar , 3);
        Controller.Validations.ControllerValidations.modelo.setValueAt(Controller.Validations.ControllerValidations.formatoMoney.format(venta.getAñoxventa()), Controller.Validations.ControllerValidations.fila_modificar , 4);
        
        totales(frame,tbl);
        
        new rojerusan.RSNotifyFade("Fila actualizada con exito", "Las ventas del año " + año + " han sido actualizadas con exito", 7, 
        RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
    }
    
    public void totales (examen.Principal frame, JTable tbl){
        
        frame.lbl_total_años.setText(String.valueOf(totalAños(tbl)));
        frame.lbl_total_ventas.setText("$ " + Controller.Validations.ControllerValidations.formatoMoney.format(totalVentas(tbl)));
        frame.lbl_total_año_cuadrado.setText(String.valueOf(totalAñosCuadrado(tbl)));
        frame.lbl_total_ventas_cuadrado.setText("$ " + Controller.Validations.ControllerValidations.formatoMoney.format(totalVentasCuadrado(tbl)));
        frame.lbl_total_añoxventas.setText("$ " + Controller.Validations.ControllerValidations.formatoMoney.format(totalAñosxVentas(tbl)));
    }
    
    private int totalAños (JTable tbl){
        int total = 0;
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        for (int i = 0; i < tbl.getRowCount(); i++){
            
            total += Integer.parseInt(tbl.getValueAt(i,0).toString());
            
        }
        
        return total;
    }
    
    private int totalVentas(JTable tbl){
        int total = 0;
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        for (int i = 0; i < tbl.getRowCount(); i++){
            
            total += Integer.parseInt(validations.quitar_puntos(tbl.getValueAt(i,1).toString()).replace(" ", ""));
            
        }
        
        return total;
    }
    
    private int totalAñosCuadrado(JTable tbl){
        int total = 0;
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        for (int i = 0; i < tbl.getRowCount(); i++){
            
            total += Integer.parseInt(tbl.getValueAt(i, 2).toString());
            
        }
        
        return total;
    }
    
    private int totalVentasCuadrado(JTable tbl){
        
        int total = 0;
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        for (int i = 0; i < tbl.getRowCount(); i++){
            
            total += Integer.parseInt(validations.quitar_puntos(tbl.getValueAt(i,3).toString()).replace(" ", ""));
            
        }
        
        return total;
    }
    
    private int totalAñosxVentas(JTable tbl){
        
        int total = 0;
        
        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();
        
        for (int i = 0; i < tbl.getRowCount(); i++){
            
            total += Integer.parseInt(validations.quitar_puntos(tbl.getValueAt(i,4).toString()).replace(" ", ""));
            
        }
        
        return total;
    }
    
}