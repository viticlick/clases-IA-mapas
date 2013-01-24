/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapas.busqueda;

import java.util.List;
import mapas.modelo.Posicion;

/**
 * Interfaz genérico para los estados del espacio de estados
 * @author rp
 */
public interface Estado {
    /** 
     * Devuelve el Vector con la lista de Operadores aplicables sobre este Estado
     * @return Vector de operadores aplicables
     */
    public List<Operador> operadoresAplicables();
    
    /**
     * Indica si este es un estad final (solución)
     * @return true si es un estado final
     */
    public boolean esFinal();
    
    /**
     * Genera un nuevo Estado resultante de aplicar el Operador indicado 
     * sobr el este Estado
     * @param o Operador a aplicar
     * @return Estado resultante
     */
    public Estado aplicarOperador(Operador o);
    
    /** Codigo hash del Estado, necesario para usar Tablas Hash */
    @Override
    public int hashCode();
    
    /** Igualdad entre Estados, necesario para usar Listas */
    @Override
    public boolean equals(Object o);
    
    @Override
    public String toString();
    
    public Posicion getPosicion();
}
