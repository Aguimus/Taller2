package org.example.taller2.controller;

import org.example.taller2.persistance.entity.*;
import org.example.taller2.persistance.repositories.*;
import org.example.taller2.service.ServicioLibro;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RestController
@RequestMapping(path = "/")
public class controller {

    @Autowired
    private ServicioLibro servicioLibro;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRespository prestamoRepository;

    @Autowired
    private PrestamoLRepository prestamoLRepository;

    @GetMapping("/")
    public Map<String, String> hello(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hola Mundo");
        return response;
    }

    //******Libros*****

    @GetMapping("/libros")
    public List<Libro> obtenerTodosLosLibros(){
        return libroRepository.findAll();
    }
    @GetMapping("/librosPorCategoria")
    public List<Libro> obtenerLibrosPorCategoria(@RequestBody Map<String, Object> datos){
        Categoria c = categoriaRepository.findFirstByNombreContaining((String) datos.get("nombre"));
        return c.getLibros();
    }

    @GetMapping("/librosPorAutor")
    public List<Libro> obtenerLibrosPorAutor(@RequestBody Map<String, Object> datos){
        System.out.println((String) datos.get("nombre"));
        Autor a = autorRepository.findFirstByNombreContaining((String) datos.get("nombre"));
        return a.getLibros();
    }

    @GetMapping("/librosPorPrestamo")
    public List<Libro> obtenerLibrosPorPrestamo(@RequestBody Map<String, Object> datos) {
        Prestamo p = prestamoRepository.findById(Long.parseLong((String) datos.get("id"))).orElse(null);
        if (p == null) {
            return new ArrayList<>();  // o lanzar un error apropiado
        }

        List<Prestamo_libro> pl = p.getPl();
        List<Libro> libros = new ArrayList<>();
        for (Prestamo_libro prestamoLibro : pl) {
            libros.add(prestamoLibro.getLibro());
        }
        return libros;
    }


    // Obtener un libro por su ID
    @GetMapping("/librosPorId")
    public Libro obtenerLibroPorId(@RequestBody Map<String, Object> datos) {
        return libroRepository.findById(Long.parseLong((String) datos.get("id"))).orElse(null);
    }

    @GetMapping("/librosPorTitulo")
    public Map<String, Object> obtenerLibroPorTitulo(@RequestBody Map<String, Object> datos) {
        Libro libro = libroRepository.findByTituloContaining((String) datos.get("titulo"));

        if (libro != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", libro.getId());
            response.put("titulo", libro.getTitulo());
            response.put("descripcion", libro.getDescripcion());
            response.put("anioPublicacion", libro.getAnioPublicacion());
            response.put("disponibilidad", libro.isDisponibilidad());
            response.put("autor", libro.getAutor().getNombre());
            response.put("categoria", libro.getCategoria().getNombre());

            return response;
        } else {
            return servicioLibro.getLibrobyName((String) datos.get("titulo"));
        }
    }

    // Crear un nuevo libro
    @PostMapping("/libros")
    public Libro crearProducto(@RequestBody Map<String, Object> datos) {
        Libro libro = new Libro((String) datos.get("titulo"), Integer.parseInt((String) datos.get("anioPublicacion")),
                (Boolean) datos.get("disponibilidad"), (String) datos.get("descripcion"));
        Autor a = autorRepository.findFirstByNombreContaining((String) datos.get("nombreAutor"));
        Categoria c = categoriaRepository.findFirstByNombreContaining((String) datos.get("nombreCategoria"));
        libro.setAutor(a);
        libro.setCategoria(c);
        a.addLibro(libro);
        c.addLibro(libro);
        return libroRepository.save(libro);
    }


    // Actualizar un libro
    @PutMapping("/actualizarLibros")
    public Libro actualizarProducto(@RequestBody Map<String, Object> datos) {
        Libro libro = libroRepository.findById(Long.parseLong((String) datos.get("id"))).orElse(null);
        Autor a = autorRepository.findFirstByNombreContaining((String) datos.get("nombreAutor"));
        Categoria c = categoriaRepository.findFirstByNombreContaining((String) datos.get("nombreCategoria"));
        if (libro != null) {
            libro.setTitulo((String) datos.get("titulo"));
            libro.setAnioPublicacion(Integer.parseInt((String) datos.get("anioPublicacion")));
            libro.setDescripcion((String) datos.get("descripcion"));
            libro.setAutor(a);
            libro.setCategoria(c);
            libro.setDisponibilidad(Boolean.parseBoolean((String) datos.get("disponibilidad")));
            return libroRepository.save(libro);
        } else {
            return libro;
        }
    }

    // Eliminar un libro
    @DeleteMapping("/eliminarLibros")
    public Map<String, String> eliminarLibro(@RequestBody Map<String, Object> datos) {
        Map<String, String> response = new HashMap<>();
        if (libroRepository.existsById(Long.parseLong(datos.get("id").toString()))) {
            libroRepository.deleteById(Long.parseLong(datos.get("id").toString()));
            response.put("message", "Eliminado con exito");
            return response;
        } else {
            response.put("message", "No se puedo encontrar el libro");
            return response;
        }
    }

    // Crear un nuevo cliente
    @PostMapping("/cliente")
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
