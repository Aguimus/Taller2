package org.example.taller2.controller;

import org.example.taller2.model.*;
import org.example.taller2.repository.*;
import org.example.taller2.service.ServicioLibro;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Libro> obtenerTodosLosLibros(){
        return libroRepository.findAll();
    }
    @GetMapping("/librosPorCategoria")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Libro> obtenerLibrosPorCategoria(@RequestBody Map<String, Object> datos){
        Categoria c = categoriaRepository.findFirstByNombreContaining((String) datos.get("nombre"));
        return c.getLibros();
    }

    @GetMapping("/librosPorAutor")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Libro> obtenerLibrosPorAutor(@RequestBody Map<String, Object> datos){
        System.out.println((String) datos.get("nombre"));
        Autor a = autorRepository.findFirstByNombreContaining((String) datos.get("nombre"));
        return a.getLibros();
    }

    @GetMapping("/librosPorPrestamo")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Libro> obtenerLibrosPorPrestamo(@RequestBody Map<String, Object> datos) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente clienteAutenticado = (Cliente) authentication.getPrincipal();


        Prestamo p = prestamoRepository.findById(Long.parseLong((String) datos.get("id"))).orElse(null);
        if (p == null) {
            return new ArrayList<>();  // o lanzar un error apropiado
        }

        List<Prestamo_libro> pl = p.getPl();
        List<Libro> libros = new ArrayList<>();
        for (Prestamo_libro prestamoLibro : pl) {
            libros.add(prestamoLibro.getLibro());
        }

        // Verifica si el cliente es ADMIN
        if (clienteAutenticado.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return libros;
        }

        // Verifica si el préstamo pertenece al cliente autenticado
        if (!p.getCliente().getId().equals(clienteAutenticado.getId())) {
            // Si el préstamo no pertenece al cliente, devolver vacío
            return new ArrayList<>();
        }

        return libros;
    }


    // Obtener un libro por su ID
    @GetMapping("/librosPorId")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Libro obtenerLibroPorId(@RequestBody Map<String, Object> datos) {
        return libroRepository.findById(Long.parseLong((String) datos.get("id"))).orElse(null);
    }

    @GetMapping("/librosPorTitulo")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
        } else return libro;
    }

    // Eliminar un libro
    @DeleteMapping("/eliminarLibros")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
