package Controller.Validations;

import Models.Ventas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 *
 * @author Famil
 */
public interface InterfaceValidations {
    
    public void Minimizar (ActionEvent evt, JFrame frame);
    
    public void Close();
    
    public void pintarBackgroundJtable(JScrollPane[] scrolls);
    
    public boolean Validar(JTextField[] txt, examen.Principal frame, Ventas ventas);
    
    public void sumatoria(JTextField txt, Integer c, KeyEvent evt);
    
    public void formatoMoney(JTextField txt);
    
    public void noPermitirCaracteresEspeciales(KeyEvent evt, JTextField txt, int n);
    
    public void noPermiteLetras(KeyEvent evt, JTextField txt, String tipo);
        
    public void limpiarTxt(JTextField[] txt, int inicio, int fin);
    
    public String quitarCaracter(String txt, String caracter);
    
    public String quitar_puntos(String txt);
}
