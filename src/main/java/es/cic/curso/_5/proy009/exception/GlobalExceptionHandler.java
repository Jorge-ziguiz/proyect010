package es.cic.curso._5.proy009.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(ModificationSecurityException.class)
    public ResponseEntity<String> handleModificationSecurityException(ModificationSecurityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateBodyNegativeResponses(e.getMessage()));

    }

    @ExceptionHandler(ArbolException.class)
    public ResponseEntity<String> handleArbolException(ArbolException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateBodyNegativeResponses(e.getMessage()));

    }


    @ExceptionHandler(RamaException.class)
    public ResponseEntity<String> handleRamaException(RamaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateBodyNegativeResponses(e.getMessage()));

    }

     private String generateBodyNegativeResponses(String mesage) {
         String jsonbody ="";
        try {
            MessageResponse mesaggeexception = new MessageResponse(mesage);
           jsonbody= objectMapper.writeValueAsString(mesaggeexception);
            return jsonbody;
        } catch (Exception e) {
            return jsonbody;
        }
    }

}
