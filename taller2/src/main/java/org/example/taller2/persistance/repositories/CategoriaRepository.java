package org.example.taller2.persistance.repositories;

import org.example.taller2.persistance.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    Categoria findByNombre(String nombre);
}
