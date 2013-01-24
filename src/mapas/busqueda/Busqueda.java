/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapas.busqueda;

import java.util.List;

/**
 * Interfaz genérico para algorimtos de búsqueda
 * @author rp
 */
public interface Busqueda {
    /**
     * Interfaz genérico para algorimtos de búsqueda
     * @param inicial Estado inicial
     * @return null o Vector con la lista de Operadores
     */
    public List<Operador> buscarSolucion(Estado inicial);
}
