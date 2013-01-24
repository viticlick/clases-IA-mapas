/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.busqueda;

/**
 *
 * @author zarapalleto
 */
public class NodoIDA extends Nodo{
 
    private Operador operador;
    private double costeCamino;
    
    public NodoIDA( Estado estado, Nodo padre , Operador operador ){
        super( estado, padre );
        this.operador = operador;
        
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
    
    
    
}
