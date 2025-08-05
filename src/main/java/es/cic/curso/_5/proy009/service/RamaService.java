package es.cic.curso._5.proy009.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso._5.proy009.exception.RamaException;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.repository.RamaRepository;

@Service
@Transactional
public class RamaService {
    private final Logger logger = LoggerFactory.getLogger(Rama.class);

    @Autowired
    private RamaRepository ramaRepository;

    // Vamos con el CRUD

    // Create

    public Rama create(Rama rama) {
        logger.info("Creamos un rama");
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
        logger.info("rama Actualizado Correcramente");
        return ramaRepository.save(rama);

    }

    // DELETE

    public void deleteById(long id) {
        ramaRepository.deleteById(id);
    }
}
