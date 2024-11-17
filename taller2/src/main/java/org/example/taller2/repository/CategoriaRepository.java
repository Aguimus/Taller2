package org.example.taller2.repository;

import org.example.taller2.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    Categoria findFirstByNombreContaining(String nombre);
}
