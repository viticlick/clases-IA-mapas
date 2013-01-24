/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapas.busqueda;

/**
 * Interfaz para encapsular Operadores
 * @author rp
 */
public interface Operador {
    /** Etiqueta descriptiva del Operador */
    public String getEtiqueta();

    /** Coste del operador */
    public double getCoste();
}
