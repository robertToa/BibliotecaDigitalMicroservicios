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

    @Column(nullable = false, name = "tipo_miembro")
    private TipoMiembro tipoMiembro;

    @Column(nullable = false, name = "fecha_registro")
    private String fechaRegistro;

    @Column(nullable = false, name = "prestamos_activos")
    private Integer prestamosActivos;
}
