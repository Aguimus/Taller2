package org.example.taller2.persistance.repositories;

import org.example.taller2.persistance.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRespository extends JpaRepository<Prestamo, Long> {
}
