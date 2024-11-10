package org.example.taller2.persistance.repositories;

import org.example.taller2.persistance.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTitulo(String title);
}
