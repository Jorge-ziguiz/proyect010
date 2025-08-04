package es.cic.curso._5.proy009.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.repository.RamaRepository;
import es.cic.curso._5.proy009.service.RamaService;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class RamaServiceIntegrationTest {

    @Autowired
    private RamaService ramaService;

    @Autowired
    private RamaRepository ramaRepository;

    // CRUD

    // CREATE TEST

    @Test
    @DisplayName("Crea un coche nuevo y le asigna automaticamente un ID")
    void shouldCreateArbol() {

        // PREPARAMOS
        Rama rama = new Rama();
        rama.setLongitud(15);
        rama.setTieneHojas(false);
        rama.setNivel(1);

        // EJECUTAMOS
        Rama result = ramaService.create(rama);

        // COMPROBAMOS
        assertNotNull(result.getId(), "El ID no debe ser null");
        assertTrue(ramaRepository.existsById(result.getId()),
                "El arbol deberia existir en la BBDD");
    }

    // READ TEST

    @Test
    @DisplayName("Test para comprobar la devolución de un coche existente")
    void shouldGetExisting() {

        // PREPARAMOS
        Rama rama = new Rama();
        rama.setLongitud(15);
        rama.setTieneHojas(false);
        rama.setNivel(1);

        // EJECUTAMOS
        ramaService.create(rama);
        Rama result = ramaService.getRamaById(rama.getId()); // Almacenamos el resultado en una moto para la
                                                             // verificacion

        // COMPROBAMOS
        assertEquals(15.0, result.getLongitud());
        assertEquals(1, result.getNivel());
    }

    @Test
    @DisplayName("Nos devolvera todos los coches")
    void shouldGetAll() {

        // PREPARAMOS
        Rama rama = new Rama();
        rama.setLongitud(15);
        rama.setTieneHojas(false);
        rama.setNivel(1);

        Rama rama1 = new Rama();
        rama1.setLongitud(20);
        rama1.setTieneHojas(false);
        rama1.setNivel(2);

        // EJECUTAMOS
        ramaService.create(rama); // Almacenamos en la BBDD
        ramaService.create(rama1); // Almacenamos en la BBDD

        // COMPROBAMOS
        List<Rama> lista = ramaService.get(); // Almacenamos los resultados en una lista para revisarlos

        // Comprobamos que todos los resultados de la lista coinciden con lo esperado
        assertEquals(2, lista.size(), "Debe haber 2 entradas");
        assertEquals(15, lista.get(0).getLongitud());
        assertEquals(1, lista.get(0).getNivel());

    }

    // UPDATE
    @Test
    @DisplayName("Modifica una moto Existente")
    void shouldUpdateArbol() {

        // PREPARAMOS
        Rama rama = new Rama();
        rama.setLongitud(15);
        rama.setTieneHojas(false);
        rama.setNivel(1);

        Rama rama1 = new Rama();
        rama1.setLongitud(20);
        rama1.setTieneHojas(false);
        rama1.setNivel(2);

        // EJECUTAMOS
        ramaService.create(rama); // Almacenamos en la BBDD
        ramaService.create(rama1); // Almacenamos en la BBDD

        Long id = rama1.getId();

        rama1.setLongitud(40); // Hacemos cambios en arbol 1
        rama1.setNivel(50); // para comprobar que solo se realizan en el

        // EJECUTAMOS
        ramaService.update(rama1);
        // Guardamos arbol1 en otra variable basandonos en la búsqueda después de la
        // actualización
        Rama rama1Update = ramaRepository.findById(id).get();

        // COMPROBAMOS
        assertEquals(40, rama1Update.getLongitud());
        assertEquals(50, rama1Update.getNivel());
    }

    @Test
    @DisplayName("Elimina una entrada de moto existente en la BBDD")
    void shuldDeleteArbol() {

        // PREPARAMOS
        Rama rama = new Rama();
        rama.setLongitud(15);
        rama.setTieneHojas(false);
        rama.setNivel(1);


        // PREPARAMOS
        rama = ramaService.create(rama);
        Long id = rama.getId(); // Guardamos el id en una variable para mantenerla despues de borrar

        // EJECUTAMOS
        ramaService.deleteById(id); // Borramos la moto

        // COMPROBAMOS
        assertFalse(ramaRepository.existsById(id), // Comprobamos que NO existe la entidad
                "El coche ha sido eliminado"); // Lanzamos mensaje

    }

}