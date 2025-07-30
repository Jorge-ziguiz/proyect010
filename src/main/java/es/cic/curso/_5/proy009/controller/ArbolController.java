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

import es.cic.curso._5.proy009.exception.ModificationSecurityException;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.service.ArbolService;

@RestController
@RequestMapping("/arbol")
public class ArbolController {

    //Creamos e instanciamos el logger como Final porque me da la gana
    private static final Logger LOGGER = LoggerFactory.getLogger(ArbolController.class);

    //Nos enlacamos al Service y como me da palo inicializar pues autowired
    @Autowired
    private ArbolService arbolService;

    //CRUD

    //CREATE (POST)
    @PostMapping
    public Arbol crearArbol(@RequestBody Arbol arbol){

        //Chequiamos si el usuario se las ha ingeniado para meter un id
        if (arbol.getId() != null){

            //Le damos a sistemas una manita ;)
            LOGGER.info("El arbol no puede tener un ID en su creación. El id se crea automáticamente");
            //Lo sacamos por ahi para que no explote el univreso
            throw new ModificationSecurityException("El arbol no puede llevar ID");

        }else {//Si ta to bien (Necesitaremos hablar con los encargados de UI/UX para saber si tenemos que gestionar desde aqui campos vacios)

            //Info
            LOGGER.info("Arbol creado correctamente");
            //Creamos
            return arbolService.create(arbol);
        }
    }

    //APRENDE A LEER (GET)
    @GetMapping("/{id}")
    public Arbol get (@PathVariable long id){
        return arbolService.getArbolById(id);
    }

    @GetMapping
    public List<Arbol> getAll(){
        return arbolService.get();
    }

    //ACTUALIZAR (PUT)
    @PutMapping
    public Arbol update(@RequestBody Arbol arbol){
        return arbolService.update(arbol);
    }

    //ELIMINAR (DELETE)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        arbolService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll(){
        arbolService.deleteAll();
    }

}
