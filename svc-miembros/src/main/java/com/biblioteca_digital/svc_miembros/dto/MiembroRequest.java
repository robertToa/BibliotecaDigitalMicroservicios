package com.biblioteca_digital.svc_miembros.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MiembroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    private String email;

    private String tipoMiembro;

    private Integer prestamosActivos;
}
