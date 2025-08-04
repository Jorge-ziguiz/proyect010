package es.cic.curso._5.proy009.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.exception.RamaException;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.repository.RamaRepository;

@Service
@Transactional
public class RamaService {
    private final  Logger logger = LoggerFactory.getLogger(Rama.class);

    @Autowired 
    private RamaRepository ramaRepository;

    // Vamos con el CRUD

    // Create

    public Rama create(Rama rama) {
        logger.info("Creamos un rama");
        if (rama.getId() != null) {
            throw new ModificationSecurityException("No se puede crear un rama con un ID DADO POR EL USUARIO");
        }
        return ramaRepository.save(rama);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Rama> get() {
        logger.info("Obteniendo lista de todos los ramas....");
        return ramaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rama getRamaById(long id) {
        logger.info("Buscando rama con id {}", id);

        return ramaRepository.findById(id)
                .orElseThrow(() -> new RamaException(id));
    }

    // Update

    public Rama update(Rama rama) {

        logger.info("Actualizando rama....");

        if (rama.getId() == null) {

            logger.info("Se intenta crear rama con un id nulo");

            throw new ModificationSecurityException("El id no puede ser Nulo");

        } else if (!ramaRepository.existsById(rama.getId())) {

            logger.info("Se intenta actualizar in rama que no existe");

            throw new RamaException(rama.getId());

        } else {

            logger.info("rama Actualizado Correcramente");

            return ramaRepository.save(rama);
        }
    }

    // DELETE

    public void deleteById(long id) {

        logger.info("Borrando ramas...");

        if (!ramaRepository.existsById(id)) {

            logger.info("No se puede eliminar un rama que no existe");

            throw new RamaException(id);

        } else {
            ramaRepository.deleteById(id);

            logger.info("Borrado satisfactoriemante rama con ID " + id);
        }
    }

    public void deleteAll() {

        logger.info("Borramos TODOS LAS RAMAS GENTE, PANICO (pero solo si habia algo importante");

        ramaRepository.deleteAll();
    }

}
