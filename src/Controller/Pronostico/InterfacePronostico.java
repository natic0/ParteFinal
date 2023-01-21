/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller.Pronostico;

import javax.swing.JTable;

/**
 *
 * @author Angie Natalia Cordoba Collazos
 */
public interface InterfacePronostico {
    
   public void calcularPronostico(examen.Principal frame,  JTable tbl_historial_ventas, JTable tbl_pronostico);
   
    public void eliminarTodo(examen.Principal frame, JTable tbl);
}
