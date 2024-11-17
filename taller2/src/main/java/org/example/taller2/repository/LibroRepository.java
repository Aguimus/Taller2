package org.example.taller2.repository;

import org.example.taller2.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTituloContaining(String title);
}
