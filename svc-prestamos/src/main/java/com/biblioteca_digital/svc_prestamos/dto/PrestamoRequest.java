package com.biblioteca_digital.svc_prestamos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrestamoRequest {

    @NotBlank(message = "El isbn es obligatorio")
    private String isbn;

    @NotBlank(message = "El miembro es obligatorio")
    private String miembroId;

    @NotBlank(message = "La fecha de devolución estimada es obligatorio")
    private String fechaDevolucionEstimada;
}
