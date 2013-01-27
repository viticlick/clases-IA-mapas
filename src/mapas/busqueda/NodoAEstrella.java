/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.busqueda;

import java.util.LinkedList;

/**
 *
 * @author zarapalleto
 */
public class NodoAEstrella extends Nodo{
    
    private Operador operador;
    private double g_costeCamino;
    private double h_estimacion;
    private LinkedList<NodoAEstrella> descendientes;
    
    
    public NodoAEstrella( Estado estado , Nodo padre , Operador operador , double h_costeEstimado ){
        super( estado , padre );
        this.operador = operador;
        this.h_estimacion = h_costeEstimado;
        this.descendientes = new LinkedList<NodoAEstrella>();
        
        if( padre == null || operador == null  ){
            this.g_costeCamino = 0;
        }else{
            this.g_costeCamino = ( (NodoAEstrella) padre ).getCosteCamino() + operador.getCoste() ;
        }
    }

    public double getCosteCamino() {
        return g_costeCamino;
    }

    public void setCosteCamino(double costeCamino) {
        this.g_costeCamino = costeCamino;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public double getEstimacion() {
        return h_estimacion;
    }

    public void setEstimacion(double costeEstimado) {
        this.h_estimacion = costeEstimado;
    }
    
    public double getCosteEstimado(){
        return this.h_estimacion + this.g_costeCamino;
    }

    public LinkedList<NodoAEstrella> getDescendientes() {
        return descendientes;
    }

    public void setDescendientes(LinkedList<NodoAEstrella> descendientes) {
        this.descendientes = descendientes;
    }
    
    /**
     * Recalcula el valor de g(e) del nodo actual y lo propaga a los nodos descendientes.
     */
    public void propagarCosteCamino(){
        
        if( this.getPadre() == null || operador == null  ){
            this.g_costeCamino = 0;
        }else{
            this.g_costeCamino = ( (NodoAEstrella) this.getPadre() ).getCosteCamino() + operador.getCoste() ;
        }
        
        for( NodoAEstrella hijo : descendientes ){
           hijo.propagarCosteCamino(); 
        }
    }
    
    /**
     * Elimina un nodo hijo concreto de la lista de descendientes
     * @param nodo Nodo que se desea eliminar de la lista de descendientes.
     */
    public void descolgarHijo( NodoAEstrella nodo ){
        this.descendientes.remove(nodo);
    }
    
    /**
     * Sustituye un nodo perteneciente a la lista de descendientes del nodo actual
     * por un nodo nuevo
     * @param antiguo Nodo que será eliminado de la lista de hijos
     * @param nuevo Nodo que se añade a la lista de nodos hijo
     */
    public void sustituirHijo( NodoAEstrella antiguo , NodoAEstrella nuevo ){
        this.descendientes.remove( antiguo );
        this.descendientes.add(nuevo);
    }
    
    @Override
    public int hashCode(){
        return this.getEstado().hashCode();
    }
    
}
