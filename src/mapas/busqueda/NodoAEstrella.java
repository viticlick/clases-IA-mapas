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
    private double costeCamino;
    private double costeEstimado;
    private LinkedList<NodoAEstrella> descendientes;
    
    
    public NodoAEstrella( Estado estado , Nodo padre , Operador operador , double costeEstimado ){
        super( estado , padre );
        this.operador = operador;
        this.costeEstimado = costeEstimado;
        this.descendientes = new LinkedList<NodoAEstrella>();
        
        if( padre == null || operador == null  ){
            this.costeCamino = 0;
        }else{
            this.costeCamino = ( (NodoIDA) padre ).getCosteCamino() + operador.getCoste() ;
        }
    }

    public double getCosteCamino() {
        return costeCamino;
    }

    public void setCosteCamino(double costeCamino) {
        this.costeCamino = costeCamino;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public double getCosteEstimado() {
        return costeEstimado;
    }

    public void setCosteEstimado(double costeEstimado) {
        this.costeEstimado = costeEstimado;
    }

    public LinkedList<NodoAEstrella> getDescendientes() {
        return descendientes;
    }

    public void setDescendientes(LinkedList<NodoAEstrella> descendientes) {
        this.descendientes = descendientes;
    }
    
    public void propagarCosteCamino(){
        
        if( this.getPadre() == null || operador == null  ){
            this.costeCamino = 0;
        }else{
            this.costeCamino = ( (NodoIDA) this.getPadre() ).getCosteCamino() + operador.getCoste() ;
        }
        
        for( NodoAEstrella hijo : descendientes ){
           hijo.propagarCosteCamino(); 
        }
    }
    
    public void descolgarHijo( ){
        
    }
    
    @Override
    public int hashCode(){
        return this.getEstado().hashCode();
    }
    
}
