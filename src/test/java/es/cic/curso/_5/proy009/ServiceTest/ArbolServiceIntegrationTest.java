package es.cic.curso._5.proy009.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso._5.proy009.exception.ArbolException;
import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.repository.ArbolRepository;
import es.cic.curso._5.proy009.service.ArbolService;

@SpringBootTest
@Transactional
public class ArbolServiceIntegrationTest {


    @Autowired
    private ArbolService arbolService;

    @Autowired
    private ArbolRepository arbolRepository;

    //CRUD

    //CREATE TEST

    @Test
     @DisplayName("Crea un coche nuevo y le asigna automaticamente un ID")
     void shouldCreateArbol(){

        
        // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);

        //EJECUTAMOS
        Arbol result = arbolRepository.save(arbol);

        //COMPROBAMOS
        assertNotNull(result.getId(), "El ID no debe ser null");
        assertTrue(arbolRepository.existsById(result.getId()),
                    "El arbol deberia existir en la BBDD");
    }

    @Test
    @DisplayName("Lanza una excepción cuando la moto venga con un ID")
    void shouldRejectCreateWithID(){

        // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);
        arbol.setId(20L);

        //AssertThrows 
        //Syntaxis: assertThrows(Exception excepción_Esperada, String MensajeDeError)
        //Nos devuelve la excepción que se lanze durante la ejecución.
        //assertThrows(ModificationSecurityException.class, () -> arbolService.create(arbol));
    }

    //READ TEST

    @Test
    @DisplayName("Test para comprobar la devolución de un coche existente")
    void shouldGetExisting (){

        // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);

        //EJECUTAMOS
        arbolService.create(arbol);
        Arbol result = arbolService.getArbolById(arbol.getId()); //Almacenamos el resultado en una moto para la verificacion

        //COMPROBAMOS
        assertEquals("Roble Común", result.getEspecie());//la especie es la correcta
        assertEquals(1950, result.getAltura());//La altura es la correcta
        assertEquals(180, result.getEdad());//La edad es la correcta
    }

    @Test
     @DisplayName("Nos lanzará una excepcion si la moto no existe o es null")
     void shouldThrowWhenNotFound(){



    }

    @Test
    @DisplayName("Nos devolvera todos los coches")
    void shouldGetAll(){
        
        //PREPARAMOS
        Arbol arbol1 = new Arbol();
        arbol1.setEspecie("Roble Común");
        arbol1.setAltura(1950);
        arbol1.setEdad(180);

        Arbol arbol2 = new Arbol();
        arbol2.setEspecie("Sauce");
        arbol2.setAltura(2000);
        arbol2.setEdad(200);

        //EJECUTAMOS
        arbolService.create(arbol1);  //Almacenamos en la BBDD
        arbolService.create(arbol2);  //Almacenamos en la BBDD

        //COMPROBAMOS 
        List <Arbol> lista = arbolService.get();  //Almacenamos los resultados en una lista para revisarlos

        //Comprobamos que todos los resultados de la lista coinciden con lo esperado
        assertEquals(2, lista.size(), "Debe haber 2 entradas");
        assertEquals("Roble Común", lista.get(0).getEspecie());
        assertEquals(1950, lista.get(0).getAltura());
        assertEquals(180, lista.get(0).getEdad());
        assertEquals("Sauce", lista.get(1).getEspecie());
        assertEquals(2000, lista.get(1).getAltura());
        assertEquals(200, lista.get(1).getEdad());
    }

    //UPDATE
    @Test
    @DisplayName("Modifica una moto Existente")
    void  shouldUpdateArbol(){

        //PREPARAMOS
        Arbol arbol1 = new Arbol();
        arbol1.setEspecie("Roble Común");
        arbol1.setAltura(1950);
        arbol1.setEdad(180);

        Arbol arbol2 = new Arbol();
        arbol2.setEspecie("Sauce");
        arbol2.setAltura(2000);
        arbol2.setEdad(200);

        //EJECUTAMOS
        arbolService.create(arbol1);  //Almacenamos en la BBDD 
        arbolService.create(arbol2);  //Almacenamos en la BBDD 

        Long id = arbol1.getId();
        
        arbol1.setEdad(100); //Hacemos cambios en arbol 1
        arbol1.setEspecie("Ya");       //para comprobar que solo se realizan en el

        //EJECUTAMOS
        arbolService.update(arbol1);
        //Guardamos arbol1 en otra variable basandonos en la búsqueda después de la actualización
        Arbol arbol1Updated = arbolRepository.findById(id).get();

        //COMPROBAMOS
        assertEquals("Ya", arbol1Updated.getEspecie());
        assertEquals(100, arbol1Updated.getEdad());
      }


    @Test
    @DisplayName("Lanza un error 400 si no tiene ID")
    void shouldRejectUpdateWithoutId(){

        //PREPARAMOS
        Arbol arbolSinId = new Arbol();    //Creamos un arbol
        arbolSinId.setId(null);       //Establecemos un ID nulo

        //EJECUTAMOS Y COMPROBAMOS
        //assertThrows(ModificationSecurityException.class,   //Esperamos una ModificationSecirityException
        //                        () -> arbolService.update(arbolSinId));              //Al hacer el update con id Null
    }

    @Test
    @DisplayName("Lanza un 404 si no existe en la base de datos")
    void shouldRejectUpdateNonExisting(){

        //PREPARAMOS
        Arbol arbolVacio = new Arbol ();
        arbolVacio.setId(1234L);

        //EJECUTAMOS Y COMPROBAMOS
        //assertThrows(ArbolException.class,       //Esperamos una ArbolException
        //             () -> arbolService.update(arbolVacio));//Al hacer el update de motoVacia
    }   

    @Test
    @DisplayName("Elimina una entrada de moto existente en la BBDD")
    void shuldDeleteArbol(){

        //PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);

        //PREPARAMOS
        arbol = arbolRepository.save(arbol);
        Long id = arbol.getId(); //Guardamos el id en una variable para mantenerla despues de borrar

        //EJECUTAMOS
        arbolService.deleteArbolById(id); //Borramos la moto

        //COMPROBAMOS
        assertFalse(arbolRepository.existsById(id),          //Comprobamos que NO existe la entidad
                    "El coche ha sido eliminado");  //Lanzamos mensaje

    }

}
