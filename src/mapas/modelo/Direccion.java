package mapas.modelo;

/** Clase que encapsula las direcciones en los mapas
  */
public class Direccion {
   private int tipo;
    
   // Constantes estáticas
   
   // Tipos de direcciones (el orden es importante, diagonales deben ser > 4)
   public static final int NORTE = 0;
   public static final int ESTE  = 1;
   public static final int SUR = 2;
   public static final int OESTE  = 3;
   public static final int NORESTE = 4;
   public static final int NOROESTE = 5;
   public static final int SUDESTE = 6;
   public static final int SUDOESTE = 7;


   /** Array de conveniencia con las direcciones posibles ordenadas (el orden es importante, diagonales deben ser > 4) */
   public static final Direccion[] DIRECCIONES = {
        new Direccion(Direccion.NORTE),
        new Direccion(Direccion.ESTE),
        new Direccion(Direccion.SUR),
        new Direccion(Direccion.OESTE),
        new Direccion(Direccion.NORESTE),
        new Direccion(Direccion.NOROESTE),
        new Direccion(Direccion.SUDESTE),
        new Direccion(Direccion.SUDOESTE)
   };

   /** Etiquetas de las direcciones */
   public static final String[] NOMBRE_DIRECCION= {"NORTE", "ESTE", "SUR", "OESTE",
                                                   "NORTESTE", "NORTOESTE", "SUDESTE", "SUDOESTE"};



   public Direccion(int id)   {
      this.tipo = id;
   }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return Direccion.NOMBRE_DIRECCION[this.tipo];
    }

   
    public boolean esDiagonal(){
        return (this.tipo >= Direccion.NORESTE);
    }

}
