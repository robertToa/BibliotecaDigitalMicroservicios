package com.biblioteca_digital.svc_catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CatalogoRequest {
    @NotBlank(message = "El isbn es obligatorio")
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    private String genero;

    @NotNull(message = "El año de publicación es obligatorio")
    private Integer anioPublicacion;

    private Integer copiasTotales;

    private Integer copiasDisponibles;
}
