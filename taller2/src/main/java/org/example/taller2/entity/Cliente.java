package org.example.taller2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;


@Entity
public class Cliente {
    @Id
    private Long id;
    private String nombre;
    private String correo;
    private int telefono;
    private Boolean estadoCuenta;

    @OneToMany(mappedBy = "prestamos", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;

}
