package com.biblioteca_digital.svc_catalogo.dto;

import jakarta.validation.constraints.Min;
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

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1500, message = "El año de publicación debe ser válido")
    private Integer anioPublicacion;

    @NotNull(message = "El número de copias totales es obligatorio")
    private Integer copiasTotales;

    @NotNull(message = "El nímero de copias disponibles es obligatorio")
    private Integer copiasDisponibles;
}
