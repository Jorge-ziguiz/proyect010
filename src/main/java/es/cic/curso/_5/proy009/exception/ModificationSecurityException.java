package es.cic.curso._5.proy009.exception;

public class ModificationSecurityException extends RuntimeException{

    public ModificationSecurityException(){
        super("Has tratado de modificar un objeto creando uno");
    }

    public ModificationSecurityException(String mensaje){
        super(mensaje);
    }

    public ModificationSecurityException(String mensaje, Throwable throwable){
        super(mensaje, throwable);
    }

}
