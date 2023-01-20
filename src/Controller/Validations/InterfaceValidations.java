
package Controller.Validations;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 *
 * @author Famil
 */
public interface InterfaceValidations {
    
    public void Minimizar (ActionEvent evt, JFrame frame);
    
    public void Close();
    
    public void pintarBackgroundJtable(JScrollPane[] scrolls);
    
}
