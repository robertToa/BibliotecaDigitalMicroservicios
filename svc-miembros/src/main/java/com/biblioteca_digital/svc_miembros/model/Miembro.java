package com.biblioteca_digital.svc_miembros.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "miembro")
public class Miembro {
    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tipoMiembro;

    @Column(nullable = false)
    private String fechaRegistro;

    @Column(nullable = false)
    private Integer prestamosActivos;
}
