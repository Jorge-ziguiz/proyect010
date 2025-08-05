package es.cic.curso._5.proy009.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso._5.proy009.exception.ArbolException;
import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.exception.RamaException;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.service.ArbolService;
import es.cic.curso._5.proy009.service.RamaService;

@RestController
@RequestMapping("/arbol")
public class ArbolController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArbolController.class);

    @Autowired
    private ArbolService arbolService;

    @Autowired
    private RamaService ramaService;

    // CRUD
    @PostMapping
    public Arbol crearArbol(@RequestBody Arbol arbol) {
        if (arbol.getId() != null) {
            LOGGER.info("El arbol no puede tener un ID en su creación. El id se crea automáticamente");
            throw new ModificationSecurityException("El arbol no puede llevar ID");

        } else {
            LOGGER.info("Arbol creado correctamente");
            if (arbol.getRamas() != null && arbol.getRamas().size() > 0) {
                for (Rama rama : arbol.getRamas()) {
                    if (rama.getId() != null) {
                        throw new ModificationSecurityException(
                                "No se puede crear un rama con un ID DADO POR EL USUARIO");
                    }
                }
            }
            return arbolService.create(arbol);
        }
    }

    // APRENDE A LEER (GET)
    @GetMapping("/{id}")
    public Arbol get(@PathVariable long id) {
        Arbol arbol = null;

        arbol = arbolService.getArbolById(id);

        if (arbol == null) {
            throw new ArbolException(id);
        }
        return arbol;
    }

    @GetMapping
    public List<Arbol> getAll() {
        return arbolService.get();
    }

    // ACTUALIZAR (PUT)
    @PutMapping
    public Arbol update(@RequestBody Arbol arbol) {
        if (arbol.getId() == null) {
            LOGGER.info("Se intenta crear arbol con un id nulo");
            throw new ModificationSecurityException("El id no puede ser Nulo");

        } else if (arbolService.getArbolById(arbol.getId()) == null) {
            LOGGER.info("Se intenta actualizar in arbol que no existe");
            throw new ArbolException(arbol.getId());

        } else {
            if (arbol.getRamas() != null && arbol.getRamas().size() > 0) {
                for (Rama rama : arbol.getRamas()) {
                    if (rama.getId() == null) {
                        LOGGER.info("Se intenta crear rama con un id nulo");
                        throw new ModificationSecurityException("El id no puede ser Nulo");

                    } else if (ramaService.getRamaById(rama.getId()) != null) {
                        LOGGER.info("Se intenta actualizar in rama que no existe");
                        throw new RamaException(rama.getId());
                    }
                }
            }

            LOGGER.info("Arbol Actualizado Correcramente");
            return arbolService.update(arbol);
        }
    }

    // ELIMINAR (DELETE)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        arbolService.deleteArbolById(id);
    }

}
