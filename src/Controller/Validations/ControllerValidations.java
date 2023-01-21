package Controller.Validations;

import Models.Ventas;
import scrollbar.ScrollBarCustom;
import java.awt.Color;
import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import rojerusan.RSNotifyFade;
import rojerusan.RSNotifyShadowAnimated;

import javax.swing.table.DefaultTableModel;

public class ControllerValidations implements InterfaceValidations{
    //Variable para todo el programa
    
    public static InterfaceValidations validations = ControllerValidations.getSingleton();
    
    public static DecimalFormat formatoMoney = new DecimalFormat("###,###.###");
    
    public static JScrollPane[] scrolls;
    
    public static JTextField[] txt;
    
    public static String[] filas_tbl;
    
    public static DefaultTableModel modelo;
    
    public static Integer fila_modificar;
    
    /////////////////////////////////////
    //Variable solo para esta clase
    
    private static ControllerValidations INSTANCE = null;
    
    private static boolean minimizar = false; 
    
    private static boolean [] estado_caracteres;
    
    private static boolean [] estado_repetidos;
    
    
    //sigleton para que los objetos solo se creen una vez
    
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
    
    public boolean Validar(JTextField[] txt, examen.Principal frame, Ventas ventas){
        
        int c = 0;
        
        //Si es mayor a 0 es porque hay campos vacios
        
        if (comprobarTxtVacios(txt) > 0){
            
            new rojerusan.RSNotifyShadowAnimated("Hay campos vacios", "Tiene que registrar la cantidaad de la venta y el numero de años", 
                    7, RSNotifyShadowAnimated.PositionNotify.BottomRight, RSNotifyShadowAnimated.AnimationNotify.BottomUp,
                    RSNotifyShadowAnimated.TypeNotify.WARNING).setVisible(true);
            
            return false;
            
        } else {
            
            if (Integer.parseInt(frame.txt_cantidad_años.getText()) != 0){
                
                if (frame.tbl_historial_ventas.getRowCount() > 0){
                    
                    estado_repetidos = new boolean[frame.tbl_historial_ventas.getRowCount()];
                    
                    for (int i = 0; i < frame.tbl_historial_ventas.getRowCount(); i++){
                        
                        if (ventas.getAño() == Integer.parseInt(frame.tbl_historial_ventas.getValueAt(i, 0).toString())){
                            
                            estado_repetidos[i] = true;
                        }
                    }
                    
                    for (int i = 0; i < estado_repetidos.length; i++){
                        
                        if (estado_repetidos[i] == true){
                            
                            c++;
                            
                        }
                    }
                    
                    if (c > 0){
                        
                        new rojerusan.RSNotifyShadowAnimated("No se permiten los mismos años", "", 
                                7, RSNotifyShadowAnimated.PositionNotify.BottomRight, 
                                RSNotifyShadowAnimated.AnimationNotify.BottomUp, RSNotifyShadowAnimated.TypeNotify.WARNING).setVisible(true);
                        
                        return false;
                    } else {
                        return true;
                    }
                    
                } else {
                    return true;
                }
            } else {
                new rojerusan.RSNotifyShadowAnimated("Año no Valido", "El año debe ser mayor a 0",
                7, RSNotifyShadowAnimated.PositionNotify.BottomRight, RSNotifyShadowAnimated.AnimationNotify.BottomUp,
                RSNotifyShadowAnimated.TypeNotify.WARNING).setVisible(true);
                
                return false;
            }
        }
        
        
    }
    
    public void registrarAño(){
        new rojerusan.RSNotifyShadowAnimated("Año Registrado con Éxito", "", 7, 
                RSNotifyShadowAnimated.PositionNotify.BottomRight, RSNotifyShadowAnimated.AnimationNotify.BottomUp, 
                RSNotifyShadowAnimated.TypeNotify.SUCCESS).setVisible(true);
    }
    
    //Verifica que no haya campos vacios
    
    private int comprobarTxtVacios(JTextField[] txt){
        int c = 0;
        
        estado_caracteres = new boolean[txt.length];
        
        for (int i = 0; i < txt.length; i++){
            if (txt[i].getText().trim().isEmpty()){
                estado_caracteres[i] = true;
            } else {
                estado_caracteres[i] = false;
            }
            
        }
        
        for (int i = 0; i < estado_caracteres.length; i++){
            if (estado_caracteres[i] == true){
                c++;
            }
        }
        
        return c;
    }
    
    private int c = 0;
    
    public void sumatoria(JTextField txt, Integer c, KeyEvent evt){
        
        if (evt != null && c == null){
            
            if(evt.getKeyCode() == KeyEvent.VK_UP){
                sumar(txt, 1);
            }
            
            if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                sumar(txt,-1);
            }
        } else {
            sumar(txt, c);
        }
    }
    
    public void sumar (JTextField txt, int numero){
        c += numero;
        
        if (c <= 0){
            txt.setText("0");
        } else {
            txt.setText(String.valueOf(c));
        }
    }
    
    public void noPermitirCaracteresEspeciales (KeyEvent evt, JTextField txt, int n){
        
        if ((int) evt.getKeyChar() >= n && (int) evt.getKeyChar() <= 47
                || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255){
            
            evt.consume();
            
            new rojerusan.RSNotifyFade("No se permite caracteres especiales", "", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
            txt.requestFocus();
        }
    }
    
    public void noPermiteLetras(KeyEvent evt, JTextField txt, String tipo){
        
        char C = evt.getKeyChar();
        
        if (tipo == "puntos"){
            if (Character.isLetter(C)){
                evt.consume();
                new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
                txt.requestFocus();
                
            } else if (((int) evt.getKeyChar() >= 32 && (int) evt.getKeyChar() <= 45 || (int) evt.getKeyChar() == 47)
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255){
                
                evt.consume();
                
               new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
               txt.requestFocus();
               
            }
        }
        
        if (tipo == "signo_punto"){
            
            if (Character.isLetter(C)){
                
                evt.consume();
                
                new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
                
               txt.requestFocus();
            } else if ((int) evt.getKeyChar() >= 32 && (int) evt.getKeyChar() <= 35
                    || (int) evt.getKeyChar() >= 37 && (int) evt.getKeyChar() <= 45
                    || (int) evt.getKeyChar() == 47
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255){
                
                evt.consume();
                
               new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
               txt.requestFocus();
            }
        }
        
        if (tipo == "normal"){
            
            if (Character.isLetter(C)){
                
                 evt.consume();
                
                new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
                
               txt.requestFocus();
                
            } else if ((int) evt.getKeyChar() >= 32 && (int) evt.getKeyChar() <= 46
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255){
                
                evt.consume();
                
               new rojerusan.RSNotifyFade("Solo se permiten numeros", "No se permiten letras y caracteres especiales", 7,
            RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            
               txt.requestFocus();
            }
            
        }
        
    }
    
    public void formatoMoney(JTextField txt){
        try {
            txt.setText(formatoMoney.format(Integer.parseInt(txt.getText())).replaceAll(" ", ""));
        } catch (java.lang.NumberFormatException ex){
            System.out.println("Excepcion al convertir a formato moneda" + ex);
        }
    }
    
    public String quitar_puntos(String txt){
        String precio = txt.replace(".", "");
        return precio;
    }
    
    @Override
    public void limpiarTxt(JTextField[] txt, int inicio, int fin){
        for (int i = inicio; i < fin; i++){
            txt[i].setText("");
        }
    }
    
    public String quitarCaracter(String txt, String caracter){
        
        String [] parts = txt.split(caracter);
        
        return parts[1]; 
    }
    
    

}