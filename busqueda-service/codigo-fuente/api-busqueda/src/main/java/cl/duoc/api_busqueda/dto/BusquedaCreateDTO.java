package cl.duoc.api_busqueda.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BusquedaCreateDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long contenidoId;
    @NotNull(message = "El título es obligatorio")
    private String titulo;


}
