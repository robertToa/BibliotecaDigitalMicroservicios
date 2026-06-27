package com.biblioteca_digital.svc_catalogo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "catalogo")
public class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Integer anioPublicacion;

    @Column(nullable = false)
    private boolean disponible;

    @Column(nullable = false)
    private Integer copiasTotales;

    @Column(nullable = false)
    private Integer copiasDisponibles;
}
