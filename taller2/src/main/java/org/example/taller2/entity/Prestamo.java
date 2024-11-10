package org.example.taller2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Prestamo {
    @Id
    private int id;

    @Column
    private Long idCliente;
    private Date fechaInicio;
    private Date fechaFinal;

    public Prestamo(int id, int idCliente, Date fechaInicio, Date fechaFinal) {
        this.id = id;
        this.idCliente = idCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public Prestamo(int idCliente, Date fechaInicio, Date fechaFinal) {
        this.idCliente = idCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public Prestamo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                '}';
    }
}
