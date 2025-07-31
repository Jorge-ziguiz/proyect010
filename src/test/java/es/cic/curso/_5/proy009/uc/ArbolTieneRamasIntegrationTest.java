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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;


@SpringBootTest
@AutoConfigureMockMvc
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
        arbol.setVersion(1L);

        Rama rama1 = new Rama();
        rama1.setLongitud(15);
        rama1.setTieneHojas(false);
        rama1.setNivel(1);

        //Recordemos que este metodo hace que tambnien se añada el arbol a la rama
        arbol.addRama(rama1);

        String json = objectMapper.writeValueAsString(arbol);

        
        
    }

}
