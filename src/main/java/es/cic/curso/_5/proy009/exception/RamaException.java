package es.cic.curso._5.proy009.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RamaException extends RuntimeException{

    public RamaException (long id){
        super("No existe el Rama con ID "+id);
    }
}
