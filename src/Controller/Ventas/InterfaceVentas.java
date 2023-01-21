package Controller.Ventas;

import javax.swing.JTable;
import javax.swing.JTextField;

public interface InterfaceVentas{
    
    public void registrarAño(JTextField[] txt, examen.Principal frame, JTable tbl);
    
    public void eliminarAño(examen.Principal frame, JTable tbl);
    
    public void eliminarTodo(examen.Principal frame, JTable tbl);
    
    public void seleccionar (JTable tbl, examen.Principal frame);
    
    public void actualizar (JTable tbl, examen.Principal frame);
}
