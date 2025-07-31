package es.cic.curso._5.proy009.ControllerTest;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso._5.proy009.model.Arbol;
import es.cic.curso._5.proy009.repository.ArbolRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolControllerIntegrationTest {

@Autowired
      private MockMvc mockMvc;

      @Autowired
      private ArbolRepository arbolRepository;

      @Autowired
      private ObjectMapper objectMapper;

      /**
       * ########################
      * # CRUD #
      * ########################
      */

      /**
       * ########################
      * # CREATE #
      * ########################
      */

      @Test // Es un metodo de test para spting
      @DisplayName("post /arbol Guarda la moto y devuelve un Json con el Id")
      void shouldCreateArbol() throws Exception {

         // PREPARAMOS
         Arbol arbol = new Arbol();
         arbol.setEspecie("Roble Común");
         arbol.setAltura(1950);
         arbol.setEdad(180);

         String arbolJson = objectMapper.writeValueAsString(arbol);

         // EJECUTAMOS
         MvcResult res = mockMvc.perform(post("/arbol")
               .contentType("application/json")
               .content(arbolJson))
               .andExpect(status().isOk())
               .andReturn();

         // COMPROBAMOS
         Arbol cuerpo = objectMapper.readValue(
               res.getResponse().getContentAsString(),
               Arbol.class);

         assertTrue(cuerpo.getId() > 0, "El id tiene que ser positivo");
      }

      /**
      * ########################
      * # READ #
      * ########################
      */

      /**
       * Test de Metodo READ sin argunemtos
       * @throws Exception
       */
      @Test
      void testGetSinID() throws Exception{

         // PREPARAMOS
         Arbol arbol = new Arbol();
         arbol.setEspecie("Roble Común");
         arbol.setAltura(1950);
         arbol.setEdad(180);

         arbolRepository.save(arbol);

         //Hacemos el Get
         mockMvc.perform(get("/arbol"))
                        .andExpect(status().isOk());
         
      }

      /**
       * 
       * @throws Exception
       */
      @Test
      @DisplayName("GET /arbol/{id} devuelve 200 con objeto y 404 si no encuentra referencia")
      void shoudReturnArbolOrNotFound() throws Exception {

         // PREPARAMOS
         Arbol arbol = new Arbol();
         arbol.setEspecie("Roble Común");
         arbol.setAltura(1950);
         arbol.setEdad(180);

         arbolRepository.save(arbol);

         // EJECUTAMOS
                // 1) Existe el arbol
                mockMvc.perform(get("/arbol/{id}", arbol.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(arbol.getId()))
                                .andExpect(jsonPath("$.especie").value("Roble Común"))
                                .andExpect(jsonPath("$.altura").value(1950))
                                .andExpect(jsonPath("$.edad").value(180));

                // 2) No existe el arbol
                long idInexistente = arbol.getId() + 999;// Cogemos el id y le sumamos 100 para que no coincida
                //String expectedString = "No existe el Arbol con ID " + idInexistente;

                mockMvc.perform(get("/arbol/{id}", idInexistente))
                                .andExpect(status().isNotFound());
                                //.andExpect(content().string(expectedString));
        }

      /**
       * ########################
      * # UPDATE #
      * ########################
      */

      @Test
      void shouldUpdateArbol() throws Exception {

            // PREPARAMOS
            Arbol arbol = new Arbol();
            arbol.setEspecie("Roble Común");
            arbol.setAltura(1950);
            arbol.setEdad(180);

            arbolRepository.save(arbol);

                //Añadimos el arbol a la BBDD
                arbolRepository.save(arbol);

                //modificamos el arbol para inyectarlo
                arbol.setEspecie("YOU GET THE TREE");
                arbol.setEdad(200);
                arbol.setAltura(1960);

                String jsonStroString = objectMapper.writeValueAsString(arbol);

                //Ejecutamos
                mockMvc.perform(put("/arbol")
                    .contentType(MediaType.APPLICATION_JSON).content(jsonStroString))
                    .andExpect(jsonPath("$.id").value(arbol.getId()))
                    .andExpect(jsonPath("$.especie").value("YOU GET THE TREE"))
                    .andExpect(jsonPath("$.altura").value(1960))
                    .andExpect(jsonPath("$.edad").value(200))
                    .andExpect(status().isOk());
            
      }

      /**
       * ########################
      * # DELETE #
      * ########################
      */

      /**
         * Metodo que comprueba la funcionalidad de borrar una moto en caso de que esta
         * exista
         * 
         * @throws Exception
         */
        @Test
        @DisplayName("DELETE /motos/{id} Elimina el registro")
        void shouldDeleteArbol() throws Exception {

                // PEC

                // PREPARAMOS
            Arbol arbol = new Arbol();
            arbol.setEspecie("Roble Común");
            arbol.setAltura(1950);
            arbol.setEdad(180);

            arbolRepository.save(arbol);

            // EJECUTAMOS
            // Le decimos con mock que nos haga el delete
            mockMvc.perform(delete("/arbol/{id}", arbol.getId()))
                            .andExpect(status().isOk());

            // COMPROBAMOS
            assertTrue((arbolRepository.findById(arbol.getId()).isEmpty()),
                            "El arbol ha sido eliminado");

        }


}
