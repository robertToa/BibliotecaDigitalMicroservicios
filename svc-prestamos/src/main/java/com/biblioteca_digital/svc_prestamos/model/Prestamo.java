package com.biblioteca_digital.svc_prestamos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    private String id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String miembroId;

    @Column(nullable = false, name = "fecha_prestamo")
    private String fechaPrestamo;

    @Column(nullable = false, name = "fecha_devolucion_estimada")
    private String fechaDevolucionEstimada;

    @Column(name = "fecha_devolucion_real")
    private String fechaDevolucionReal;

    @Column(nullable = false)
    private TipoEstado estado;

}
