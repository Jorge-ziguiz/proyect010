package es.cic.curso._5.proy009.uc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;

public class ArbolTieneRamasIntegrationTest {

    //Autowired de MockMvc
    @Autowired
    private MockMvc mockMvc;

    //Object Mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testArbolTieneRamas() throws Exception{

        // PREPARAMOS
        Arbol arbol = new Arbol();
        arbol.setEspecie("Roble Común");
        arbol.setAltura(1950);
        arbol.setEdad(180);

        Rama rama1 = new Rama();
        rama1.setLongitud(15);
        rama1.setTieneHojas(false);
        rama1.setNivel(1);

        //Le asignamos las ramas al arbol de Test Y VICEVERSA
        //rama1.setArbol(arbol);
        arbol.addRama(rama1);
        ObjectMapper mapeador = new ObjectMapper();
        //Convertimos la relacion en un string para poder manipularla
        String hayRamas = mapeador.writeValueAsString(arbol);
        //String arbolTieneRamas = objectMapper.writeValueAsString(arbol);
        System.out.println("MOTORISTA TEST A JSON VALOR:"+arbol);

        //Utilizamos MockMVC para simular una peticion HTTP de creacion de moto (POST)
        MvcResult mvcResult = mockMvc.perform(post("/arbol/rama") //Hazme un Post
                                .contentType("application/json")     //Pasandote un JSON
                                .content(hayRamas))               //Concretamente el Json que hicimos antes
                                .andExpect(status().isOk())                     //Si esta correcto
                                .andExpect( arbolResult ->{                   //Y el resultado
                                    assertNotNull(                              //No es nulo
                                        objectMapper.readValue(                 //Si tomamos como referencia el 
                                            arbolResult.getResponse().getContentAsString(), Arbol.class), //Contenido de persona como string
                                                "El arbol tiene ramas"); //Y lanzamos el mensaje
                                    })
                                .andReturn(); //Y devolvemos el resultado de mockResult

        mvcResult.toString(); //Masamos el Result a string para verlo por aqui
        
        // Arbol arbolCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Arbol.class);
        // Long id = arbolCreado.getId();

        // mockMvc.perform(get("/arbol/" + id))
        //         .andDo(print())
        //         .andExpect(status().isOk())
        //         .andExpect(result -> {
        //             assertEquals(objectMapper.readValue(result.getResponse().getContentAsString(), Arbol.class).getId(),
        //                     id);
        //         });   
         
        // //Le cambiamos la marca a la moto del motorista
        // arbolCreado.getRamas().set("Honda");

        // //Variables de apoyo en fase de test
        // // Motorista socorro = motoristaCreado;
        // // socorro.getMoto().setMarca("Honda");
        
        // String arbolJson = objectMapper.writeValueAsString(arbolCreado);

        // //Utilizamos MockMVC para simular una peticion HTTP de actualizacion de moto (POST)
        // mockMvc.perform(put("/motoristas")
        //         .contentType("application/json")
        //         .content(arbolJson))
        //         .andDo(print())                
        //         .andExpect(status().isOk());

        // //Hacemos el delete para eliminar el motorista
        // mockMvc.perform(delete("/motoristas/" + id))
        //         .andDo(print())        
        //         .andExpect(status().isOk());  
    }

}
