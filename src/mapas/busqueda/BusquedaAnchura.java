/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.busqueda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import mapas.modelo.EstadoMapa;

/**
 * Implementa una búsqueda en Anchura genérica (independiente de Estados y Operadores)
 * controlando repetición de estados.
 * Usa lista ABIERTOS (LinkedList) y lista CERRADOS (Hastable usando Estado como clave)
 * @author rp
 */
public class BusquedaAnchura implements Busqueda {

    /** Lista ABIERTOS implementada como una lista enlazada de NodoAnchura */
    private LinkedList<NodoAnchura> abiertos;
    
    //private HashSet<Estado> cerrados;
    /** Lista CERRADOS implementada como una tabla hash donde las claves son
     *  objetos de tipo Estado
     */
    private HashMap<Estado, NodoAnchura> cerrados;

    /** Constructor de una búsqueda en anchura, crear las listas ABIERTOS y CERRADOS*/
    public BusquedaAnchura() {
        abiertos = new LinkedList<NodoAnchura>(); // Acceso ordenado (COLA)
        cerrados = new HashMap<Estado, NodoAnchura>(); // Acceso directo (por Estado)
    }

    /** 
     * Implementa la búsqueda en anchura. Si encuentra solución recupera la lista
     * de Operadores empleados almacenada en los atributos de los objetos NodoAnchura
     * @param estadoInicial Estado inicial de la búsqueda
     * @return null o Vector con la lista de Operadores que llevan a la solución
     */
    public List<Operador> buscarSolucion(Estado estadoInicial) {
        NodoAnchura nodoActual = null;
        Estado estadoActual, estadoSucesor;
        boolean solucion = false;

        abiertos.clear();
        cerrados.clear();
        abiertos.add(new NodoAnchura(estadoInicial, null, null));
        while (!solucion && !abiertos.isEmpty()) {
            nodoActual = abiertos.pollFirst();
            estadoActual = nodoActual.getEstado();
            
            if (estadoActual.esFinal()) {
                solucion = true;
            } else {
                cerrados.put(estadoActual, nodoActual);             

                for (Operador operador : estadoActual.operadoresAplicables()) {
                    estadoSucesor = estadoActual.aplicarOperador(operador);
                    if (!cerrados.containsKey(estadoSucesor) && !estadoEnAbiertos(estadoSucesor)) {
                        // Anadir al final (PILA -> nodos ordenados de menor a mayor profundidad)                        
                        abiertos.addLast(new NodoAnchura(estadoSucesor, nodoActual, operador));
                    }
                }
            }
        }
        
        if (solucion) {
            List<Operador> lista = new LinkedList<Operador>();
            NodoAnchura nodo = nodoActual;

            while (nodo.getPadre() != null) { // Asciende hasta raíz
                lista.add(0, nodo.getOperador()); // Añadir al  principio de la lista de movimientos (vamos de abajo a arriba)
                nodo = (NodoAnchura) nodo.getPadre();
            }
            return (lista);
        } else {
            return ((List<Operador>) null);
        }
    }

    /**
     * Recorre la lista Abiertos para comprobar si el estada recibido está en alguno  de esos NodoAnchura
     * TODO: evitar búsqueda lineal empleando otra estrucutra de datos para Abiertos
     * @param e
     * @return
     */
    private boolean estadoEnAbiertos(Estado e){
        for (NodoAnchura nodo : this.abiertos) {
            if (e.equals(nodo.getEstado())){
                return true; // Encontrado
            }
        }
        return false;  // No encontrado
    }
}
