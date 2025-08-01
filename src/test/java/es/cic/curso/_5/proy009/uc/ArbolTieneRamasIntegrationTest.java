package es.cic.curso._5.proy009.uc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso._5.proy009.controller.ArbolController;
import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.service.ArbolService;


@SpringBootTest
@AutoConfigureMockMvc
public class ArbolTieneRamasIntegrationTest {

    //Autowired de MockMvc
    @Autowired
    private MockMvc mockMvc;

    //Object Mapper
    @Autowired
    private ObjectMapper objectMapper;

    //Arbol Controller
    @Autowired
    private ArbolController arbolController;

    //Arbol Service
    @Autowired
    private ArbolService arbolService;

    //CRUD:

    @Test
    public void testCrearArbolConRamas() throws Exception{

        // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);
        arbol.setVersion(1L);

        Rama rama1 = new Rama();
        rama1.setLongitud(15);
        rama1.setTieneHojas(false);
        rama1.setNivel(1);

        //Recordemos que este metodo hace que tambnien se añada el arbol a la rama
        arbol.addRama(rama1);

        String json = objectMapper.writeValueAsString(arbol);

        //Si hacemos el post nos funciona?
        MvcResult mvcResult = mockMvc.perform(post("/arbol")    //Hacemos un post en arbol, ya que toda la logica esta ahi
                                .contentType("application/json")//Pasandole un Json
                                .content(json))                 //Le oasanis ek json que sacamos de arbol
                                .andExpect(status().isOk())     //Y esperamos que el resultado SEA UN CODIGO 200
                                .andExpect(result ->{           //Y esperamos que el resultado (RESULT Es una instancia de MvcREsult que encapsula la respuesta http)
                                    assertNotNull(              //No sea nulo
                                        objectMapper.readValue( //Cogemos el valor de la cadena
                                            result.getResponse().getContentAsString(), Arbol.class), //Usamos Jackson para decirle que convierta la cadena en un tipo de variable
                                            "El arbol tiene ramas" //Sacamos una respuesta por consola 
                                    );
                                }).andReturn(); //Devolvemos el MvcREsult

        mvcResult.toString();//Le pasamos un String
    }

    @Test
    public void obtenerArbolConRamas() throws Exception{

         // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);
        arbol.setVersion(1L);

        Rama rama1 = new Rama();
        rama1.setLongitud(15);
        rama1.setTieneHojas(false);
        rama1.setNivel(1);

        //Recordemos que este metodo hace que tambnien se añada el arbol a la rama
        arbol.addRama(rama1);
        //Dejamos el arbol creado
        arbolService.create(arbol);
        
        //Hacemos al arbol un Json para usarlo mas adelante
        String json = objectMapper.writeValueAsString(arbol);

        mockMvc.perform(get("/arbol/"+arbol.getId()))
                                    .andDo(print())             //Sacame por la consola la petición Y la respuesta
                                    .andExpect(status().isOk()) //Esperamos que la respuesta http sea 200
                                    .andExpect(result -> {      //Leemos el contenido Json de la Respuesta
                                        assertEquals(objectMapper.readValue( //Y esperamos que sea el mismo al Json de Arbol
                                            result.getResponse().getContentAsString(), //Lo convertimos a string
                                            Arbol.class).getId(), arbol.getId()); //Le decimos que coja el id y los compare ya que el metodo necesita un Long
                                    });
    }

    @Test
    public void actualizarArbolConRamas() throws Exception{
        
    }

}
