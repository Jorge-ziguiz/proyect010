package es.cic.curso._5.proy009.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso._5.proy009.exception.ArbolException;
import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.repository.ArbolRepository;

@Service
@Transactional
public class ArbolService {

    // Usamos Logger para mantener un registro de la actividad y los herrores
    private final static Logger LOGGER = LoggerFactory.getLogger(Arbol.class);

    @Autowired
    private ArbolRepository arbolRepository;

    // Vamos con el CRUD

    // Create

    public Arbol create(Arbol arbol) {
        LOGGER.info("Creamos un arbol");

        return arbolRepository.save(arbol);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Arbol> get() {
        LOGGER.info("Obteniendo lista de todos los arboles....");
        return arbolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Arbol getArbolById(long id) {
        LOGGER.info("Buscando Arbol con id {}", id);

        return arbolRepository.findById(id)
                .orElseThrow(() -> new ArbolException(id));
    }

    // Update

    public Arbol update(Arbol arbol) {

        LOGGER.info("Actualizando Arbol...");

        // Filtramos
        // Si no tiene ID
        
        return arbolRepository.save(arbol);

    }

    public void deleteArbolById(long id) {
        LOGGER.info("borrando Arbol con id {}", id);
        arbolRepository.deleteById(id);

    }

    public void deleteAll() {

        // El clasico
        LOGGER.info("Borramos TODOS LOS ARBOLES GENTE, PANICO (pero solo si habia algo importante)");

        arbolRepository.deleteAll();
    }

}
