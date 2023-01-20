package Controller.Validations;

import Component.scrollbar.ScrollBarCustom;
import java.awt.Color;
import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ControllerValidations implements InterfaceValidations{
    //Variable para todo el programa
    
    public static InterfaceValidations validations = ControllerValidations.getSingleton();
    
    public static JScrollPane[] scrolls;
    
    /////////////////////////////////////
    //Variable solo para esta clase
    
    private static ControllerValidations INSTANCE = null;
    
    private static boolean minimizar = false; 
    
    private static synchronized void setSingleton(){
        //Si el objeto es null se crea una sola vez 
        
        if (INSTANCE == null){
            
            INSTANCE = new ControllerValidations();
        
        }
    }
    
    //Reutilizar el Objeto
    
    public static ControllerValidations getSingleton(){
        setSingleton();
        
        return INSTANCE;
    }
    
    public void Minimizar(ActionEvent evt, JFrame frame){
        frame.setExtendedState(ICONIFIED);
        if (!minimizar){
            minimizar = false;
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        } else {
            minimizar = true;
        }
    }
    
    public void Close(){
        System.exit(0);
       
    }
    
    public void pintarBackgroundJtable(JScrollPane[] scrolls){
        
        for (int i = 0; i < scrolls.length; i++){
            //pinta de color blanco el background del jtable y asigna un scroll moderno
            
            scrolls[i].getViewport().setBackground(Color.WHITE);
            
            scrolls[i].setVerticalScrollBar(new ScrollBarCustom());
            ScrollBarCustom sp = new ScrollBarCustom();
            sp.setOrientation(JScrollBar.VERTICAL);
            scrolls[i].setHorizontalScrollBar(sp);
            
            scrolls[i].setVerticalScrollBar(new ScrollBarCustom());
            ScrollBarCustom sp1 = new ScrollBarCustom();
            sp1.setOrientation(JScrollBar.VERTICAL);
            scrolls[i].setHorizontalScrollBar(sp1);
            scrolls[i].getViewport().setBackground(Color.WHITE);
            
        }
    }

}