package com.biblioteca_digital.svc_miembros.dto;
import com.biblioteca_digital.svc_miembros.model.TipoMiembro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MiembroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String email;

    @NotNull(message = "El tipo de miembro es obligatorio")
    @Pattern(
            regexp = "ACTIVO|DEVUELTO|ATRASADO",
            message = "El tipo de miembro es inválido"
    )
    private TipoMiembro tipoMiembro;
}
