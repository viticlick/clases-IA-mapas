/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapas.busqueda;

/**
 * Nodos usados por la BusquedaAnchura. 
 * Añade el Operador usado para generar el estado almacenado en este Nodo. 
 * Usado para simplificar la reconstrucción del camino solución.
 * @author rp
 */
public class NodoAnchura extends Nodo {
    /** Operador empeado para generar el Estado almacenado en este Nodo*/
    private Operador operador;
    
    public NodoAnchura(Estado estado, Nodo padre, Operador operador) {
        super(estado, padre);
        this.operador = operador;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
    
    @Override
    public String toString(){
        return (super.toString()+"\nOPERADOR:"+operador.getEtiqueta());
    }
}
