package es.cic.curso._5.proy009.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso._5.proy009.exception.ArbolException;
import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.repository.ArbolRepository;

@Service
@Transactional
public class ArbolService {

    // Usamos Logger para mantener un registro de la actividad y los herrores
    private final static Logger LOGGER = LoggerFactory.getLogger(Arbol.class);

    private final ArbolRepository arbolRepository;

    public ArbolService(ArbolRepository arbolRepository){
        this.arbolRepository=arbolRepository;
    }

    //Vamos con el CRUD

    //Create

    public Arbol create(Arbol arbol){
        LOGGER.info("Creamos un arbol");
        if (arbol.getId() != null){
            
        }
        return arbolRepository.save(arbol);
    }

    //Read

    public List<Arbol> get(){
        LOGGER.info("Obteniendo lista de todos los arboles....");
        return arbolRepository.findAll();     
    }
    
    public Arbol getArbolById(long id){
        LOGGER.info("Buscando Arbol con id {}", id);

        return arbolRepository.findById(id)
                                .orElseThrow(() 
                                -> new ModificationSecurityException("No se encontró el Arbol con el ID especificado {}"+id));
    }

    //Update 

    public Arbol update (Arbol arbol){

        LOGGER.info("Actualizando Arbol...");

        //Filtramos
        //Si no tiene ID
        if (arbol.getId() == null){

            //Actualicamos a los de sistemas de lo que pasa
            LOGGER.info("Se intenta crear arbol con un id nulo");

            //Lanzamos una excepcion de seguridad
            throw new ModificationSecurityException("El id no puede ser Nulo");
            
        //Si NO  hay un arbol con ese ID 
        }else if (!arbolRepository.existsById(arbol.getId())){

            //Informamos a los amigos de sistemas
            LOGGER.info("Se intenta actualizar in arbol que no existe");

            //Lanzamos un ArbolException porque ya existe
            throw new ArbolException(arbol.getId());
        
        //En cualquier otro caso
        }else {

            //Que pesados los de sistemas macho
            LOGGER.info("Arbol Actualizado Correcramente");

            //Devolvemos el arbol creado
            return arbolRepository.save(arbol);
        }
    }

    //DELETE

    public void deleteById(long id){

        //les voy a empezar a meter chistes en el logger 
        LOGGER.info("Borrando Arboles...");

        //Si no existe el coso
        if (!arbolRepository.existsById(id)) {

            //Les decimos a los panas 
            LOGGER.info("No se puede eliminar un arbol que no existe");

            //Lanzamos una excepcion
            throw new ArbolException(id);

        //Si esta pos
        } else {
            //Hacemos el delete
            arbolRepository.deleteById(id);

            //Y se lo decimos a los compas
            LOGGER.info("Borrado satisfactoriemante arbol con ID " +id);
        }
    }

    public void deleteAll(){

        //El clasico
        LOGGER.info("Borramos TODOS LOS ARBOLES GENTE, PANICO (pero solo si habia algo importante)");

        arbolRepository.deleteAll();
    }

}
