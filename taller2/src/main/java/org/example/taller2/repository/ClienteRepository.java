package org.example.taller2.repository;

import org.example.taller2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombre(String nombre);
    @Query("SELECT c FROM Cliente c WHERE c.username = :username")
    Optional<Cliente> findByUsername(@Param("username") String username);

}
