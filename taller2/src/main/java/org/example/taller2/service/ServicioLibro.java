package org.example.taller2.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioLibro {
    private HttpClient client;
    private RestTemplate restTemplate;


    //private Logger logger = LoggerFactory.getLogger(PokeService.class);

    public ServicioLibro(@Autowired HttpClient client, @Autowired RestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }
    public Map<String, Object> getLibrobyName(String name) {
        String url = "https://openlibrary.org/search.json?title=" + name;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JsonNode root = mapper.readTree(response.getBody());
            JsonNode docs = root.path("docs");

            if (docs.isArray() && docs.size() > 0) {
                JsonNode firstBook = docs.get(0);

                String titulo = firstBook.path("title").asText("Título no disponible");
                String autor = firstBook.path("author_name").isArray() && firstBook.get("author_name").size() > 0
                        ? firstBook.get("author_name").get(0).asText("Autor no disponible")
                        : "Autor no disponible";
                String categoria = firstBook.path("subjects").isArray() && firstBook.get("subjects").size() > 0
                        ? firstBook.get("subjects").get(0).asText("Categoria no disponible")
                        : "Categoria no disponible";
                int anioPublicacion = firstBook.path("first_publish_year").asInt(0);

                // Crear Map de salida con los datos requeridos
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("titulo", titulo);
                resultado.put("autor", autor);
                resultado.put("anioPublicacion", anioPublicacion);
                resultado.put("categoria", categoria);
                resultado.put("Disponibilidad", false);

                return resultado;
            } else {
                Map<String, Object> mensajeError = new HashMap<>();
                mensajeError.put("mensaje", "No se encontraron resultados");
                return mensajeError;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al procesar la solicitud");
            return errorResponse;
        }
    }
}
