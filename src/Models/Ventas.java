
package Models;


public class Ventas {
    
    private int año;
    private int cantidad_ventas;
    private int año_cuadrado;
    private int venta_cuadrado;
    private int añoxventa;
    
    private static Ventas INSTANCE = null;
    
    public void Ventas (int año, int cantidad_ventas, int año_cuadrado, int venta_cuadrado, int añoxventa){
        this.año = año;
        this.cantidad_ventas = cantidad_ventas;
        this.año_cuadrado = año_cuadrado;
        this.venta_cuadrado = venta_cuadrado;
        this.añoxventa = añoxventa;
    }
    
    public int getAño(){
        return año;
    }
    
    public int getCantidad_ventas(){
        return cantidad_ventas;
    }
    
    public int getAño_cuadrado(){
        return año_cuadrado;
    }
    
    public int getVenta_cuadrado(){
        return venta_cuadrado;
    }
    
    public int getAñoxventa(){
        return añoxventa;
    }
    
    
    private static synchronized void setSingleton(){
        if (INSTANCE == null){
            
            INSTANCE = new Ventas ();
        }
    }
    
    public static Ventas getSingleton(){
        setSingleton();
        
        return INSTANCE;
    }
    
    
    
}
