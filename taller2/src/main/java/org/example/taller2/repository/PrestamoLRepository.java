package org.example.taller2.repository;

import org.example.taller2.model.Prestamo_libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoLRepository extends JpaRepository<Prestamo_libro, Long> {
}
