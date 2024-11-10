package org.example.taller2.persistance.repositories;

import org.example.taller2.persistance.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {
 Autor findByNombre(String nombre);
}
