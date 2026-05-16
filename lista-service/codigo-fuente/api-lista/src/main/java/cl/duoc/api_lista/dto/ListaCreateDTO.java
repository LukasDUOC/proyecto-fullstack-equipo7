package cl.duoc.api_lista.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListaCreateDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
    @NotNull(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "Los IDs de los contenidos son obligatorios")
    private List<Long> contenidosId;

   
}