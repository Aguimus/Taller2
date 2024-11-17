package org.example.taller2.repository;

import org.example.taller2.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRespository extends JpaRepository<Prestamo, Long> {
}
