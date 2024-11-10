package org.example.taller2.controller;

import org.example.taller2.persistance.entity.*;
import org.example.taller2.persistance.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RestController
@RequestMapping(path = "/")
public class controller {

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
        System.out.println((String) datos.get("nombre"));
        Categoria c = categoriaRepository.findByNombre((String) datos.get("nombre"));
        return c.getLibros();
    }

    @GetMapping("/librosPorAutor")
    public List<Libro> obtenerLibrosPorAutor(@RequestBody Map<String, Object> datos){
        System.out.println((String) datos.get("nombre"));
        Autor a = autorRepository.findByNombre((String) datos.get("nombre"));
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
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(libro -> ResponseEntity.ok(libro))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo libro
    @PostMapping("/libros")
    public Libro crearProducto(@RequestBody Libro libro) {
        Autor autor = autorRepository.findByNombre(libro.getNombreAutor());
        libro.setAutor(autor);
        Categoria categoria = categoriaRepository.findByNombre(libro.getNombreCategoria());
        libro.setCategoria(categoria);
        return libroRepository.save(libro);
    }


    // Actualizar un libro
    @PutMapping("/libros/{id}")
    public ResponseEntity<Libro> actualizarProducto(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroDetalles.getTitulo());
                    libro.setAnioPublicacion(libroDetalles.getAnioPublicacion());
                    libro.setDescripcion(libroDetalles.getDescripcion());
                    libro.setAutor(autorRepository.findById(libroDetalles.getAutor().getId()).get());
                    libro.setCategoria(categoriaRepository.findById(libroDetalles.getCategoria().getId()).get());
                    libro.setDisponibilidad(libroDetalles.isDisponibilidad());
                    libro.setDescripcion(libroDetalles.getDescripcion());
                    return ResponseEntity.ok(libroRepository.save(libro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un libro
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libroRepository.delete(libro);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo cliente
    @PostMapping("/cliente")
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
