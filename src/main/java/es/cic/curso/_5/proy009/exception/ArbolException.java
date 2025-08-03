package es.cic.curso._5.proy009.exception;
public class ArbolException extends RuntimeException{

    public ArbolException (long id){
        super("No existe el Arbol con ID "+id);
    }
}
