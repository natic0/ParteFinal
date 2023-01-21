/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Pronostico;

import static Controller.Validations.ControllerValidations.validations;
import static Controller.Ventas.ControllerVentas.ventas;
import examen.Principal;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import rojerusan.RSNotifyFade;

/**
 *
 *@author Angie Natalia Cordoba Collazos
 */
public class ControllerPronostico implements InterfacePronostico {

    private static int n, a, b, c;

    private static int numerador, denominador;

    private static int[] años;
    private static int[] minimos_cuadrados;

    // variables para todo el programa
    public static InterfacePronostico pronostico = ControllerPronostico.getSingleton();

    private static ControllerPronostico INSTANCE = null;

    private static synchronized void setSingleton() {

        // si el objeto es null
        // si el objeto es null se crea una sola vez
        if (INSTANCE == null) {

            // cree el objeto una sola vez
            INSTANCE = new ControllerPronostico();

        }
    }

    // metodo para retuilizar el objeto
    public static ControllerPronostico getSingleton() {
        // cree el objeto una sola vez
        setSingleton();

        // retorna el objeto
        return INSTANCE;
    }

    private void calcularA(JTable tbl, examen.Principal frame) {

        obtenerDatos(tbl, frame);

        numerador = Controller.Ventas.ControllerVentas.cantidad_ventas - (b * Controller.Ventas.ControllerVentas.año);

        denominador = n;

        a = (numerador / denominador);

    }

    private void calcularB(JTable tbl, examen.Principal frame) {

        obtenerDatos(tbl, frame);

        numerador = (n * (Controller.Ventas.ControllerVentas.añoxventa)) - (Controller.Ventas.ControllerVentas.año * Controller.Ventas.ControllerVentas.cantidad_ventas);

        denominador = (int) ((n * Controller.Ventas.ControllerVentas.año_cuadrado) - (Math.pow(Controller.Ventas.ControllerVentas.año, 2)));

        try {
            b = (numerador / denominador);
        } catch (java.lang.ArithmeticException ex) {
            System.out.println("No se puede dividir entre cero: " + ex);
        }

    }

    private void calcularPorcentaje(JTable tbl, examen.Principal frame) {

        obtenerDatos(tbl, frame);

        numerador = b * n;

        denominador = Controller.Ventas.ControllerVentas.cantidad_ventas;

        c = (numerador / denominador);

    }

    private void obtenerDatos(JTable tbl, examen.Principal frame) {
        n = tbl.getRowCount();

        Controller.Ventas.ControllerVentas.año = Integer.parseInt(frame.lbl_total_años.getText());

        Controller.Ventas.ControllerVentas.cantidad_ventas = Integer.parseInt(validations.quitar_puntos(validations.quitarCaracter(frame.lbl_total_ventas.getText(), " ")));

        Controller.Ventas.ControllerVentas.año_cuadrado = Integer.parseInt(frame.lbl_total_año_cuadrado.getText());

        Controller.Ventas.ControllerVentas.venta_cuadrado = Integer.parseInt(validations.quitar_puntos(validations.quitarCaracter(frame.lbl_total_ventas_cuadrado.getText(), " ")));

        Controller.Ventas.ControllerVentas.añoxventa = Integer.parseInt(validations.quitar_puntos(validations.quitarCaracter(frame.lbl_total_añoxventas.getText(), " ")));

    }

    public void calcularPronostico(Principal frame, JTable tbl_historial_ventas, JTable tbl_pronostico) {

        años = new int[tbl_historial_ventas.getRowCount()];
        minimos_cuadrados = new int[tbl_historial_ventas.getRowCount()];

        if (tbl_historial_ventas.getRowCount() >= 2) {

            if (tbl_pronostico.getRowCount() > 0) {
                eliminarTodo(frame, tbl_pronostico);
            }

            // guarde el arreglo en la tabla
            Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl_pronostico.getModel();

            Controller.Validations.ControllerValidations.filas_tbl = new String[2];

            if (tbl_pronostico.getRowCount() != tbl_historial_ventas.getRowCount()) {

                calcularAños(tbl_historial_ventas);

                calcularB(tbl_historial_ventas, frame);
                calcularA(tbl_historial_ventas, frame);
                calcularPorcentaje(tbl_historial_ventas, frame);

                int porcentaje = c * 100;

                frame.lbl_porcentaje.setText(String.valueOf(porcentaje) + " %");

                calcularMinimosCuadrados();

                for (int i = 0; i < años.length; i++) {
                    Controller.Validations.ControllerValidations.filas_tbl[0] = String.valueOf(años[i]);
                    Controller.Validations.ControllerValidations.filas_tbl[1] = String.valueOf(minimos_cuadrados[i]);
                    Controller.Validations.ControllerValidations.modelo.addRow(Controller.Validations.ControllerValidations.filas_tbl);
                }

            } else {
                new rojerusan.RSNotifyFade("NO se pudo calcular el pronostico de ventas", "ya fue calculado el pronostico de ventas",
                        7, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            }

        } else {

            new rojerusan.RSNotifyFade("NO se pudo calcular el pronostico de ventas", "Debe haber como minimo dos ventas registradas",
                    7, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        }

    }

    private void calcularMinimosCuadrados() {

        for (int i = 0; i < años.length; i++) {

            minimos_cuadrados[i] = a + b * años[i];

        }
    }

    private void calcularAños(JTable tbl) {

        int ultimaFila = tbl.getRowCount() - 1;
        int ultimo_año = Integer.parseInt(tbl.getValueAt(ultimaFila, 0).toString());

        // JOptionPane.showMessageDialog(null, "Ultimo año: " + ultimo_año);
        //J//OptionPane.showMessageDialog(null, "Tamaño del arreglo de años: " + años.length);
        años = new int[tbl.getRowCount()];
        int i = 0;

        while (i < tbl.getRowCount()) {
            años[i] = (ultimo_año++) + 1;

            i++;
        }

    }

    public void eliminarTodo(examen.Principal frame, JTable tbl) {

        Controller.Validations.ControllerValidations.modelo = (DefaultTableModel) tbl.getModel();

        int fila = tbl.getRowCount();

        for (int i = fila - 1; i >= 0; i--) {

            Controller.Validations.ControllerValidations.modelo.removeRow(i);

        }

    }

}
