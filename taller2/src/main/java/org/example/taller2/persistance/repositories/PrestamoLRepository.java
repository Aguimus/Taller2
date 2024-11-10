package org.example.taller2.persistance.repositories;

import org.example.taller2.persistance.entity.Prestamo_libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoLRepository extends JpaRepository<Prestamo_libro, Long> {
}
