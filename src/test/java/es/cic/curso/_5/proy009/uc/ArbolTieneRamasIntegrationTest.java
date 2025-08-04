package es.cic.curso._5.proy009.uc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.model.Rama;
import es.cic.curso._5.proy009.service.ArbolService;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolTieneRamasIntegrationTest {

        // Autowired de MockMvc
        @Autowired
        private MockMvc mockMvc;

        // Object Mapper
        @Autowired
        private ObjectMapper objectMapper;

        // Arbol Service
        @Autowired
        private ArbolService arbolService;

        // CRUD:

        @Test
        public void testCrearArbolConRamas() throws Exception {

                Arbol arbol = new Arbol();
                arbol.setEspecie("Roble Común");
                arbol.setAltura(1950);
                arbol.setEdad(180);
                arbol.setVersion(1L);

                Rama rama1 = new Rama();
                rama1.setLongitud(15);
                rama1.setTieneHojas(false);
                rama1.setNivel(1);

                arbol.addRama(rama1);

                String json = objectMapper.writeValueAsString(arbol);

                String resultjson = mockMvc.perform(post("/arbol")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {
                                        assertNotNull(
                                                        objectMapper.readValue(
                                                                        result.getResponse().getContentAsString(),
                                                                        Arbol.class),
                                                        "El arbol tiene ramas");
                                }).andReturn().getResponse().getContentAsString();

                Arbol arbolPost = objectMapper.readValue(resultjson, Arbol.class);
                Arbol arbolDeLaBaseDatos = (Arbol) arbolService.getArbolById(arbolPost.getId());

                assertEquals(arbolDeLaBaseDatos, arbolPost);
                assertEquals(arbolDeLaBaseDatos, arbolPost);

        }

        @Test
        public void obtenerArbolConRamas() throws Exception {

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

                // Recordemos que este metodo hace que tambnien se añada el arbol a la rama
                arbol.addRama(rama1);

                // Dejamos el arbol creado
                arbolService.create(arbol);

                mockMvc.perform(get("/arbol/" + arbol.getId()))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {

                                        Arbol resultadoArbol = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        Arbol.class);
                                        assertEquals(resultadoArbol.getId(), arbol.getId());

                                        assertEquals(resultadoArbol.getRamas().get(0).getNivel(), rama1.getNivel());
                                });

        }

        @Test
        public void actualizarArbolConRamas() throws Exception {
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

                // Recordemos que este metodo hace que tambnien se añada el arbol a la rama
                arbol.addRama(rama1);

                // Dejamos el arbol creado
                String json = objectMapper.writeValueAsString(arbol);

                String resultjson = mockMvc.perform(post("/arbol")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {
                                        assertNotNull(
                                                        objectMapper.readValue(
                                                                        result.getResponse().getContentAsString(),
                                                                        Arbol.class),
                                                        "El arbol tiene ramas");
                                }).andReturn().getResponse().getContentAsString();

                Arbol arbolPost = objectMapper.readValue(resultjson, Arbol.class);
                
                mockMvc.perform(get("/arbol/" + arbolPost.getId()))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {

                                        Arbol resultadoArbol = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        Arbol.class);
                                        assertTrue(resultadoArbol.getId()>0);

                                        assertEquals(resultadoArbol.getRamas().get(0).getNivel(), rama1.getNivel());
                                });           



                Arbol arbolActulizado = objectMapper.readValue(resultjson, Arbol.class);
                arbolActulizado.getRamas().remove(0);

                String jsonActualizado = objectMapper.writeValueAsString(arbolActulizado);

                mockMvc.perform(put("/arbol")
                                .contentType("application/json")
                                .content(jsonActualizado))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {
                                }).andReturn().getResponse().getContentAsString();

                mockMvc.perform(get("/arbol/" + arbolActulizado.getId()))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(result -> {

                                        Arbol resultadoArbol = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        Arbol.class);

                                        assertTrue(resultadoArbol.getRamas().isEmpty());
                                });

        }

}
